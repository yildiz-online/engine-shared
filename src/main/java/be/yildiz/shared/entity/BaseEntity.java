/*
 * This file is part of the Yildiz-Engine project, licenced under the MIT License  (MIT)
 *
 * Copyright (c) 2017 Grégory Van den Borre
 *
 * More infos available: https://www.yildiz-games.be
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 * documentation files (the "Software"), to deal in the Software without restriction, including without
 * limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 * of the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial
 * portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS
 * OR COPYRIGHT  HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE  SOFTWARE.
 */

package be.yildiz.shared.entity;

import be.yildiz.common.BoundedValue;
import be.yildiz.common.collections.Lists;
import be.yildiz.common.collections.Sets;
import be.yildiz.common.gameobject.Movable;
import be.yildiz.common.id.ActionId;
import be.yildiz.common.id.EntityId;
import be.yildiz.common.id.PlayerId;
import be.yildiz.common.vector.Point3D;
import be.yildiz.shared.data.EntityType;
import be.yildiz.shared.data.State;
import be.yildiz.shared.data.ViewDistance;
import be.yildiz.shared.entity.action.AbstractAttack;
import be.yildiz.shared.entity.action.Action;
import be.yildiz.shared.entity.action.ProtectInvincible;
import be.yildiz.shared.entity.action.materialization.EmptyProtectMaterialization;
import be.yildiz.shared.entity.fields.AttackHitResult;
import be.yildiz.shared.entity.fields.SharedPosition;
import be.yildiz.shared.entity.fields.StateHolder;
import be.yildiz.shared.entity.fields.Target;
import be.yildiz.shared.entity.module.EmptyModule;
import be.yildiz.shared.entity.module.EntityModules;
import be.yildiz.shared.entity.module.Module;
import be.yildiz.shared.entity.module.ModuleGroup;
import be.yildiz.shared.entity.module.detector.BlindDetector;
import be.yildiz.shared.entity.module.energy.NoEnergyGenerator;
import be.yildiz.shared.entity.module.hull.InvincibleTemplate;
import be.yildiz.shared.entity.module.interaction.NoWeaponModule;
import be.yildiz.shared.entity.module.move.StaticModule;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * The entity is the logical data for every usable game object.
 *
 * @author Grégory Van den Borre
 */
public final class BaseEntity implements Entity, Target {

    /**
     * This Entity represent the world, it is used to represent an empty or non existing entity.
     */
    public static final BaseEntity WORLD = new BaseEntity(
            EntityInConstruction.WORLD, new EntityModules(
            new InvincibleTemplate()
            .materialize(new ProtectInvincible(EntityId.WORLD, InvincibleTemplate.MODULE, new EmptyProtectMaterialization(EntityId.WORLD))),
            new NoEnergyGenerator(EntityId.WORLD),
            new BlindDetector(EntityId.WORLD),
            new NoWeaponModule(EntityId.WORLD),
            new StaticModule(EntityId.WORLD),
            new EmptyModule(EntityId.WORLD),
            new EmptyModule(EntityId.WORLD),
            new EmptyModule(EntityId.WORLD)));

    private static final State destroyed = new State("destroyed");

    /**
     * List of all entity visible by this one.
     */
    private final Set<Entity> visibleEntities = Sets.newSet();

    private final Set<PlayerId> seenBy = Sets.newSet();

    /**
     * Entity unique Id, this value is used as an identifier for equals method.
     */
    private final EntityId id;
    /**
     * Position, shared between this entity and its modules.
     */
    private final SharedPosition position = new SharedPosition();
    /**
     * States, shared between this entity and its modules.
     */
    private final StateHolder states = new StateHolder();
    private final BoundedValue energy = new BoundedValue();
    private final BoundedValue hp = new BoundedValue();

    private final EntityType type;

    private final EntityModules modules;

    private String name;

    private PlayerId owner;


    private List<Action> actionRunning = Lists.newList();

    private List<Action> actionComplete = Lists.newList();

    private Optional<Action> actionToPrepare = Optional.empty();

    /**
     * Create a new Entity.
     * @param e Entity metadata.
     * @param entityModules Modules.
     */
    public BaseEntity(EntityInConstruction e, EntityModules entityModules) {
        this(e, e.getName(), entityModules);
        this.hp.setValue(e.getHp());
        this.energy.setValue(e.getEnergy());
    }

    /**
     * Create a new Entity.
     * @param e Entity metadata.
     * @param entityModules Modules
     */
    public BaseEntity(DefaultEntityInConstruction e, EntityModules entityModules) {
        this(e, e.getType().name, entityModules);
        this.hp.setValue(entityModules.getHull().getMaxHp());
        this.energy.setValue(entityModules.getEnergyGenerator().getEnergyMax());
    }

