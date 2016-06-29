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

package be.yildiz.shared.entity.action;

import be.yildiz.common.BoundedValue;
import be.yildiz.common.id.ActionId;
import be.yildiz.common.id.EntityId;
import be.yildiz.common.vector.Point3D;
import be.yildiz.shared.entity.fields.SharedPosition;
import be.yildiz.shared.entity.fields.StateHolder;
import be.yildiz.shared.entity.fields.Target;
import lombok.Getter;
import lombok.NonNull;

/**
 * An action can be done by an Entity, an Entity can do only one action at a time. 2 actions are considered equals if they share the same Entity. An action can have different states, defined by the
 * combination of 2 booleans: <li>Prepare to run(init()): toRun is true, running is false.</li> <li>
 * Running(run(time)): toRun is true, running is true.</li> <li>Stopped(stop()): toRun is false, running is false.</li>
 *
 * @author Grégory Van den Borre
 */
public abstract class Action {

    /**
     * Entity using this action.
     */
    private final EntityId entity;
    /**
     * Passive action will run all the time, and will not notify listeners.
     */
    private final boolean passive;
    /**
     * True if the action is only interacting with the entity itself.
     */
    @Getter
    private final boolean self;
    /**
     * action type, defined by the class.
     */
    public ActionId id;
    protected SharedPosition position;
    protected StateHolder states;
    protected BoundedValue energy;
    protected BoundedValue hp;
    /**
     * <code>true</code> if the action is currently running.
     */
    private boolean running;
    /**
     * <code>true</code> if the action must continue to run.
     */
    private boolean toRun;

    protected Action(@NonNull final EntityId e, final boolean passive) {
        this(e, passive, false);
    }

    protected Action(@NonNull final EntityId e, final boolean passive, final boolean self) {
        super();
        this.entity = e;
        this.passive = passive;
        this.self = self;
    }

    public void setSharedPosition(SharedPosition position) {
        this.position = position;
    }

    public void setStates(final StateHolder states) {
        this.states = states;
    }

    public void setEnergy(final BoundedValue energy) {
        this.energy = energy;
    }

    public void setHp(final BoundedValue hp) {
        this.hp = hp;
    }

    /**
     * @return The used Entity hashCode.
     */
    @Override
    public final int hashCode() {
        return this.entity.hashCode();
    }

    /**
     * 2 Actions are considered equals if they share the same Entity, no matter the action identity or state. This is because 1 Entity can only have one action at a time.
     *
     * @param o Object to test for equality.
     * @return <code>true</code> if the test object is an action with the same Entity as this object.
     */
    @Override
    public final boolean equals(final Object o) {
        if (!(o instanceof Action)) {
            return false;
        }
        return this.entity.equals(((Action) o).entity);
    }

    /**
     * Check if 2 Actions are the same type and coming from the same module.
     *
     * @param a Other action to test.
     * @return <code>true</code> if the 2 actions have the same type.
     */
    public final boolean isSameType(final Action a) {
        return this.id.equals(a.id);
    }

    /**
     * Run the action, to stop the action, the stop method must be called, anyway in case of passive action, any call to stop will be ignored, the only way to stop the action is not satisfying the
     * prerequisite condition.
     *
     * @param time Time since the last call.
     * @return <code>true</code> if the action must continue.
     */
    public final boolean run(final long time) {
        if (this.passive && this.checkPrerequisite()) {
            this.running = true;
            this.runImpl(time);
            return true;
        } else if (!this.toRun || !this.checkPrerequisite()) {
            this.running = false;
            return false;
        } else {
            this.running = true;
            this.runImpl(time);
            return true;
        }
    }

    /**
     * Initialize the action before running it.
     */
    public final void init() {
        this.toRun = true;
        this.initImpl();
    }

    /**
     * Passive action will run all the time, and will not notify listeners.
     *
     * @return <code>true</code> if this action is passive, <code>false</code> otherwise.
     */
    public final boolean isPassive() {
        return this.passive;
    }

    /**
     * @return <code>true</code> if the action is currently in running state.
     */
    public final boolean isRunning() {
        return this.running;
    }

    /**
     * Run the action.
     *
     * @param time Time since the last call.
     */
    protected abstract void runImpl(long time);

    /**
     * Check if the prerequisite to start or continue the action is filled.
     *
     * @return <code>true</code> if the action can start or continue.
     */
    public abstract boolean checkPrerequisite();

    /**
     * Initialize the action before running it.
     */
    protected abstract void initImpl();

    /**
     * @return The action destination.
     */
    public abstract Point3D getDestination();

    /**
     * Set the action destination this can also be use to target a specific zone.
     *
     * @param destination Destination to set.
     */
    public abstract void setDestination(Point3D destination);

    /**
     * Set a target for this action to apply on.
     *
     * @param entity Entity to target.
     */
    public abstract void setTarget(Target entity);

    /**
     * @return The action target.
     */
    public abstract EntityId getTargetId();

    /**
     * Stop the action.
     */
    public final void stop() {
        this.toRun = false;
        this.running = false;
        this.stopImpl();
    }

    protected abstract void stopImpl();

    public abstract void delete();

    public final void setId(ActionId id) {
        this.id = id;
    }
}
