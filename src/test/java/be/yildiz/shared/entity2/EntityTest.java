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
import be.yildiz.common.id.PlayerId;
import be.yildiz.common.vector.Point3D;
import be.yildiz.helper.Helper;
import be.yildiz.shared.data.EntityType;
import be.yildiz.shared.data.State;
import be.yildiz.shared.entity.Entity;
import be.yildiz.shared.player.Player;
import be.yildiz.shared.player.PlayerManager;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * @author Grégory Van den Borre
 */
public class EntityTest {

    @Rule
    public final ExpectedException rule = ExpectedException.none();

    @Test
    public void testHashCode() {
        Entity e1 = Helper.anEntity(6);
        Assert.assertEquals(e1.hashCode(), 6);
        Entity e2 = Helper.anEntity(6, 8);
        Assert.assertEquals(e1.hashCode(), e2.hashCode());
    }

    @Test
    public void testEntity() {
        // fail("Not yet implemented"); // TODO
    }

    @Test
    public void testLookAt() {
        Entity e1 = Helper.givenAnEntity();
        e1.setPosition(Point3D.ZERO);
        e1.lookAt(Point3D.valueOfXZ(100));
        Assert.assertEquals(Point3D.valueOfXZ(100), e1.getDirection());
        this.rule.expect(NullPointerException.class);
        e1.lookAt(null);
    }

    @Test
    public void testReduceEnergy() {
        // fail("Not yet implemented"); // TODO
    }

    @Test
    public void testAddHitResult() {
        // fail("Not yet implemented"); // TODO
    }

    @Test
    public void testEqualsObject() {
        // fail("Not yet implemented"); // TODO
    }

    @Test
    public void testAddHasRemoveState() {
        State s1 = new State("s1");
        State s2 = new State("s2");
        Entity e1 = Helper.givenAnEntity();
        e1.addState(s1);
        Assert.assertTrue(e1.hasState(s1));
        Assert.assertFalse(e1.hasState(s2));
        e1.addState(s2);
        Assert.assertTrue(e1.hasState(s1));
        Assert.assertTrue(e1.hasState(s2));
        e1.removeState(s1);
        Assert.assertFalse(e1.hasState(s1));
        this.rule.expect(AssertionError.class);
        e1.addState(null);
        this.rule.expect(AssertionError.class);
        e1.removeState(null);
    }

    @Test
    public void testGetSetModuleHolder() {
        // fail("Not yet implemented"); // TODO
    }

    @Test
    public void testGetVisibleEntities() {
        // fail("Not yet implemented"); // TODO
    }

    @Test
    public void testGetRemoveViewers() {
        // fail("Not yet implemented"); // TODO
    }

    @Test
    public void testGetHitResults() {
        // fail("Not yet implemented"); // TODO
    }

    @Test
    public void testGetId() {
        Entity e1 = Helper.givenAnEntity();
        Assert.assertEquals(EntityId.valueOf(5L), e1.getId());
    }

    @Test
    public void testGetType() {
        Entity e1 = Helper.givenAnEntity();
        Assert.assertEquals(EntityType.valueOf(0), e1.getType());
    }

    @Test
    public void testGetSetPosition() {
        Entity e1 = Helper.givenAnEntity();
        e1.setPosition(Point3D.valueOf(10));
        Assert.assertEquals(Point3D.valueOf(10), e1.getPosition());
        this.rule.expect(AssertionError.class);
        e1.setPosition(null);
    }

    @Test
    public void testGetSetDirection() {
        Entity e1 = Helper.givenAnEntity();
        e1.setDirection(Point3D.valueOf(10));
        Assert.assertEquals(Point3D.valueOf(10), e1.getDirection());
    }

    @Test
    public void testGetSetHitPoint() {
        Entity e1 = Helper.givenAnEntity();
        Assert.assertNotNull(e1.getHitPoints());
    }

    @Test
    public void testGetSetEnergy() {
        Entity e1 = Helper.givenAnEntity();
        Assert.assertNotNull(e1.getEnergyPoints());
    }

    @Test
    public void testGetSetOwner() {
        PlayerManager pm = new PlayerManager();
        Player p = pm.createPlayer(PlayerId.valueOf(5), "test");
        Entity e1 = Helper.anEntity(2, 5);
        Assert.assertEquals(p.id, e1.getOwner());
        Player p2 = pm.createPlayer(PlayerId.valueOf(6), "test2");
        e1.setOwner(p2.id);
        Assert.assertEquals(p2.id, e1.getOwner());
        this.rule.expect(AssertionError.class);
        e1.setOwner(null);
    }
}
