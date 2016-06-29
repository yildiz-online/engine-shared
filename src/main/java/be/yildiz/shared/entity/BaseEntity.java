//        This file is part of the Yildiz-Online project, licenced under the MIT License
//        (MIT)
//
//        Copyright (c) 2016 Grégory Van den Borre
//
//        More infos available: http://yildiz.bitbucket.org
//
//        Permission is hereby granted, free of charge, to any person obtaining a copy
//        of this software and associated documentation files (the "Software"), to deal
//        in the Software without restriction, including without limitation the rights
//        to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
//        copies of the Software, and to permit persons to whom the Software is
//        furnished to do so, subject to the following conditions:
//
//        The above copyright notice and this permission notice shall be included in all
//        copies or substantial portions of the Software.
//
//        THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
//        IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
//        FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
//        AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
//        LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
//        OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
//        SOFTWARE.

package be.yildiz.shared.entity;

import be.yildiz.common.BoundedValue;
import be.yildiz.common.collections.Lists;
import be.yildiz.common.collections.Sets;
import be.yildiz.common.gameobject.GameMaterialization;
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
import be.yildiz.shared.entity.fields.AttackHitResult;
import be.yildiz.shared.entity.fields.SharedPosition;
import be.yildiz.shared.entity.fields.StateHolder;
import be.yildiz.shared.entity.fields.Target;
import be.yildiz.shared.entity.module.*;
import be.yildiz.shared.entity.module.energy.NoEnergyGenerator;
import be.yildiz.shared.entity.module.hull.Invincible;
import be.yildiz.shared.entity.module.interaction.NoWeaponModule;
import be.yildiz.shared.entity.module.move.StaticModule;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.util.Collections;
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
    public static final BaseEntity WORLD = new BaseEntity(EntityInConstruction.WORLD, new StaticModule(EntityId.WORLD), new NoWeaponModule(EntityId.WORLD), new Invincible(EntityId.WORLD),
            new NoEnergyGenerator(EntityId.WORLD), Collections.emptyList(), null);

    private static final State destroyed = new State("destroyed");

    /**
     * List of all entity visible by this one.
     */
    @Getter
    private final Set<Entity> visibleEntities = Sets.newSet();

    @Getter
    private final Set<PlayerId> seenBy = Sets.newSet();

    /**
     * Entity unique Id, this value is used as an identifier for equals method.
     */
    @Getter
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
    /**
     * Module managing the base weapon for this entity, cannot be null.
     */
    private final Weapon weapon;
    /**
     * Module managing the base move action for this entity, cannot be null.
     */
    private final MoveEngine moveEngine;
    private final EntityType type;
    @Getter
    @Setter
    private String name;
    @Getter
    @Setter
    @NonNull
    private PlayerId owner;
    @Getter
    private List<Module<? extends Action>> other = Lists.newList();
    @Getter
    private List<Action> actionRunning = Lists.newList();
    @Getter
    private List<Action> actionComplete = Lists.newList();
    private Movable mat;

    private Optional<Action> actionToPrepare = Optional.empty();

    /**
     * Create a new Entity.
     *
     * @param player Owner of this Entity.
     * @param id     Id for this Entity.
     * @param type   Type for this Entity.
     * @requires true
     */
    public BaseEntity(EntityInConstruction e, MoveEngine move, Weapon weapon, Hull hull, EnergyGenerator eg, List<Module<? extends Action>> other, GameMaterialization mat) {
        super();
        this.type = e.getType();
        this.owner = e.getOwner();
        this.id = e.getId();
        this.name = e.getName();
        this.position.setPosition(e.getPosition());
        this.position.setDirection(e.getDirection());
        this.hp.setMax(hull.getMaxHp());
        this.hp.setValue(e.getHp());
        this.energy.setMax(eg.getEnergyMax());
        this.energy.setValue(e.getEnergy());
        this.weapon = weapon;
        this.completeModule(this.weapon);
        this.moveEngine = move;
        this.completeModule(this.moveEngine);
        this.hull = hull;
        this.completeModule(this.hull);
        this.energyGenerator = eg;
        this.completeModule(this.energyGenerator);
        this.other = other;
        this.other.forEach(this::completeModule);
        this.mat = mat;

    }

    public BaseEntity(DefaultEntityInConstruction e, MoveEngine move, Weapon weapon, Hull hull, EnergyGenerator eg, List<Module<? extends Action>> other, GameMaterialization mat) {
        super();
        this.type = e.getType();
        this.owner = e.getOwner();
        this.id = e.getId();
        this.name = e.getType().name;
        this.position.setPosition(e.getPosition());
        this.position.setDirection(e.getDirection());
        this.hp.setValueAndMax(hull.getMaxHp());
        this.energy.setValueAndMax(eg.getEnergyMax());
        this.weapon = weapon;
        this.completeModule(this.weapon);
        this.moveEngine = move;
        this.completeModule(this.moveEngine);
        this.hull = hull;
        this.completeModule(this.hull);
        this.energyGenerator = eg;
        this.completeModule(this.energyGenerator);
        this.other = other;
        this.other.forEach(this::completeModule);
        this.mat = mat;

    }

    /**
     * Fill a module with all this entity shared variable.
     *
     * @param module Module to fill.
     * @requires module != null
     * @ensures module.position == this.position
     * @ensures module.hp == this.hp
     * @ensures module.energy == this.energy
     * @ensures module.states == this.states
     */
    private void completeModule(Module<? extends Action> module) {
        module.setSharedPosition(this.position);
        module.setEnergy(this.energy);
        module.setStates(this.states);
        module.setHp(this.hp);
    }

    @Override
    public void doActions(final long time) {
        this.actionComplete.clear();
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

    @Override
    public Action move(final Point3D destination) {
        Action move = this.moveEngine.getAction();
        move.setDestination(destination);
        move.init();
        this.actionRunning.add(move);
        return move;
    }

    @Override
    public Action attack(final Target target) {
        Action attack = this.weapon.getAction();
        attack.setTarget(target);
        attack.init();
        this.actionRunning.add(attack);
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
    public void removeState(@NonNull final State state) {
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
        if (this.moveEngine.getId() == actionId) {
            return this.moveEngine.getAction();
        }
        if (this.weapon.getId() == actionId) {
            return this.weapon.getAction();
        }
        if (this.hull.getId() == actionId) {
            return this.hull.getAction();
        }
        if (this.energyGenerator.getId() == actionId) {
            return this.energyGenerator.getAction();
        }
        // this.other.stream().filter(module -> module.getId() == actionId).findFirst().orElse(new IllegalArgumentException("actionId is invalid: " + actionId + " for entity " + this.id));
        for (Module<? extends Action> a : this.other) {
            if (a.getId() == actionId) {
                return a.getAction();
            }
        }
        throw new IllegalArgumentException("actionId is invalid: " + actionId + " for entity " + this.id);
    }

    @Override
    public void delete() {
        this.moveEngine.delete();
        this.weapon.delete();
        this.energyGenerator.delete();
        this.other.stream().forEach(Module::delete);
        this.hull.delete();
        this.mat.delete();
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
    public void setPosition(@NonNull Point3D position) {
        //FIXME use the module to set the position instead, to avoid to be able to set the position even if the module cannot move.
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
        } else {
            for (Module<? extends Action> m : this.other) {
                if (m.getId().equals(action)) {
                    Action a = m.getAction();
                    a.setTarget(target);
                    a.setDestination(pos);
                    a.init();
                    this.actionRunning.add(a);
                }
            }
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
        // FIXME HIGH wrong value (hard coded)
        return new ViewDistance(500);
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

    //FIXME do not expose actions outside of this entity.
//    @Override
//    public Move getMoveAction() {
//        return this.moveEngine.getAction();
//    }

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
    public Modules getModules() {
        List<ActionId> ids = Lists.newList();
        ids.add(this.moveEngine.getId());
        ids.add(this.weapon.getId());
        ids.add(this.hull.getId());
        ids.add(this.energyGenerator.getId());
        this.other.forEach(a -> ids.add(a.getId()));
        return new Modules(ids);
    }

    @Override
    public Movable getMaterialization() {
        return this.mat;
    }

    @Override
    public boolean hasSameOwnerAs(final Entity e) {
        return this.owner.equals(e.getOwner());
    }

    @Override
    public void prepareAction(Optional<ActionId> action) {
        if (action.isPresent()) {
            this.actionToPrepare = Optional.of(this.getAction(action.get()));
        } else {
            this.actionToPrepare = Optional.empty();
        }

    }

    @Override
    public void setTarget(final Target t) {
        if (!this.actionToPrepare.isPresent()) {
            this.actionToPrepare = Optional.of(this.weapon.getAction());
        }
        this.actionToPrepare.get().setTarget(t);
    }

    @Override
    public void setDestination(final Point3D p) {
        if (!this.actionToPrepare.isPresent()) {
            this.actionToPrepare = Optional.of(this.moveEngine.getAction());
        }
        this.actionToPrepare.get().setDestination(p);
    }

    @Override
    public void startPreparedAction() {
        this.startAction(this.actionToPrepare.get());
    }

    @Override
    public Action getPreparedAction() {
        return this.actionToPrepare.orElse(this.moveEngine.getAction());
    }


}
