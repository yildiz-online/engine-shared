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

import be.yildiz.common.id.EntityId;
import be.yildiz.common.vector.Point3D;
import be.yildiz.shared.entity.fields.Target;

/**
 * Base class for actions without interactions.
 *
 * @author Grégory Van den Borre
 */
public abstract class AbstractNoInteractionAction extends Action {

    /**
     * Build a new no interaction action.
     *
     * @param e       Entity using the action.
     * @param id      action unique Id.
     * @param passive Is the action passive or active?
     */
    protected AbstractNoInteractionAction(final EntityId e, final boolean passive) {
        super(e, passive);
    }

    protected AbstractNoInteractionAction(final EntityId e, final boolean passive, boolean self) {
        super(e, passive, self);
    }

    /**
     * @return World.
     */
    @Override
    public final EntityId getTargetId() {
        return EntityId.WORLD;
    }

    /**
     * @return This entity position.
     */
    @Override
    public final Point3D getDestination() {
        return this.position.getPosition();
    }

    @Override
    public final void setDestination(final Point3D destination) {
    }

    @Override
    public final void setTarget(final Target target) {
    }
}