    private BaseEntity(
            DefaultEntityInConstruction e,
            String name,
            EntityModules entityModules) {
        super();
        this.type = e.getType();
        this.name = name;
        this.owner = e.getOwner();
        this.id = e.getId();
        this.position.setPosition(e.getPosition());
        this.position.setDirection(e.getDirection());
        this.modules = entityModules;
        this.completeModule(this.modules.getWeapon());
        this.completeModule(this.modules.getMoveEngine());
        this.completeModule(this.modules.getHull());
        this.completeModule(this.modules.getEnergyGenerator());
        this.completeModule(this.modules.getDetector());
        this.completeModule(this.modules.getAdditional1());
        this.completeModule(this.modules.getAdditional2());
        this.completeModule(this.modules.getAdditional3());
        this.hp.setMax(this.modules.getHull().getMaxHp());
        this.energy.setMax(this.modules.getEnergyGenerator().getEnergyMax());
    }

    /**
     * Fill a module with all this entity shared variable.
     *
     * @param module Module to fill.
     */
    //@requires module != null
    //@ensures module.position == this.position
    //@ensures module.hp == this.hp
    //@ensures module.energy == this.energy
    //@ensures module.states == this.states
    private void completeModule(Module<? extends Action> module) {
        module.setSharedPosition(this.position);
        module.setEnergy(this.energy);
        module.setStates(this.states);
        module.setHp(this.hp);
    }

    @Override
    public void doActions(final long time) {
        this.actionComplete.clear();
        if(!this.actionRunning.isEmpty()) {
            for (Action a : this.actionRunning) {
                if (!a.checkPrerequisite()) {
                    this.actionComplete.add(a);
                } else {
                    boolean running = a.run(time);
                    if (!running) {
                        this.actionComplete.add(a);
                    }
                }
            }
            this.actionRunning.removeAll(this.actionComplete);
        }
    }

    @Override
    public Action move(final Point3D destination) {
        Action move = this.modules.getMoveEngine().getAction();
        move.setDestination(destination);
        move.init();
        if(!move.isRunning()) {
            this.actionRunning.add(move);
        }
        return move;
    }

    @Override
    public Action attack(final Target target) {
        Action attack = this.modules.getWeapon().getAction();
        attack.setTarget(target);
        if(!attack.isRunning()) {
            attack.init();
            this.actionRunning.add(attack);
        }
        return attack;
    }

    @Override
    public void lookAt(final Point3D destination) {
        this.position.lookAt(destination);
    }

    @Override
    public void addState(final State state) {
        this.states.addState(state);
    }

    @Override
    public void hit(final AttackHitResult result) {
        this.modules.getHull().getAction().addHitResult(result);
    }

    @Override
    public void removeState(final State state) {
        this.states.removeState(state);
    }

    @Override
    public boolean hasState(final State state) {
        return this.states.hasState(state);
    }

    @Override
    public int hashCode() {
        return this.id.hashCode();
    }

    @Override
    public boolean equals(final Object o) {
        return this == o || o instanceof BaseEntity && this.id.equals(((BaseEntity) o).id);
    }

    @Override
    public boolean isSeeing(final Entity e) {
        return this.visibleEntities.contains(e);
    }

    @Override
    public boolean see(final Entity e) {
        return this.visibleEntities.add(e);
    }

    void noLongerSee(final Entity e) {
        this.visibleEntities.remove(e);
    }

    @Override
    public String toString() {
        return "Entity:" + this.getId() + " -- " + this.getType();
    }

    @Override
    public Action getAction(final ActionId actionId) {
        return this.modules.getAction(actionId);
    }

    @Override
    public void delete() {
        this.modules.delete();
    }

    @Override
    public boolean isDeleted() {
        return this.hasState(destroyed);
    }

    @Override
    public EntityType getType() {
        return this.type;
    }

    @Override
    public Point3D getPosition() {
        return this.position.getPosition();
    }

    @Override
    public void setPosition(Point3D position) {
        //FIXME use the module to set the position instead, to avoid to be able to set the position even if the module cannot move.
        assert position != null;
        this.position.setPosition(position);
    }

    @Override
    public Point3D getDirection() {
        return this.position.getDirection();
    }

    @Override
    public void setDirection(Point3D direction) {
        this.position.setDirection(direction);
    }

