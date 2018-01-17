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

import be.yildizgames.common.model.PlayerId;
import be.yildizgames.engine.feature.entity.BaseEntity;
import be.yildizgames.engine.feature.entity.Entity;
import be.yildizgames.engine.feature.entity.LosListener;
import be.yildizgames.engine.feature.player.Player;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Grégory Van den Borre
 */
final class LosManagerTest {

   /* @Test
    void newCollisionTest() {
        EntityManager<BaseEntity> em = new EntityManager<>(BaseEntity.WORLD);
        LosManager<BaseEntity> lm = new LosManager<>(em);
        final Player p1 = PlayerManager.getInstance().createPlayer(PlayerId.valueOf(1), "p1");
        PlayerManager.getInstance().createPlayer(PlayerId.valueOf(2), "p2");
        final Entity vr = Helper.anEntity(5, 1, em);
        final Entity vw = Helper.anEntity(6, 2, em);
        assertFalse(vr.equals(vw));
        TestLostListener tll = new TestLostListener(p1, vr, vw);
        lm.willNotify(tll);
        lm.newCollision(new CollisionResult(vr.getId(), vw.getId()));
        assertTrue(tll.see);
        lm.lostCollision(new CollisionResult(vr.getId(), vw.getId()));
        assertFalse(tll.see);
    }*/

    private static class TestLostListener implements LosListener<BaseEntity> {

        private final Player p1;

        private final Entity vr;

        private final Entity vw;

        private boolean see = false;

        private TestLostListener(Player p1, Entity vr, Entity vw) {
            this.p1 = p1;
            this.vr = vr;
            this.vw = vw;
        }

        @Override
        public void see(BaseEntity viewer, BaseEntity viewed) {
            assertEquals(vr, viewer);
            assertEquals(vw, viewed);
            see = true;
        }

        @Override
        public void playerSee(PlayerId player, BaseEntity viewed) {
            assertEquals(p1.id, player);
            assertEquals(vw, viewed);
        }

        @Override
        public void playerNoLongerSee(PlayerId player, BaseEntity viewed) {
            assertEquals(p1.id, player);
            assertEquals(vw, viewed);
        }

        @Override
        public void noLongerSee(BaseEntity viewer, BaseEntity viewed) {
            assertEquals(vr, viewer);
            assertEquals(vw, viewed);
            see = false;
        }
    }
}
