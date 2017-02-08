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
import be.yildiz.helper.Helper;
import be.yildiz.shared.entity.BaseEntity;
import be.yildiz.shared.entity.Entity;
import be.yildiz.shared.entity.EntityManager;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * @author Grégory Van den Borre
 */
public class EntityManagerTest {

    @Rule
    public final ExpectedException rule = ExpectedException.none();

    @Test
    public void testEntityManager() {
    }

    @Test
    public void testRemoveEntity() {
        EntityManager<Entity> em = new EntityManager<>(BaseEntity.WORLD);
        Entity e = Helper.anEntity(2, 5);
        em.addEntity(e);
        Assert.assertEquals(e, em.findById(EntityId.get(2L)));
        em.removeEntity(e);
        Assert.assertEquals(BaseEntity.WORLD, em.findById(EntityId.get(2L)));
    }

    /**
     * Test for removeEntity(Entity) entity is not nil.
     */

    @Test
    public void testNullEntity() {
        EntityManager<Entity> em = new EntityManager<>(BaseEntity.WORLD);
        this.rule.expect(NullPointerException.class);
        Entity e = null;
        em.removeEntity(e);
    }

    @Test
    public void testAddVisibleUnit() {
    }

    @Test
    public void testRemoveVisibleUnit() {
    }

    @Test
    public void testSee() {
    }

    @Test
    public void testGetNumberOfEntities() {
    }

    @Test
    public void testGetEntities() {
    }

    @Test
    public void testAddEntity() {
    }

    @Test
    public void testAddCity() {
    }

    @Test
    public void testAddBonus() {
    }

    @Test
    public void testSetOwnerEntityPlayer() {
    }


}
