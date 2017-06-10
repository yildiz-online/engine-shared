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
import be.yildiz.common.collections.Maps;
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
import be.yildiz.shared.entity.module.*;
import be.yildiz.shared.entity.module.detector.BlindDetector;
import be.yildiz.shared.entity.module.energy.NoEnergyGenerator;
import be.yildiz.shared.entity.module.hull.InvincibleTemplate;
import be.yildiz.shared.entity.module.interaction.NoWeaponModule;
import be.yildiz.shared.entity.module.move.StaticModule;

import java.util.List;
import java.util.Map;
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
    public static final BaseEntity WORLD = new BaseEntity(EntityInConstruction.WORLD, new StaticModule(EntityId.WORLD), new NoWeaponModule(EntityId.WORLD), new BlindDetector(EntityId.WORLD), new InvincibleTemplate()
            .materialize(new ProtectInvincible(EntityId.WORLD, InvincibleTemplate.MODULE, new EmptyProtectMaterialization(EntityId.WORLD))),
            new NoEnergyGenerator(EntityId.WORLD), new EmptyModule(EntityId.WORLD), new EmptyModule(EntityId.WORLD), new EmptyModule(EntityId.WORLD));

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
    /**
     * Module managing the hit point manipulation for this entity, cannot be null.
     */
    private final Hull hull;
    /**
     * Module managing the energy generation for this entity, cannot be null.
     */
    private final EnergyGenerator energyGenerator;

    private final Detector detector;
    /**
     * Module managing the base weapon for this entity, cannot be null.
     */
    private final Weapon weapon;
    /**
     * Module managing the base move action for this entity, cannot be null.
     */
    private final MoveEngine moveEngine;
    private final EntityType type;

    private final Module additional1;

    private final Module additional2;

    private final Module additional3;

    private String name;

    private PlayerId owner;


    private List<Action> actionRunning = Lists.newList();

    private List<Action> actionComplete = Lists.newList();

    private Optional<Action> actionToPrepare = Optional.empty();

    private Map<ActionId, Action> actions = Maps.newMap();

    /**
     * Create a new Entity.
     * @param e Entity metadata.
     * @param move Move module.
     * @param weapon Interaction module.
     * @param detector Detection module.
     * @param hull Hull module.
     * @param eg Energy generation module.
     * @param additional1 Optional module.
     * @param additional2 Optional module.
     * @param additional3 Optional module.
     */
    public BaseEntity(
            EntityInConstruction e,
            MoveEngine move,
            Weapon weapon,
            Detector detector,
            Hull hull,
            EnergyGenerator eg,
            Module additional1,
            Module additional2,
            Module additional3) {
        this(e, e.getName(), move, weapon, hull, eg, detector, additional1, additional2, additional3);
        this.hp.setValue(e.getHp());
        this.energy.setValue(e.getEnergy());
    }

    /**
     * Create a new Entity.
     * @param e Entity metadata.
     * @param move Move module.
     * @param weapon Interaction module.
     * @param detector Detection module.
     * @param hull Hull module.
     * @param eg Energy generation module.
     * @param additional1 Optional module.
     * @param additional2 Optional module.
     * @param additional3 Optional module.
     */
    public BaseEntity(
            DefaultEntityInConstruction e,
            MoveEngine move, Weapon weapon,
            Hull hull,
            EnergyGenerator eg,
            Detector detector,
            Module additional1,
            Module additional2,
            Module additional3) {
        this(e, e.getType().name, move, weapon, hull, eg, detector, additional1, additional2, additional3);
        this.hp.setValue(hull.getMaxHp());
        this.energy.setValue(eg.getEnergyMax());
    }

    private BaseEntity(
            DefaultEntityInConstruction e,
            String name,
            MoveEngine move,
            Weapon weapon,
            Hull hull,
            EnergyGenerator eg,
            Detector detector,
            Module additional1,
            Module additional2,
            Module additional3) {
        super();
        this.type = e.getType();
        this.name = name;
        this.owner = e.getOwner();
        this.id = e.getId();
        this.position.setPosition(e.getPosition());
        this.position.setDirection(e.getDirection());
        this.weapon = weapon;
        this.completeModule(this.weapon);
        this.moveEngine = move;
        this.completeModule(this.moveEngine);
        this.hull = hull;
        this.completeModule(this.hull);
        this.energyGenerator = eg;
        this.completeModule(this.energyGenerator);
        this.detector = detector;
        this.completeModule(this.detector);
        this.additional1 = additional1;
        this.completeModule(this.additional1);
        this.additional2 = additional2;
        this.completeModule(this.additional2);
        this.additional3 = additional3;
        this.completeModule(this.additional3);
        this.hp.setMax(hull.getMaxHp());
        this.energy.setMax(eg.getEnergyMax());
        this.actions.put(this.moveEngine.getId(), this.moveEngine.getAction());
        this.actions.put(this.weapon.getId(), this.weapon.getAction());
        this.actions.put(this.hull.getId(), this.hull.getAction());
        this.actions.put(this.energyGenerator.getId(), this.energyGenerator.getAction());
        this.actions.put(this.detector.getId(), this.detector.getAction());
        this.actions.put(this.additional1.getId(), this.additional1.getAction());
        this.actions.put(this.additional2.getId(), this.additional2.getAction());
        this.actions.put(this.additional3.getId(), this.additional3.getAction());
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
        Action move = this.moveEngine.getAction();
        move.setDestination(destination);
        move.init();
        System.out.println(move.isRunning());
        if(!move.isRunning()) {
            this.actionRunning.add(move);
        }
        return move;
    }

    @Override
    public Action attack(final Target target) {
        Action attack = this.weapon.getAction();
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
        this.hull.getAction().addHitResult(result);
    }

    @Override
    public void removeState(final State state) {
        assert state != null;
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
        if (this == o) {
            return true;
        }
        if (!(o instanceof BaseEntity)) {
            return false;
        }
        return this.id.equals(((BaseEntity) o).id);
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
        if(!actions.containsKey(actionId)) {
            throw new IllegalArgumentException("actionId is invalid: " + actionId + " for entity " + this.id);
        }
        return this.actions.get(actionId);
    }

    @Override
    public void delete() {
        this.moveEngine.delete();
        this.weapon.delete();
        this.energyGenerator.delete();
        this.detector.delete();
        this.additional1.delete();
        this.additional2.delete();
        this.additional3.delete();
        this.hull.delete();
    }

    @Override
    public boolean isDeleted() {
        return this.hasState(destroyed);
    }

    @Override
    public EntityType getType() {
        return this.type;
    }

    @Deprecated
    //Use getHitPoints
    public int getHp() {
        return this.hp.getValue();
    }

    @Deprecated
    //use get Max hit points
    public int getMaxHp() {
        return this.hp.getMax();
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
        if (this.moveEngine.getId().equals(action)) {
            this.move(pos);
        } else if (this.weapon.getId().equals(action)) {
            this.attack(target);
        } else if (this.additional1.getId().equals(action)) {
            Action a = this.additional1.getAction();
            a.setTarget(target);
            a.setDestination(pos);
            a.init();
            this.actionRunning.add(a);
        } else if (this.additional2.getId().equals(action)) {
            Action a = this.additional2.getAction();
            a.setTarget(target);
            a.setDestination(pos);
            a.init();
            this.actionRunning.add(a);
        } else if (this.additional3.getId().equals(action)) {
            Action a = this.additional3.getAction();
            a.setTarget(target);
            a.setDestination(pos);
            a.init();
            this.actionRunning.add(a);
        }
    }

    @Override
    public void startAction(Action a) {
        a.init();
        this.actionRunning.add(a);
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
        this.actionRunning.remove(this.weapon.getAction());
        this.actionComplete.add(this.weapon.getAction());
    }

    @Override
    public List<Action> getActions() {
        return this.actionRunning;
    }

    @Override
    public ViewDistance getLineOfSight() {
        return this.detector.getLineOfSight();
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
        return this.weapon.getAction().isRunning();
    }

    @Override
    public AbstractAttack getAttackAction() {
        return this.weapon.getAction();
    }

    @Override
    public Action getProtectAction() {
        return this.hull.getAction();
    }

    @Override
    public Action getGenerateEnergyAction() {
        return this.energyGenerator.getAction();
    }

    @Override
    public ModuleGroup getModules() {
        return new ModuleGroup.ModuleGroupBuilder()
                .withHull(this.hull.getId())
                .withEnergy(this.energyGenerator.getId())
                .withDetector(this.detector.getId())
                .withMove(this.moveEngine.getId())
                .withInteraction(this.weapon.getId())
                .withAdditional1(this.additional1.getId())
                .withAdditional2(this.additional2.getId())
                .withAdditional3(this.additional3.getId())
                .build();
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
        this.actionToPrepare.orElse(this.weapon.getAction()).setTarget(t);
    }

    @Override
    public Movable getMaterialization() {
        return this.hull.getAction().getMaterialization().getObject();
    }

    @Override
    public void setDestination(final Point3D p) {
        this.actionToPrepare.orElse(this.moveEngine.getAction()).setDestination(p);
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
        return this.actionToPrepare.orElse(this.moveEngine.getAction());
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
        return additional1;
    }

    @Override
    public Module getAdditional2() {
        return additional2;
    }

    @Override
    public Module getAdditional3() {
        return additional3;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }
}