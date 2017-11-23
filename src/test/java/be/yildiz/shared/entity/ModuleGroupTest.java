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

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author Grégory Van den Borre
 */
class ModuleGroupTest {

    private static final ActionId MOVE = ActionId.valueOf(12);

    private static final ActionId INTERACTION = ActionId.valueOf(48);

    private static final ActionId DETECTOR = ActionId.valueOf(75);

    private static final ActionId HULL = ActionId.valueOf(34);

    private static final ActionId ENERGY = ActionId.valueOf(111);

    private static final ActionId OTHER_1 = ActionId.valueOf(41);

    private static final ActionId OTHER_2 = ActionId.valueOf(52);

    private static final ActionId OTHER_3 = ActionId.valueOf(255);

    @Nested
    class ConstructorWithAllParameters {

        @Test
        void withIds() {
            ModuleGroup modules = new ModuleGroup.ModuleGroupBuilder()
                    .withHull(HULL)
                    .withEnergy(ENERGY)
                    .withDetector(DETECTOR)
                    .withMove(MOVE)
                    .withInteraction(INTERACTION)
                    .withAdditional1(OTHER_1)
                    .withAdditional2(OTHER_2)
                    .withAdditional3(OTHER_3).build();

            assertEquals(MOVE, modules.getMove());
            assertEquals(INTERACTION, modules.getInteraction());
            assertEquals(HULL, modules.getHull());
            assertEquals(ENERGY, modules.getEnergy());
            assertEquals(DETECTOR, modules.getDetector());
            assertEquals(OTHER_1, modules.getAdditional1());
            assertEquals(OTHER_2, modules.getAdditional2());
            assertEquals(OTHER_3, modules.getAdditional3());
        }

        @Test
        void withOneNull() {
            assertThrows(AssertionError.class, () -> new ModuleGroup.ModuleGroupBuilder()
                    .withHull(HULL)
                    .withEnergy(ENERGY)
                    .withDetector(DETECTOR)
                    .withMove(MOVE)
                    .withInteraction(null)
                    .withAdditional1(OTHER_1)
                    .withAdditional2(OTHER_2)
                    .withAdditional3(OTHER_3).build());
        }

        @Test
        void withOneNullInOptionalParameters() {
            assertThrows(AssertionError.class, () -> new ModuleGroup.ModuleGroupBuilder()
                    .withHull(HULL)
                    .withEnergy(ENERGY)
                    .withDetector(DETECTOR)
                    .withMove(MOVE)
                    .withInteraction(INTERACTION)
                    .withAdditional1(OTHER_1)
                    .withAdditional2(OTHER_2)
                    .withAdditional3(null).build());
        }
    }

    @Nested
    class ConstructorFromList {

        @Test
        void happyFlow() {
            List<ActionId> l = Arrays.asList(HULL, ENERGY, DETECTOR, MOVE, INTERACTION, OTHER_1, OTHER_2, OTHER_3);
            ModuleGroup modules = new ModuleGroup.ModuleGroupBuilder().fromList(l).build();
            assertEquals(MOVE, modules.getMove());
            assertEquals(INTERACTION, modules.getInteraction());
            assertEquals(HULL, modules.getHull());
            assertEquals(ENERGY, modules.getEnergy());
            assertEquals(DETECTOR, modules.getDetector());
            assertEquals(OTHER_1, modules.getAdditional1());
            assertEquals(OTHER_2, modules.getAdditional2());
            assertEquals(OTHER_3, modules.getAdditional3());
        }

        @Test
        void withNullList() {
            List<ActionId> l = Arrays.asList(HULL, null, DETECTOR, MOVE, INTERACTION, OTHER_1, OTHER_2, OTHER_3);
            assertThrows(IllegalArgumentException.class, () -> new ModuleGroup.ModuleGroupBuilder().fromList(l).build());
        }

