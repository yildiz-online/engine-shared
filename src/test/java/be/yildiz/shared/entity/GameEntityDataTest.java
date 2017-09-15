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
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Grégory Van den Borre
 */
class GameEntityDataTest {

    private static final EntityType TYPE_OK = new EntityType(5, "test");

    private static final int SIZE_OK = 10;

    private static final int SIZE_NOT_OK = -1;

    private static final Instance INSTANCE_OK = Instance.UNIQUE;

    private static final Level LEVEL_OK = Level.ONE;

    private static final DefaultModuleProvider DEFAULT_MODULE_PROVIDER = new TestDefaultModuleProvider();

    private static final ModulesAllowed ALLOWED_OK = new ModulesAllowed()
            .move(ActionId.valueOf(1))
            .detector(ActionId.valueOf(2))
            .energy(ActionId.valueOf(3))
            .hull(ActionId.valueOf(4))
            .weapon(ActionId.valueOf(5))
            .other(ActionId.valueOf(6), ActionId.valueOf(7), ActionId.valueOf(8));

    private static final ResourceValue PRICE_OK = new ResourceValue(new float[]{100,100,100});

    private static final TimeToBuild TIME_OK = new TimeToBuild(Time.seconds(5));

    @Nested
    class Constructor {

        @Test
        void happyFlow() {
            GameEntityData d = new GameEntityData(TYPE_OK, SIZE_OK, INSTANCE_OK, LEVEL_OK, DEFAULT_MODULE_PROVIDER, ALLOWED_OK, PRICE_OK, TIME_OK, true);
            assertEquals(TYPE_OK, d.getType());
            assertEquals(SIZE_OK, d.getSize());
            assertEquals(INSTANCE_OK, d.getMaxInstances());
            assertEquals(LEVEL_OK, d.getRequiredLevel());
            assertEquals(ALLOWED_OK, d.getModulesAllowed());
            assertEquals(PRICE_OK, d.getPrice());
            assertEquals(TIME_OK, d.getTimeToBuild());
            assertTrue(d.isBuildable());

            d = new GameEntityData(TYPE_OK, SIZE_OK, INSTANCE_OK, LEVEL_OK, DEFAULT_MODULE_PROVIDER, ALLOWED_OK, PRICE_OK, TIME_OK, false);
            assertFalse(d.isBuildable());
        }

        @Test
        void withNegativeSize() {
            assertThrows(AssertionError.class, () -> new GameEntityData(TYPE_OK, SIZE_NOT_OK, INSTANCE_OK, LEVEL_OK, DEFAULT_MODULE_PROVIDER, ALLOWED_OK, PRICE_OK, TIME_OK, true));
        }

        @Test
        void withNullType() {
            assertThrows(AssertionError.class, () -> new GameEntityData(null, SIZE_NOT_OK, INSTANCE_OK, LEVEL_OK, DEFAULT_MODULE_PROVIDER, ALLOWED_OK, PRICE_OK, TIME_OK, true));
        }

        @Test
        void withNullInstance() {
            assertThrows(AssertionError.class, () -> new GameEntityData(TYPE_OK, SIZE_NOT_OK, null, LEVEL_OK, DEFAULT_MODULE_PROVIDER, ALLOWED_OK, PRICE_OK, TIME_OK, true));
        }

        @Test
        void withNullLevel() {
            assertThrows(AssertionError.class, () -> new GameEntityData(TYPE_OK, SIZE_NOT_OK, INSTANCE_OK, null, DEFAULT_MODULE_PROVIDER, ALLOWED_OK, PRICE_OK, TIME_OK, true));
        }

        @Test
        void withNullAllowed() {
            assertThrows(AssertionError.class, () -> new GameEntityData(TYPE_OK, SIZE_NOT_OK, INSTANCE_OK, LEVEL_OK, DEFAULT_MODULE_PROVIDER, null, PRICE_OK, TIME_OK, true));
        }

        @Test
        void withNullPrice() {
            assertThrows(AssertionError.class, () -> new GameEntityData(TYPE_OK, SIZE_NOT_OK, INSTANCE_OK, LEVEL_OK, DEFAULT_MODULE_PROVIDER, ALLOWED_OK, null, TIME_OK, true));
        }

        @Test
        void withNullTime() {
            assertThrows(AssertionError.class, () -> new GameEntityData(TYPE_OK, SIZE_NOT_OK, INSTANCE_OK, LEVEL_OK, DEFAULT_MODULE_PROVIDER, ALLOWED_OK, PRICE_OK, null, true));
        }

    }

    private static class TestDefaultModuleProvider implements DefaultModuleProvider {

        @Override
        public ModuleGroup getModules() {
            return new ModuleGroup
                    .ModuleGroupBuilder()
                    .withMove(ActionId.valueOf(1))
                    .withDetector(ActionId.valueOf(2))
                    .withEnergy(ActionId.valueOf(3))
                    .withHull(ActionId.valueOf(4))
                    .withInteraction(ActionId.valueOf(5))
                    .withAdditional1(ActionId.valueOf(6))
                    .withAdditional2(ActionId.valueOf(7))
                    .withAdditional3(ActionId.valueOf(8))
                    .build();
        }
    }

}
