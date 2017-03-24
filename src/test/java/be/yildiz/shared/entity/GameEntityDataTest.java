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

package be.yildiz.shared.entity;

import be.yildiz.common.id.ActionId;
import be.yildiz.common.util.Time;
import be.yildiz.shared.data.EntityType;
import be.yildiz.shared.data.Instance;
import be.yildiz.shared.data.Level;
import be.yildiz.shared.data.TimeToBuild;
import be.yildiz.shared.entity.module.DefaultModuleProvider;
import be.yildiz.shared.entity.module.ModuleGroup;
import be.yildiz.shared.entity.module.ModulesAllowed;
import be.yildiz.shared.resources.ResourceValue;
import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

/**
 * @author Grégory Van den Borre
 */
@RunWith(Enclosed.class)
public class GameEntityDataTest {

    private static final EntityType TYPE_OK = new EntityType(5, "test");

    private static final int SIZE_OK = 10;

    private static final int SIZE_NOT_OK = -1;

    private static final Instance INSTANCE_OK = Instance.UNIQUE;

    private static final Level LEVEL_OK = Level.ONE;

    private static final DefaultModuleProvider DEFAULT_MODULE_PROVIDER = new TestDefaultModuleProvider();

    private static final ModulesAllowed ALLOWED_OK = new ModulesAllowed()
            .move(ActionId.get(1))
            .detector(ActionId.get(2))
            .energy(ActionId.get(3))
            .hull(ActionId.get(4))
            .weapon(ActionId.get(5))
            .other(ActionId.get(6), ActionId.get(7), ActionId.get(8));

    private static final ResourceValue PRICE_OK = new ResourceValue(new float[]{100,100,100});

    private static final TimeToBuild TIME_OK = new TimeToBuild(Time.seconds(5));

    public static class Constructor {

        @Test
        public void happyFlow() {
            GameEntityData d = new GameEntityData(TYPE_OK, SIZE_OK, INSTANCE_OK, LEVEL_OK, DEFAULT_MODULE_PROVIDER, ALLOWED_OK, PRICE_OK, TIME_OK, true);
            Assert.assertEquals(TYPE_OK, d.getType());
            Assert.assertEquals(SIZE_OK, d.getSize());
            Assert.assertEquals(INSTANCE_OK, d.getMaxInstances());
            Assert.assertEquals(LEVEL_OK, d.getRequiredLevel());
            Assert.assertEquals(ALLOWED_OK, d.getModulesAllowed());
            Assert.assertEquals(PRICE_OK, d.getPrice());
            Assert.assertEquals(TIME_OK, d.getTimeToBuild());
            Assert.assertTrue(d.isBuildable());

            d = new GameEntityData(TYPE_OK, SIZE_OK, INSTANCE_OK, LEVEL_OK, DEFAULT_MODULE_PROVIDER, ALLOWED_OK, PRICE_OK, TIME_OK, false);
            Assert.assertFalse(d.isBuildable());
        }

        @Test(expected = IllegalArgumentException.class)
        public void withNegativeSize() {
            new GameEntityData(TYPE_OK, SIZE_NOT_OK, INSTANCE_OK, LEVEL_OK, DEFAULT_MODULE_PROVIDER, ALLOWED_OK, PRICE_OK, TIME_OK, true);
        }

        @Test(expected = AssertionError.class)
        public void withNullType() {
            new GameEntityData(null, SIZE_NOT_OK, INSTANCE_OK, LEVEL_OK, DEFAULT_MODULE_PROVIDER, ALLOWED_OK, PRICE_OK, TIME_OK, true);
        }

        @Test(expected = AssertionError.class)
        public void withNullInstance() {
            new GameEntityData(TYPE_OK, SIZE_NOT_OK, null, LEVEL_OK, DEFAULT_MODULE_PROVIDER, ALLOWED_OK, PRICE_OK, TIME_OK, true);
        }

        @Test(expected = AssertionError.class)
        public void withNullLevel() {
            new GameEntityData(TYPE_OK, SIZE_NOT_OK, INSTANCE_OK, null, DEFAULT_MODULE_PROVIDER, ALLOWED_OK, PRICE_OK, TIME_OK, true);
        }

        @Test(expected = AssertionError.class)
        public void withNullAllowed() {
            new GameEntityData(TYPE_OK, SIZE_NOT_OK, INSTANCE_OK, LEVEL_OK, DEFAULT_MODULE_PROVIDER, null, PRICE_OK, TIME_OK, true);
        }

        @Test(expected = AssertionError.class)
        public void withNullPrice() {
            new GameEntityData(TYPE_OK, SIZE_NOT_OK, INSTANCE_OK, LEVEL_OK, DEFAULT_MODULE_PROVIDER, ALLOWED_OK, null, TIME_OK, true);
        }

        @Test(expected = AssertionError.class)
        public void withNullTime() {
            new GameEntityData(TYPE_OK, SIZE_NOT_OK, INSTANCE_OK, LEVEL_OK, DEFAULT_MODULE_PROVIDER, ALLOWED_OK, PRICE_OK, null, true);
        }

    }

    private static class TestDefaultModuleProvider implements DefaultModuleProvider {

        @Override
        public ModuleGroup getModules() {
            return new ModuleGroup
                    .ModuleGroupBuilder()
                    .withMove(ActionId.get(1))
                    .withDetector(ActionId.get(2))
                    .withEnergy(ActionId.get(3))
                    .withHull(ActionId.get(4))
                    .withInteraction(ActionId.get(5))
                    .withAdditional1(ActionId.get(6))
                    .withAdditional2(ActionId.get(7))
                    .withAdditional3(ActionId.get(8))
                    .build();
        }
    }

}
