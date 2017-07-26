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

import be.yildiz.shared.entity.Entity;
import be.yildiz.shared.player.Player;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mockito;

/**
 * @author Grégory Van den Borre
 */
public final class LosManagerTest {

    @Ignore
    @Test
    public void newCollisionTest() {

        // LosManager<Entity, GameEntityData> lm = new LosManager<BaseEntity, GameEntityData>(new EntityManager<Entity, GameEntityData>(), new PlayerManager());
        final Player p1 = Mockito.mock(Player.class);
        final Player p2 = Mockito.mock(Player.class);
        final Entity vr = Mockito.mock(Entity.class);
        final Entity vw = Mockito.mock(Entity.class);
        Assert.assertFalse(vr.equals(vw));
//        lm.willNotify(new LosListener<Entity>() {
//            @Override
//            public void see(Entity viewer, Entity viewed) {
//                Assert.assertEquals(vr, viewer);
//                Assert.assertEquals(vw, viewed);
//            }
//
//            @Override
//            public void playerSee(PlayerId player, Entity viewed) {
//                Assert.assertEquals(p1, player);
//                Assert.assertEquals(vw, viewed);
//            }
//
//            @Override
//            public void playerNoLongerSee(Player player, Entity viewed) {
//                Assert.assertEquals(p1, player);
//                Assert.assertEquals(vw, viewed);
//            }
//
//            @Override
//            public void noLongerSee(Entity viewer, Entity viewed) {
//                Assert.assertEquals(vr, viewer);
//                Assert.assertEquals(vw, viewed);
//            }
//        });
//        lm.newCollision(new CollisionResult(EntityId.get(1), EntityId.get(2)));
//        lm.lostCollision(new CollisionResult(EntityId.get(1), EntityId.get(2)));

    }
}