        @Test
        void withNullValue() {
            assertThrows(AssertionError.class, () -> new ModuleGroup.ModuleGroupBuilder().fromList(null).build());
        }

        @Test
        void withIncorrectSize() {
            List<ActionId> l = Arrays.asList(HULL, DETECTOR, MOVE, INTERACTION, OTHER_1, OTHER_2, OTHER_3);
            assertThrows(IllegalArgumentException.class, () -> new ModuleGroup.ModuleGroupBuilder().fromList(l).build());
        }


    }

    @Nested
    class HashCode {

        @Test
        void forSameValue() {
            ModuleGroup modules = new ModuleGroup.ModuleGroupBuilder()
                    .withHull(HULL)
                    .withEnergy(ENERGY)
                    .withDetector(DETECTOR)
                    .withMove(MOVE)
                    .withInteraction(INTERACTION)
                    .withAdditional1(OTHER_1)
                    .withAdditional2(OTHER_2)
                    .withAdditional3(OTHER_3).build();
            ModuleGroup modules2 = new ModuleGroup.ModuleGroupBuilder()
                    .withHull(HULL)
                    .withEnergy(ENERGY)
                    .withDetector(DETECTOR)
                    .withMove(MOVE)
                    .withInteraction(INTERACTION)
                    .withAdditional1(OTHER_1)
                    .withAdditional2(OTHER_2)
                    .withAdditional3(OTHER_3).build();
            assertEquals(modules.hashCode(), modules2.hashCode());
        }

    }

    @Nested
    class Equals {

        @Test
        void forSameInstance() {
            ModuleGroup modules = new ModuleGroup.ModuleGroupBuilder()
                    .withHull(HULL)
                    .withEnergy(ENERGY)
                    .withDetector(DETECTOR)
                    .withMove(MOVE)
                    .withInteraction(INTERACTION)
                    .withAdditional1(OTHER_1)
                    .withAdditional2(OTHER_2)
                    .withAdditional3(OTHER_3).build();
            assertEquals(modules, modules);
        }


        @Test
        void forSameValue() {
            ModuleGroup modules = new ModuleGroup.ModuleGroupBuilder()
                    .withHull(HULL)
                    .withEnergy(ENERGY)
                    .withDetector(DETECTOR)
                    .withMove(MOVE)
                    .withInteraction(INTERACTION)
                    .withAdditional1(OTHER_1)
                    .withAdditional2(OTHER_2)
                    .withAdditional3(OTHER_3).build();
            ModuleGroup modules2 = new ModuleGroup.ModuleGroupBuilder()
                    .withHull(HULL)
                    .withEnergy(ENERGY)
                    .withDetector(DETECTOR)
                    .withMove(MOVE)
                    .withInteraction(INTERACTION)
                    .withAdditional1(OTHER_1)
                    .withAdditional2(OTHER_2)
                    .withAdditional3(OTHER_3).build();
            assertEquals(modules, modules2);
        }

        @Test
        void forNull() {
            ModuleGroup modules = new ModuleGroup.ModuleGroupBuilder()
                    .withHull(HULL)
                    .withEnergy(ENERGY)
                    .withDetector(DETECTOR)
                    .withMove(MOVE)
                    .withInteraction(INTERACTION)
                    .withAdditional1(OTHER_1)
                    .withAdditional2(OTHER_2)
                    .withAdditional3(OTHER_3).build();
            assertNotEquals(modules, null);
        }

        @Test
        void forDifferentOrder() {
            ModuleGroup modules = new ModuleGroup.ModuleGroupBuilder()
                    .withHull(HULL)
                    .withEnergy(ENERGY)
                    .withDetector(DETECTOR)
                    .withMove(MOVE)
                    .withInteraction(INTERACTION)
                    .withAdditional1(OTHER_1)
                    .withAdditional2(OTHER_2)
                    .withAdditional3(OTHER_3).build();
            ModuleGroup modules2 = new ModuleGroup.ModuleGroupBuilder()
                    .withHull(ENERGY)
                    .withEnergy(HULL)
                    .withDetector(DETECTOR)
                    .withMove(MOVE)
                    .withInteraction(INTERACTION)
                    .withAdditional1(OTHER_1)
                    .withAdditional2(OTHER_2)
                    .withAdditional3(OTHER_3).build();
            assertNotEquals(modules, modules2);
        }

