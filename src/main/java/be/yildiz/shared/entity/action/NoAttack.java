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
import be.yildiz.shared.entity.fields.Target;

/**
 * Attack implementation with no attack behavior.
 *
 * @author Grégory Van den Borre
 */
public class NoAttack extends AbstractAttack {

    /**
     * Create a new NoAttack action.
     *
     * @param attacker Associated entity.
     * @param id Action id of the associated module.
     */
    public NoAttack(final EntityId attacker, ActionId id) {
        super(attacker, id);
    }

    /**
     * Does nothing.
     */
    @Override
    protected void fire() {
        //does nothing
    }

    /**
     * Does nothing.
     */
    @Override
    protected void stopFire() {
        //does nothing
    }

    /**
     * @return <code>false</code>
     */
    @Override
    public boolean checkPrerequisite() {
        return false;
    }

    /**
     * Does nothing.
     *
     * @param target Unused.
     */
    @Override
    public void setTarget(final Target target) {
        //does nothing
    }

    /**
     * @return The associated entity position.
     */
    @Override
    public Point3D getDestination() {
        return this.position.getPosition();
    }

    /**
     * Does nothing.
     *
     * @param destination Unused.
     */
    @Override
    public void setDestination(final Point3D destination) {
        //does nothing
    }

    /**
     * @return EntityId.WORLD.
     */
    @Override
    public EntityId getTargetId() {
        return EntityId.WORLD;
    }

    /**
     * Does nothing.
     */
    @Override
    public void stopImpl() {
        //does nothing
    }

    /**
     * Does nothing.
     */
    @Override
    public void initImpl() {
        //does nothing
    }

    /**
     * Does nothing.
     *
     * @param time Unused.
     */
    @Override
    protected void runImpl(final long time) {
        //does nothing
    }

    @Override
    public void delete() {
        //does nothing
    }

}
