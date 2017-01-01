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
import be.yildiz.shared.entity.BaseEntity;
import be.yildiz.shared.entity.action.Action;
import be.yildiz.shared.entity.fields.Target;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * @author Grégory Van den Borre
 */
public class ActionTest {

    @Rule
    public final ExpectedException rule = ExpectedException.none();

    /**
     * Test method for {@link be.yildiz.shared.entity.action.Action#hashCode()}.
     */
    @Test
    public void testHashCode() {
    }

    /**
     * Test method for {@link be.yildiz.shared.entity.action.Action#equals(java.lang.Object)}.
     */
    @Test
    public void testEqualsObject() {
        Action a = new ActionMock();
        Action b = new Action(Helper.anEntity(5, 5).getId(), false) {

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
        Assert.assertEquals(a, b);
        Action c = new ActionMock();
        Assert.assertEquals(a, c);
        Assert.assertNotEquals(a, null);
        Assert.assertNotEquals(a, new ActionMock(Helper.anEntity(3, 5)));
    }

    /**
     * Test method for {@link be.yildiz.shared.entity.action.Action#isSameType(be.yildiz.shared.entity.action.Action)} .
     */
    @Test
    public void testIsSameType() {
        Action a = new ActionMock();
        a.setId(ActionId.get(5));
        Action b = new ActionMock();
        b.setId(ActionId.get(5));
        Assert.assertTrue(a.isSameType(b));
        Action c = new Action(Helper.anEntity(4, 5).getId(), false) {

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
                return null;
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
        c.setId(ActionId.get(4));
        Assert.assertFalse(a.isSameType(c));
    }

    /**
     * Test method for {@link be.yildiz.shared.entity.action.Action#checkPrerequisite()}.
     */
    @Test
    public void testCheckPrerequisite() {
    }

    /**
     * Test method for {@link be.yildiz.shared.entity.action.Action#run(long)}.
     */
    @Test
    public void testRun() {
    }

    /**
     * Test method for {@link be.yildiz.shared.entity.action.Action#isPassive()}.
     */
    @Test
    public void testIsPassive() {
    }

    /**
     * Test method for {@link be.yildiz.shared.entity.action.Action#init()}.
     */
    @Test
    public void testInit() {
    }

    /**
     * Test method for {@link be.yildiz.shared.entity.action.Action#setDestination(be.yildiz.common.vector.Point3D)} .
     */
    @Test
    public void testSetDestination() {
    }

    /**
     * Test method for {@link be.yildiz.shared.entity.action.Action#getDestination()}.
     */
    @Test
    public void testGetDestination() {
    }

    /**
     * Test method for {@link be.yildiz.shared.entity.action.Action#setTarget(be.yildiz.shared.entity2.BaseEntity)} .
     */
    @Test
    public void testSetTarget() {

    }

    /**
     * Test method for {@link be.yildiz.shared.entity.action.Action#getTarget()}.
     */
    @Test
    public void testGetTarget() {
    }

    /**
     * Test method for {@link be.yildiz.shared.entity.action.Action#stop()}.
     */
    @Test
    public void testStop() {
        Action a = new ActionMock();
        a.init();
        a.run(0);
        Assert.assertTrue("action 'a' is not running", a.isRunning());
        a.stop();
        a.run(0);
        Assert.assertFalse("action 'a' is still running", a.isRunning());
        a.init();
        a.run(0);
        Assert.assertTrue("action 'a' is not running", a.isRunning());
    }

    /**
     * Test method for {@link be.yildiz.shared.entity.action.Action#isRunning()}.
     */
    @Test
    public void testIsRunning() {
        Action a = new ActionMock();
        a.init();
        a.run(0);
        Assert.assertTrue("action 'a' is not running", a.isRunning());
        Action b = new ActionMock();
        b.run(0);
        Assert.assertFalse("action 'b' is running, shouldnt be if not initialized", b.isRunning());
    }

    /**
     * Test method for {@link be.yildiz.shared.entity.action.Action#Action(BaseEntity)}.
     */
    @Test
    public void testActionNullEntity() {
        this.rule.expect(NullPointerException.class);
        new ActionMock(null);
    }

    /**
     * Test method for {@link be.yildiz.shared.entity.action.Action#Action(BaseEntity)}.
     */
    @Test
    public void testAction() {
        new ActionMock();
    }

}
