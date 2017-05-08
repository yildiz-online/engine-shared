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

package be.yildiz.shared.entity.action;

import be.yildiz.common.id.ActionId;
import be.yildiz.common.id.EntityId;
import be.yildiz.common.vector.Point3D;
import be.yildiz.shared.entity.fields.MutableSpeed;
import be.yildiz.shared.entity.fields.Target;

/**
 * action to move an entity to a different place.
 *
 * @author Grégory Van den Borre
 */
public abstract class Move extends Action {

    /**
     * Move current speed.
     */
    protected final MutableSpeed speed = new MutableSpeed();
    /**
     * Distance to the destination to consider the move as complete.
     */
    protected float distance;
    /**
     * Acceleration factor.
     */
    protected float acceleration;

    /**
     * Move maximum speed.
     */
    protected float maxSpeed;
    /**
     * Destination to reach.
     */
    private Point3D destination = Point3D.ZERO;

    /**
     * Create a new Move action.
     *
     * @param e Entity executing the action.
     * @param id Action id of the associated module.
     */
    protected Move(final ActionId id, final EntityId e) {
        super(id, e, false);
    }

    /**
     * Reinitialize the distance to avoid shared value with a previous move.
     */
    @Override
    protected final void initImpl() {
        this.distance = 0;
    }

    /**
     * Set the destination being the current target position.
     *
     * @param target Target to retrieve position.
     */
    @Override
    public final void setTarget(final Target target) {
        this.setDestination(target.getPosition());
    }

    @Override
    public final EntityId getTargetId() {
        return EntityId.WORLD;
    }

    @Override
    public Point3D getDestination() {
        return destination;
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }

    /**
     * Set a new destination.
     *
     * @param d New destination coordinates.
     * @throws AssertionError if the destination is null.
     */
    @Override
    public final void setDestination( final Point3D d) {
        assert d != null;
        this.destination = d;
    }

    public void setAcceleration(float acceleration) {
        this.acceleration = acceleration;
    }

    public void setMaxSpeed(float maxSpeed) {
        this.maxSpeed = maxSpeed;
    }
}
