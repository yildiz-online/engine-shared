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

package be.yildiz.shared.entity2;

import be.yildiz.common.id.EntityId;
import be.yildiz.common.vector.Point3D;
import be.yildiz.shared.entity.action.Move;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author Grégory Van den Borre
 */
public class MoveTest {

    @Test
    public void testConstructor() {
        Move m = new DummyMove();
        Assert.assertFalse(m.isPassive());
        Assert.assertFalse(m.isSelf());
        Assert.assertEquals(EntityId.WORLD, m.getTargetId());
        Assert.assertEquals(Point3D.ZERO, m.getDestination());
    }

    @Test
    public void testSetDestination() {
        Move m = new DummyMove();
        m.setDestination(Point3D.xyz(1, 2, 3));
        Assert.assertEquals(Point3D.xyz(1, 2, 3), m.getDestination());
    }

    @Test(expected = NullPointerException.class)
    public void testSetDestinationNull() {
        Move m = new DummyMove();
        m.setDestination(null);
    }

    /**
     * Empty implementation.
     */
    private static final class DummyMove extends Move {

        /**
         * Create a new Move action.
         */
        protected DummyMove() {
            super(EntityId.get(5L));
        }

        @Override
        protected void runImpl(long time) {

        }

        @Override
        public boolean checkPrerequisite() {
            return true;
        }

        @Override
        protected void stopImpl() {

        }

        @Override
        public void delete() {

        }
    }
}