        @Test
        void forDifferentHull() {
            ModuleGroup modules = new ModuleGroup.ModuleGroupBuilder()
                    .withHull(HULL)
                    .withEnergy(ENERGY)
                    .withDetector(DETECTOR)
                    .withMove(MOVE)
                    .withInteraction(INTERACTION)
                    .withAdditional1(OTHER_1)
                    .withAdditional2(OTHER_2)
                    .withAdditional3(OTHER_3).build();
            ModuleGroup modules2 = new ModuleGroup.ModuleGroupBuilder()
                    .withHull(ENERGY)
                    .withEnergy(ENERGY)
                    .withDetector(DETECTOR)
                    .withMove(MOVE)
                    .withInteraction(INTERACTION)
                    .withAdditional1(OTHER_1)
                    .withAdditional2(OTHER_2)
                    .withAdditional3(OTHER_3).build();
            assertNotEquals(modules, modules2);
        }

        @Test
        void forDifferentEnergy() {
            ModuleGroup modules = new ModuleGroup.ModuleGroupBuilder()
                    .withHull(HULL)
                    .withEnergy(ENERGY)
                    .withDetector(DETECTOR)
                    .withMove(MOVE)
                    .withInteraction(INTERACTION)
                    .withAdditional1(OTHER_1)
                    .withAdditional2(OTHER_2)
                    .withAdditional3(OTHER_3).build();
            ModuleGroup modules2 = new ModuleGroup.ModuleGroupBuilder()
                    .withHull(HULL)
                    .withEnergy(HULL)
                    .withDetector(DETECTOR)
                    .withMove(MOVE)
                    .withInteraction(INTERACTION)
                    .withAdditional1(OTHER_1)
                    .withAdditional2(OTHER_2)
                    .withAdditional3(OTHER_3).build();
            assertNotEquals(modules, modules2);
        }

        @Test
        void forDifferentDetector() {
            ModuleGroup modules = new ModuleGroup.ModuleGroupBuilder()
                    .withHull(HULL)
                    .withEnergy(ENERGY)
                    .withDetector(DETECTOR)
                    .withMove(MOVE)
                    .withInteraction(INTERACTION)
                    .withAdditional1(OTHER_1)
                    .withAdditional2(OTHER_2)
                    .withAdditional3(OTHER_3).build();
            ModuleGroup modules2 = new ModuleGroup.ModuleGroupBuilder()
                    .withHull(HULL)
                    .withEnergy(ENERGY)
                    .withDetector(ENERGY)
                    .withMove(MOVE)
                    .withInteraction(INTERACTION)
                    .withAdditional1(OTHER_1)
                    .withAdditional2(OTHER_2)
                    .withAdditional3(OTHER_3).build();
            assertNotEquals(modules, modules2);
        }

        @Test
        void forDifferentMove() {
            ModuleGroup modules = new ModuleGroup.ModuleGroupBuilder()
                    .withHull(HULL)
                    .withEnergy(ENERGY)
                    .withDetector(DETECTOR)
                    .withMove(MOVE)
                    .withInteraction(INTERACTION)
                    .withAdditional1(OTHER_1)
                    .withAdditional2(OTHER_2)
                    .withAdditional3(OTHER_3).build();
            ModuleGroup modules2 = new ModuleGroup.ModuleGroupBuilder()
                    .withHull(HULL)
                    .withEnergy(ENERGY)
                    .withDetector(DETECTOR)
                    .withMove(DETECTOR)
                    .withInteraction(INTERACTION)
                    .withAdditional1(OTHER_1)
                    .withAdditional2(OTHER_2)
                    .withAdditional3(OTHER_3).build();
            assertNotEquals(modules, modules2);
        }