    @Override
    public void startAction(ActionId action, Target target, Point3D pos) {
        if (this.modules.getMoveEngine().getId().equals(action)) {
            this.move(pos);
        } else if (this.modules.getWeapon().getId().equals(action)) {
            this.attack(target);
        } else if (this.modules.getAdditional1().getId().equals(action)) {
            Action a = this.modules.getAdditional1().getAction();
            a.setTarget(target);
            a.setDestination(pos);
            a.init();
            this.actionRunning.add(a);
        } else if (this.modules.getAdditional2().getId().equals(action)) {
            Action a = this.modules.getAdditional2().getAction();
            a.setTarget(target);
            a.setDestination(pos);
            a.init();
            this.actionRunning.add(a);
        } else if (this.modules.getAdditional3().getId().equals(action)) {
            Action a = this.modules.getAdditional3().getAction();
            a.setTarget(target);
            a.setDestination(pos);
            a.init();
            this.actionRunning.add(a);
        }
    }

    @Override
    public void startAction(Action a) {
        a.init();
        if(a.id == this.modules.getMoveEngine().getId() && !this.modules.getMoveEngine().getAction().isRunning()
                ||
                a.id == this.modules.getWeapon().getId() && !this.modules.getWeapon().getAction().isRunning()) {
            this.actionRunning.add(a);
        }
    }

    @Override
    public List<Action> getActionDone() {
        return this.actionComplete;
    }

    @Override
    public boolean isZeroHp() {
        return this.hp.getValue() == 0;
    }

    @Override
    public int getHitPoints() {
        return this.hp.getValue();
    }

    @Override
    public void setHitPoints(int hitPoint) {
        this.hp.setValue(hitPoint);

    }

    @Override
    public int getMaxHitPoints() {
        return this.hp.getMax();
    }

    @Override
    public int getEnergyPoints() {
        return this.energy.getValue();
    }

    @Override
    public void setEnergyPoints(int energy) {
        this.energy.setValue(energy);
    }

    @Override
    public int getMaxEnergyPoints() {
        return this.energy.getMax();
    }

    @Override
    public void stopAttack() {
        this.actionRunning.remove(this.modules.getWeapon().getAction());
        this.actionComplete.add(this.modules.getWeapon().getAction());
    }

    @Override
    public List<Action> getActions() {
        return this.actionRunning;
    }

    @Override
    public ViewDistance getLineOfSight() {
        return this.modules.getDetector().getLineOfSight();
    }

    @Override
    public float getHitPointsRatio() {
        return this.hp.getRatio();
    }

    @Override
    public float getEnergyPointsRatio() {
        return this.energy.getRatio();
    }

    @Override
    public boolean isAttacking() {
        return this.modules.getWeapon().getAction().isRunning();
    }

    @Override
    public AbstractAttack getAttackAction() {
        return this.modules.getWeapon().getAction();
    }

    @Override
    public Action getProtectAction() {
        return this.modules.getHull().getAction();
    }

    @Override
    public Action getGenerateEnergyAction() {
        return this.modules.getEnergyGenerator().getAction();
    }

    @Override
    public ModuleGroup getModules() {
        return this.modules.getGroup();
    }

    @Override
    public boolean hasSameOwnerAs(final Entity e) {
        return this.owner.equals(e.getOwner());
    }

    @Override
    public void prepareAction(Optional<ActionId> action) {
        this.actionToPrepare = action.map(this::getAction);
    }

    @Override
    public void setTarget(final Target t) {
        this.actionToPrepare.orElse(this.modules.getWeapon().getAction()).setTarget(t);
    }

    @Override
    public Movable getMaterialization() {
        return this.modules.getHull().getAction().getMaterialization().getObject();
    }

    @Override
    public void setDestination(final Point3D p) {
        this.actionToPrepare.orElse(this.modules.getMoveEngine().getAction()).setDestination(p);
    }

    @Override
    public void setOwner(PlayerId ownerId) {
        assert ownerId != null;
        this.owner = ownerId;
    }

    @Override
    public void startPreparedAction() {
        this.actionToPrepare.ifPresent(this::startAction);
    }

    @Override
    public Action getPreparedAction() {
        return this.actionToPrepare.orElse(this.modules.getMoveEngine().getAction());
    }

    public Set<Entity> getVisibleEntities() {
        return visibleEntities;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public PlayerId getOwner() {
        return owner;
    }

    @Override
    public List<Action> getActionRunning() {
        return actionRunning;
    }

    public List<Action> getActionComplete() {
        return actionComplete;
    }

    @Override
    public Set<PlayerId> getSeenBy() {
        return seenBy;
    }

    @Override
    public EntityId getId() {
        return id;
    }

    @Override
    public Module getAdditional1() {
        return modules.getAdditional1();
    }

    @Override
    public Module getAdditional2() {
        return modules.getAdditional2();
    }

    @Override
    public Module getAdditional3() {
        return modules.getAdditional3();
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }
}