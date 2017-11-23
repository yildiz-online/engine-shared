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

import be.yildiz.common.id.ActionId;
import be.yildiz.common.id.EntityId;
import be.yildiz.common.vector.Point3D;
import be.yildiz.helper.Helper;
import be.yildizgames.engine.feature.entity.action.Action;
import be.yildizgames.engine.feature.entity.fields.Target;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author Grégory Van den Borre
 */
class ActionTest {

    @Test
    void testHashCode() {
    }

    /**
     * Test method for {@link be.yildizgames.engine.feature.entity.action.Action#equals(java.lang.Object)}.
     */
    @Test
    void testEqualsObject() {
        Action a = new ActionMock();
        Action b = new Action(ActionId.WORLD, Helper.anEntity(5, 5).getId(), false) {

            @Override
            public void setTarget(Target target) {
            }

            @Override
            public void setDestination(Point3D destination) {
            }

            @Override
            protected void runImpl(long time) {
            }

            @Override
            protected void initImpl() {
            }

            @Override
            public EntityId getTargetId() {
                return EntityId.WORLD;
            }

            @Override
            public Point3D getDestination() {
                return null;
            }

            @Override
            public boolean checkPrerequisite() {
                return false;
            }

            @Override
            public void stopImpl() {
            }

            @Override
            public void delete() {
                // TODO Auto-generated method stub

            }
        };
        assertEquals(a, b);
        Action c = new ActionMock();
        assertEquals(a, c);
        assertNotEquals(a, null);
        assertNotEquals(a, new ActionMock(Helper.anEntity(3, 5)));
    }

    /**
     * Test method for {@link be.yildizgames.engine.feature.entity.action.Action#isSameType(be.yildizgames.engine.feature.entity.action.Action)} .
     */
    @Test
    void testIsSameType() {
        Action a = new ActionMock();
        Action b = new ActionMock();
        assertTrue(a.isSameType(b));
        Action c = new Action(ActionId.valueOf(1), Helper.anEntity(4, 5).getId(), false) {

            @Override
            public void setTarget(Target target) {
            }

            @Override
            protected void runImpl(long time) {
            }

            @Override
            protected void initImpl() {
            }

            @Override
            public EntityId getTargetId() {
                return EntityId.WORLD;
            }

            @Override
            public Point3D getDestination() {
                return Point3D.ZERO;
            }

            @Override
            public void setDestination(Point3D destination) {
            }

            @Override
            public boolean checkPrerequisite() {
                return false;
            }

            @Override
            public void stopImpl() {
            }

            @Override
            public void delete() {
                // TODO Auto-generated method stub

            }
        };
        assertFalse(a.isSameType(c));
    }

    /**
     * Test method for {@link be.yildizgames.engine.feature.entity.action.Action#checkPrerequisite()}.
     */
    @Test
    void testCheckPrerequisite() {
    }

    /**
     * Test method for {@link be.yildizgames.engine.feature.entity.action.Action#run(long)}.
     */
    @Test
    void testRun() {
    }

    /**
     * Test method for {@link be.yildizgames.engine.feature.entity.action.Action#isPassive()}.
     */
    @Test
    void testIsPassive() {
    }

    /**
     * Test method for {@link be.yildizgames.engine.feature.entity.action.Action#init()}.
     */
    @Test
    void testInit() {
    }

    /**
     * Test method for {@link be.yildizgames.engine.feature.entity.action.Action#setDestination(be.yildiz.common.vector.Point3D)} .
     */
    @Test
    void testSetDestination() {
    }

    /**
     * Test method for {@link be.yildizgames.engine.feature.entity.action.Action#getDestination()}.
     */
    @Test
    void testGetDestination() {
    }

    @Test
    void testSetTarget() {

    }

    @Test
    void testGetTarget() {
    }

    /**
     * Test method for {@link be.yildizgames.engine.feature.entity.action.Action#stop()}.
     */
    @Test
    void testStop() {
        Action a = new ActionMock();
        a.init();
        a.run(0);
        assertTrue(a.isRunning());
        a.stop();
        a.run(0);
        assertFalse(a.isRunning());
        a.init();
        a.run(0);
        assertTrue(a.isRunning());
    }

    /**
     * Test method for {@link be.yildizgames.engine.feature.entity.action.Action#isRunning()}.
     */
    @Test
    void testIsRunning() {
        Action a = new ActionMock();
        a.init();
        a.run(0);
        assertTrue(a.isRunning());
        Action b = new ActionMock();
        b.run(0);
        assertFalse(b.isRunning());
    }

    @Test
    void testActionNullEntity() {
        assertThrows(NullPointerException.class, () -> new ActionMock(null));
    }

    @Test
    void testAction() {
        new ActionMock();
    }

}