        @Test
        void forDifferentInteraction() {
            ModuleGroup modules = new ModuleGroup.ModuleGroupBuilder()
                    .withHull(HULL)
                    .withEnergy(ENERGY)
                    .withDetector(DETECTOR)
                    .withMove(MOVE)
                    .withInteraction(INTERACTION)
                    .withAdditional1(OTHER_1)
                    .withAdditional2(OTHER_2)
                    .withAdditional3(OTHER_3).build();
            ModuleGroup modules2 = new ModuleGroup.ModuleGroupBuilder()
                    .withHull(HULL)
                    .withEnergy(ENERGY)
                    .withDetector(DETECTOR)
                    .withMove(MOVE)
                    .withInteraction(MOVE)
                    .withAdditional1(OTHER_1)
                    .withAdditional2(OTHER_2)
                    .withAdditional3(OTHER_3).build();
            assertNotEquals(modules, modules2);
        }

        @Test
        void forDifferentAdditional1() {
            ModuleGroup modules = new ModuleGroup.ModuleGroupBuilder()
                    .withHull(HULL)
                    .withEnergy(ENERGY)
                    .withDetector(DETECTOR)
                    .withMove(MOVE)
                    .withInteraction(INTERACTION)
                    .withAdditional1(OTHER_1)
                    .withAdditional2(OTHER_2)
                    .withAdditional3(OTHER_3).build();
            ModuleGroup modules2 = new ModuleGroup.ModuleGroupBuilder()
                    .withHull(HULL)
                    .withEnergy(ENERGY)
                    .withDetector(DETECTOR)
                    .withMove(MOVE)
                    .withInteraction(INTERACTION)
                    .withAdditional1(INTERACTION)
                    .withAdditional2(OTHER_2)
                    .withAdditional3(OTHER_3).build();
            assertNotEquals(modules, modules2);
        }

        @Test
        void forDifferentAdditional2() {
            ModuleGroup modules = new ModuleGroup.ModuleGroupBuilder()
                    .withHull(HULL)
                    .withEnergy(ENERGY)
                    .withDetector(DETECTOR)
                    .withMove(MOVE)
                    .withInteraction(INTERACTION)
                    .withAdditional1(OTHER_1)
                    .withAdditional2(OTHER_2)
                    .withAdditional3(OTHER_3).build();
            ModuleGroup modules2 = new ModuleGroup.ModuleGroupBuilder()
                    .withHull(HULL)
                    .withEnergy(ENERGY)
                    .withDetector(DETECTOR)
                    .withMove(MOVE)
                    .withInteraction(INTERACTION)
                    .withAdditional1(OTHER_1)
                    .withAdditional2(OTHER_1)
                    .withAdditional3(OTHER_3).build();
            assertNotEquals(modules, modules2);
        }

        @Test
        void forDifferentAdditional3() {
            ModuleGroup modules = new ModuleGroup.ModuleGroupBuilder()
                    .withHull(HULL)
                    .withEnergy(ENERGY)
                    .withDetector(DETECTOR)
                    .withMove(MOVE)
                    .withInteraction(INTERACTION)
                    .withAdditional1(OTHER_1)
                    .withAdditional2(OTHER_2)
                    .withAdditional3(OTHER_3).build();
            ModuleGroup modules2 = new ModuleGroup.ModuleGroupBuilder()
                    .withHull(HULL)
                    .withEnergy(ENERGY)
                    .withDetector(DETECTOR)
                    .withMove(MOVE)
                    .withInteraction(INTERACTION)
                    .withAdditional1(OTHER_1)
                    .withAdditional2(OTHER_2)
                    .withAdditional3(OTHER_2).build();
            assertNotEquals(modules, modules2);
        }
    }
}
