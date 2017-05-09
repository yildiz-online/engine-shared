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

import be.yildiz.common.gameobject.Movable;
import be.yildiz.common.id.ActionId;
import be.yildiz.common.id.PlayerId;
import be.yildiz.common.vector.Point3D;
import be.yildiz.shared.data.EntityType;
import be.yildiz.shared.data.State;
import be.yildiz.shared.data.ViewDistance;
import be.yildiz.shared.entity.action.AbstractAttack;
import be.yildiz.shared.entity.action.Action;
import be.yildiz.shared.entity.fields.Target;
import be.yildiz.shared.entity.module.Module;
import be.yildiz.shared.entity.module.ModuleGroup;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * An entity is the base component for every usable objects in the game, the
 * implementation will be different server and client side. An entity is owned
 * by a player, it can be the world.
 *
 * @author Grégory Van den Borre
 */
//@specfield id : Id : must be unique through the system, immutable.
//@specfield hitPointMax: int : Maximum value reachable by the hitPoint value.
//@specfield hitPoint : int : Hit point value for this entity, if reaching 0, the entity is considered as destroyed if it is not invincible.
//@specfield energyMax : int : Maximum value reachable by the energy value.
//@specfield energy : int Entity energy point value, can be used to activate actions.
//@specfield position: Point3D: entity current position.
//@specfield direction: Point3D: entity current direction vector, the value is normalized.
//@specfield owner : Player : player owner of this entity, must be existing in the system, can be set.
//@specfield states: Set : Cannot contains null values.
//@invariant id != null.
//@invariant owner != null.
//@invariant 0 <= hitPoint <= hitPointMax.
//@invariant 0 <= energy <= energyMax.
//@invariant position != null.
//@invariant direction != null.
//@invariant direction.x + direction.y + direction.z = 1.
//@rructinvariant !states.contains(null).
public interface Entity extends Target {

    /**
     * @param e Entity to check if visible or not.
     * @return true if the provided entity can be seen by this one.
     */
    //@Requires e != null.
    boolean see(Entity e);

    /**
     * @param time Time since the last call.
     */
    //@Requires time >= 0. Run every actions for this entity.
    void doActions(long time);

    /**
     * @return The id of the player owner of this entity.
     */
    //@Ensures result != null.
    //@Ensures Player.get(result).contains(this) == true
    PlayerId getOwner();

    /**
     * Change the entity owner.
     *
     * @param owner Id of the new owner.
     */
    //@Requires owner != null.
    //@Requires owner is a valid player id.
    //@Ensures this.owner == owner
    void setOwner(PlayerId owner);

    /**
     * Provide this entity type.
     *
     * @return this entity type.
     */
    //@Ensures EntityType != null;
    EntityType getType();

    void setPosition(Point3D position);

    /**
     * Provide this entity hit points.
     *
     * @return The hit points.
     */
    //@Ensures 0 >= result <= getMaxHitPoints
    int getHitPoints();

    void setHitPoints(int hitPoint);

    int getMaxHitPoints();

    /**
     * Provide an action matching its id.
     *
     * @param actionId Id of the action to find.
     * @return The matching action in this entity.
     * @throws IllegalArgumentException If no matching action is found in this entity.
     */
    //@Requires actionId != null.
    //@Ensures result.id == actionId
    Action getAction(ActionId actionId);

    List<Action> getActions();

    boolean isSeeing(Entity unseen);

    /**
     * @return The energy points current value.
     */
    //@Ensures 0 >= result <= getMaxEnergyPoints()
    int getEnergyPoints();

    void setEnergyPoints(int energy);

    /**
     * @return The maximum energy points value.
     */
    //@Ensures result >= 0
    int getMaxEnergyPoints();

    /**
     * @return The entity current direction.
     */
    //@Ensures result != null
    //@Ensures result == entity.direction
    Point3D getDirection();

    void setDirection(Point3D direction);

    /**
     * Check if this entity has a given state.
     *
     * @param state State to check.
     * @return <code>true</code> if this entity has the given state, false
     * otherwise.
     */
    //@Requires state != null
    //@Ensures result == true if this.hull.states.contains(state)
    boolean hasState(State state);

    void removeState(State state);

    void addState(State state);

    void lookAt(Point3D destination);

    void delete();

    boolean isDeleted();

    List<Action> getActionRunning();

    List<Action> getActionDone();

    void startAction(ActionId action, Target target, Point3D pos);

    /**
     * Request this entity to do a move action.
     *
     * @param destination Destination to reach to complete the move.
     * @return The running action.
     */
    Action move(Point3D destination);

    Action attack(Target target);

    void startAction(Action a);

    void stopAttack();

    ViewDistance getLineOfSight();

    /**
     * @return The ratio between the current hit point and the max hit points.
     */
    //@Ensures result == getHitPoints / getMaxHitPoint
    float getHitPointsRatio();

    float getEnergyPointsRatio();

    boolean isAttacking();

    // Move getMoveAction();

    AbstractAttack getAttackAction();

    Action getProtectAction();

    Action getGenerateEnergyAction();

    ModuleGroup getModules();

    Set<PlayerId> getSeenBy();

    boolean hasSameOwnerAs(Entity e);

    /**
     * @return The entity name.
     */
    //@Ensures result != null
    String getName();

    /**
     * Give a name to this entity.
     *
     * @param name Name to set
     * @throws NullPointerException if name == null
     */
    //@Ensures this.name == name
    void setName(String name);

    /**
     * Prepare an action to be executed, the action is optional, if empty, no
     * action will be prepared.
     *
     * @param action Optional action to prepare.
     */
    void prepareAction(Optional<ActionId> action);

    void setTarget(Target t);

    Movable getMaterialization();

    /**
     * Set the action to prepare destination, if there is no action to prepare,
     * the default move action will be used.
     *
     * @param destination Destination coordinates.
     * see <code>prepareAction(Optional ActionId action);</code>
     */
    //@prerequisites destination != null
    //@ensures this.actionToPrepare.isPresent() ? this.actionToPrepare.destination == destination : this.move.destination == destination
    void setDestination(Point3D destination);

    void startPreparedAction();

    Action getPreparedAction();

    Module getAdditional1();

    Module getAdditional2();

    Module getAdditional3();
}
