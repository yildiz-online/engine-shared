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
import lombok.Setter;

import java.util.Optional;

/**
 * @author Grégory Van den Borre
 */
public final class Follow extends Action {

    private final Move move;

    private Optional<Target> target;

    @Setter
    private float distance;

    /**
     * Create a new Follow action.
     *
     * @param move   Move action used to follow a target.
     * @param entity Entity doing this action.
     */
    public Follow(final Move move, final EntityId entity) {
        super(entity, false);
        this.move = move;
        this.target = Optional.empty();
    }

    @Override
    public boolean checkPrerequisite() {
        return this.target.isPresent() && !this.target.get().isZeroHp();
    }

    @Override
    public void runImpl(final long time) {
        this.move.setDestination(this.target.get().getPosition());
        this.move.run(time);
    }

    @Override
    public void setTarget(final Target target) {
        this.target = Optional.of(target);
    }

    @Override
    public Point3D getDestination() {
        return this.target.get().getPosition();
    }

    @Override
    public void setDestination(final Point3D destination) {
    }

    @Override
    public EntityId getTargetId() {
        return this.target.get().getId();
    }

    @Override
    public void stopImpl() {
        this.move.stop();
    }

    @Override
    public void initImpl() {
        this.move.init();
        this.move.setDistance(this.distance);
    }

    @Override
    public void delete() {

    }
}
