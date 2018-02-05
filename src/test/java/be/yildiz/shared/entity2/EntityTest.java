/*
 * This file is part of the Yildiz-Engine project, licenced under the MIT License  (MIT)
 *
 *  Copyright (c) 2018 Grégory Van den Borre
 *
 *  More infos available: https://www.yildiz-games.be
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 *  documentation files (the "Software"), to deal in the Software without restriction, including without
 *  limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 *  of the Software, and to permit persons to whom the Software is furnished to do so,
 *  subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be included in all copies or substantial
 *  portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 *  WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS
 *  OR COPYRIGHT  HOLDERS BE LIABLE FOR ANY CLAIM,
 *  DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE  SOFTWARE.
 *
 */

package be.yildiz.shared.entity2;

/**
 * @author Grégory Van den Borre
 */
class EntityTest {

   /* @Test
    void testHashCode() {
        Entity e1 = Helper.anEntity(6);
        assertEquals(e1.hashCode(), 6);
        Entity e2 = Helper.anEntity(6, 8);
        assertEquals(e1.hashCode(), e2.hashCode());
    }

    @Test
    void testEntity() {
        // fail("Not yet implemented"); // TODO
    }

    @Test
    void testLookAt() {
        Entity e1 = Helper.givenAnEntity();
        e1.setPosition(Point3D.ZERO);
        e1.lookAt(Point3D.valueOfXZ(100));
        assertEquals(Point3D.valueOfXZ(100), e1.getDirection());
        assertThrows(NullPointerException.class, () -> e1.lookAt(null));
    }

    @Test
    void testReduceEnergy() {
        // fail("Not yet implemented"); // TODO
    }

    @Test
    void testAddHitResult() {
        // fail("Not yet implemented"); // TODO
    }

    @Test
    void testEqualsObject() {
        // fail("Not yet implemented"); // TODO
    }

    @Test
    void testAddHasRemoveState() {
        State s1 = new State("s1");
        State s2 = new State("s2");
        Entity e1 = Helper.givenAnEntity();
        e1.addState(s1);
        assertTrue(e1.hasState(s1));
        assertFalse(e1.hasState(s2));
        e1.addState(s2);
        assertTrue(e1.hasState(s1));
        assertTrue(e1.hasState(s2));
        e1.removeState(s1);
        assertFalse(e1.hasState(s1));
        assertThrows(AssertionError.class, () -> e1.addState(null));
        assertThrows(AssertionError.class, () -> e1.removeState(null));
    }

    @Test
    void testGetSetModuleHolder() {
        // fail("Not yet implemented"); // TODO
    }

    @Test
    void testGetVisibleEntities() {
        // fail("Not yet implemented"); // TODO
    }

    @Test
    void testGetRemoveViewers() {
        // fail("Not yet implemented"); // TODO
    }

    @Test
    void testGetHitResults() {
        // fail("Not yet implemented"); // TODO
    }

    @Test
    void testGetId() {
        Entity e1 = Helper.givenAnEntity();
        assertEquals(EntityId.valueOf(5L), e1.getId());
    }

    @Test
    void testGetType() {
        Entity e1 = Helper.givenAnEntity();
        assertEquals(EntityType.valueOf(0), e1.getType());
    }

    @Test
    void testGetSetPosition() {
        Entity e1 = Helper.givenAnEntity();
        e1.setPosition(Point3D.valueOf(10));
        assertEquals(Point3D.valueOf(10), e1.getPosition());
        assertThrows(AssertionError.class, () -> e1.setPosition(null));
    }

    @Test
    void testGetSetDirection() {
        Entity e1 = Helper.givenAnEntity();
        e1.setDirection(Point3D.valueOf(10));
        assertEquals(Point3D.valueOf(10), e1.getDirection());
    }

    @Test
    void testGetSetHitPoint() {
        Entity e1 = Helper.givenAnEntity();
        assertNotNull(e1.getHitPoints());
    }

    @Test
    void testGetSetEnergy() {
        Entity e1 = Helper.givenAnEntity();
        assertNotNull(e1.getEnergyPoints());
    }

    @Test
    void testGetSetOwner() {
        Player p = PlayerManager.getInstance().createPlayer(PlayerId.valueOf(5), "test");
        Entity e1 = Helper.anEntity(2, 5);
        assertEquals(p.id, e1.getOwner());
        Player p2 = PlayerManager.getInstance().createPlayer(PlayerId.valueOf(6), "test2");
        e1.setOwner(p2.id);
        assertEquals(p2.id, e1.getOwner());
        assertThrows(AssertionError.class, () -> e1.setOwner(null));
    }*/
}
