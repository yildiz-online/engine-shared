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
import be.yildiz.shared.entity.module.ModuleGroup;
import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.List;

/**
 * @author Grégory Van den Borre
 */
@RunWith(Enclosed.class)
public class ModuleGroupTest {

    private static final ActionId MOVE = ActionId.get(12);

    private static final ActionId INTERACTION = ActionId.get(48);

    private static final ActionId DETECTOR = ActionId.get(75);

    private static final ActionId HULL = ActionId.get(34);

    private static final ActionId ENERGY = ActionId.get(111);

    private static final ActionId OTHER_1 = ActionId.get(41);

    private static final ActionId OTHER_2 = ActionId.get(52);

    private static final ActionId OTHER_3 = ActionId.get(255);

    public static class ConstructorWithAllParameters {

        @Test
        public void withIds() {
            ModuleGroup modules = new ModuleGroup.ModuleGroupBuilder()
                    .withHull(HULL)
                    .withEnergy(ENERGY)
                    .withDetector(DETECTOR)
                    .withMove(MOVE)
                    .withInteraction(INTERACTION)
                    .withAdditional1(OTHER_1)
                    .withAdditional2(OTHER_2)
                    .withAdditional3(OTHER_3).build();

            Assert.assertEquals(MOVE, modules.getMove());
            Assert.assertEquals(INTERACTION, modules.getInteraction());
            Assert.assertEquals(HULL, modules.getHull());
            Assert.assertEquals(ENERGY, modules.getEnergy());
            Assert.assertEquals(DETECTOR, modules.getDetector());
            Assert.assertEquals(OTHER_1, modules.getAdditional1());
            Assert.assertEquals(OTHER_2, modules.getAdditional2());
            Assert.assertEquals(OTHER_3, modules.getAdditional3());
        }

        @Test(expected = AssertionError.class)
        public void withOneNull() {
            new ModuleGroup.ModuleGroupBuilder()
                    .withHull(HULL)
                    .withEnergy(ENERGY)
                    .withDetector(DETECTOR)
                    .withMove(MOVE)
                    .withInteraction(null)
                    .withAdditional1(OTHER_1)
                    .withAdditional2(OTHER_2)
                    .withAdditional3(OTHER_3).build();
        }

        @Test(expected = AssertionError.class)
        public void withOneNullInOptionalParameters() {
            new ModuleGroup.ModuleGroupBuilder()
                    .withHull(HULL)
                    .withEnergy(ENERGY)
                    .withDetector(DETECTOR)
                    .withMove(MOVE)
                    .withInteraction(INTERACTION)
                    .withAdditional1(OTHER_1)
                    .withAdditional2(OTHER_2)
                    .withAdditional3(null).build();
        }
    }

    public static final class ConstructorFromList {

        @Test
        public void happyFlow() {
            List<ActionId> l = Arrays.asList(HULL, ENERGY, DETECTOR, MOVE, INTERACTION, OTHER_1, OTHER_2, OTHER_3);
            ModuleGroup modules = new ModuleGroup.ModuleGroupBuilder().fromList(l).build();
            Assert.assertEquals(MOVE, modules.getMove());
            Assert.assertEquals(INTERACTION, modules.getInteraction());
            Assert.assertEquals(HULL, modules.getHull());
            Assert.assertEquals(ENERGY, modules.getEnergy());
            Assert.assertEquals(DETECTOR, modules.getDetector());
            Assert.assertEquals(OTHER_1, modules.getAdditional1());
            Assert.assertEquals(OTHER_2, modules.getAdditional2());
            Assert.assertEquals(OTHER_3, modules.getAdditional3());
        }

        @Test(expected = IllegalArgumentException.class)
        public void withNullList() {
            List<ActionId> l = Arrays.asList(HULL, null, DETECTOR, MOVE, INTERACTION, OTHER_1, OTHER_2, OTHER_3);
            new ModuleGroup.ModuleGroupBuilder().fromList(l).build();
        }

        @Test(expected = AssertionError.class)
        public void withNullValue() {
            new ModuleGroup.ModuleGroupBuilder().fromList(null).build();
        }

        @Test(expected = IllegalArgumentException.class)
        public void withIncorrectSize() {
            List<ActionId> l = Arrays.asList(HULL, DETECTOR, MOVE, INTERACTION, OTHER_1, OTHER_2, OTHER_3);
            new ModuleGroup.ModuleGroupBuilder().fromList(l).build();
        }


    }

    public static class HashCode {

        @Test
        public void forSameValue() {
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
            Assert.assertEquals(modules.hashCode(), modules2.hashCode());
        }

    }

    public static class Equals {

        @Test
        public void forSameInstance() {
            ModuleGroup modules = new ModuleGroup.ModuleGroupBuilder()
                    .withHull(HULL)
                    .withEnergy(ENERGY)
                    .withDetector(DETECTOR)
                    .withMove(MOVE)
                    .withInteraction(INTERACTION)
                    .withAdditional1(OTHER_1)
                    .withAdditional2(OTHER_2)
                    .withAdditional3(OTHER_3).build();
            Assert.assertEquals(modules, modules);
        }


        @Test
        public void forSameValue() {
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
            Assert.assertEquals(modules, modules2);
        }

        @Test
        public void forNull() {
            ModuleGroup modules = new ModuleGroup.ModuleGroupBuilder()
                    .withHull(HULL)
                    .withEnergy(ENERGY)
                    .withDetector(DETECTOR)
                    .withMove(MOVE)
                    .withInteraction(INTERACTION)
                    .withAdditional1(OTHER_1)
                    .withAdditional2(OTHER_2)
                    .withAdditional3(OTHER_3).build();
            ModuleGroup modules2 = null;
            Assert.assertNotEquals(modules, modules2);
        }

        @Test
        public void forDifferentOrder() {
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
            Assert.assertNotEquals(modules, modules2);
        }

        @Test
        public void forDifferentHull() {
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
            Assert.assertNotEquals(modules, modules2);
        }

        @Test
        public void forDifferentEnergy() {
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
            Assert.assertNotEquals(modules, modules2);
        }

        @Test
        public void forDifferentDetector() {
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
            Assert.assertNotEquals(modules, modules2);
        }

        @Test
        public void forDifferentMove() {
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
            Assert.assertNotEquals(modules, modules2);
        }

        @Test
        public void forDifferentInteraction() {
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
            Assert.assertNotEquals(modules, modules2);
        }

        @Test
        public void forDifferentAdditional1() {
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
            Assert.assertNotEquals(modules, modules2);
        }

        @Test
        public void forDifferentAdditional2() {
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
            Assert.assertNotEquals(modules, modules2);
        }

        @Test
        public void forDifferentAdditional3() {
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
            Assert.assertNotEquals(modules, modules2);
        }
    }
}
