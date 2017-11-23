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
import be.yildizgames.engine.feature.entity.module.ModuleGroup;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static be.yildiz.helper.Helper.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author Grégory Van den Borre
 */
class DefaultEntityInConstructionTest {

    public static final ModuleGroup MODULES_OK = new ModuleGroup
            .ModuleGroupBuilder()
            .withMove(ActionId.valueOf(1))
            .withHull(ActionId.valueOf(2))
            .withInteraction(ActionId.valueOf(3))
            .withEnergy(ActionId.valueOf(4))
            .withDetector(ActionId.valueOf(5))
            .withNoAdditional()
            .build();

    @Nested
    class Constructor {

        @Test
        void happyFlow() {
            DefaultEntityInConstruction d = new DefaultEntityInConstruction(TYPE_OK, ID_OK, OWNER_OK, MODULES_OK, POSITION_OK, DIRECTION_OK);
            assertEquals(TYPE_OK, d.getType());
            assertEquals(ID_OK, d.getId());
            assertEquals(OWNER_OK, d.getOwner());
            assertEquals(MODULES_OK, d.getModules());
            assertEquals(POSITION_OK, d.getPosition());
            assertEquals(DIRECTION_OK, d.getDirection());
        }

        @Test
        void withNullType() {
            assertThrows(AssertionError.class, () -> new DefaultEntityInConstruction(null, ID_OK, OWNER_OK, MODULES_OK, POSITION_OK, DIRECTION_OK));
        }

        @Test
        void withNullId() {
            assertThrows(AssertionError.class, () -> new DefaultEntityInConstruction(TYPE_OK, null, OWNER_OK, MODULES_OK, POSITION_OK, DIRECTION_OK));
        }

        @Test
        void withNullOwner() {
            assertThrows(AssertionError.class, () -> new DefaultEntityInConstruction(TYPE_OK, ID_OK, null, MODULES_OK, POSITION_OK, DIRECTION_OK));
        }
    }
}
