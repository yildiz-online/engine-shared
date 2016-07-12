//        This file is part of the Yildiz-Online project, licenced under the MIT License
//        (MIT)
//
//        Copyright (c) 2016 Grégory Van den Borre
//
//        More infos available: http://yildiz.bitbucket.org
//
//        Permission is hereby granted, free of charge, to any person obtaining a copy
//        of this software and associated documentation files (the "Software"), to deal
//        in the Software without restriction, including without limitation the rights
//        to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
//        copies of the Software, and to permit persons to whom the Software is
//        furnished to do so, subject to the following conditions:
//
//        The above copyright notice and this permission notice shall be included in all
//        copies or substantial portions of the Software.
//
//        THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
//        IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
//        FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
//        AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
//        LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
//        OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
//        SOFTWARE.

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

    private static final ActionId HULL = ActionId.get(34);

    private static final ActionId ENERGY = ActionId.get(111);

    private static final ActionId OTHER_1 = ActionId.get(41);

    private static final ActionId OTHER_2 = ActionId.get(52);

    public static class ConstructorWithAllParameters {

        @Test
        public void withoutIds() {
            ModuleGroup modules = new ModuleGroup(MOVE,INTERACTION,HULL,ENERGY);
            Assert.assertEquals(MOVE, modules.getMove());
            Assert.assertEquals(INTERACTION, modules.getInteraction());
            Assert.assertEquals(HULL, modules.getHull());
            Assert.assertEquals(ENERGY, modules.getEnergy());
            Assert.assertEquals(4, modules.getAll().size());
            Assert.assertEquals(MOVE, modules.getAll().get(0));
            Assert.assertEquals(INTERACTION, modules.getAll().get(1));
            Assert.assertEquals(HULL, modules.getAll().get(2));
            Assert.assertEquals(ENERGY, modules.getAll().get(3));
            Assert.assertEquals(4, modules.getAll().size());
            Assert.assertTrue(modules.getModules().isEmpty());
        }

        @Test
        public void withIds() {
            ModuleGroup modules = new ModuleGroup(MOVE,INTERACTION,HULL,ENERGY,OTHER_1, OTHER_2);
            Assert.assertEquals(MOVE, modules.getMove());
            Assert.assertEquals(INTERACTION, modules.getInteraction());
            Assert.assertEquals(HULL, modules.getHull());
            Assert.assertEquals(ENERGY, modules.getEnergy());
            Assert.assertEquals(MOVE, modules.getAll().get(0));
            Assert.assertEquals(INTERACTION, modules.getAll().get(1));
            Assert.assertEquals(HULL, modules.getAll().get(2));
            Assert.assertEquals(ENERGY, modules.getAll().get(3));
            Assert.assertEquals(6, modules.getAll().size());
            Assert.assertEquals(2, modules.getModules().size());
            Assert.assertEquals(OTHER_1, modules.getModules().get(0));
            Assert.assertEquals(OTHER_2, modules.getModules().get(1));
            Assert.assertEquals(OTHER_1, modules.getAll().get(4));
            Assert.assertEquals(OTHER_2, modules.getAll().get(5));
        }

        @Test(expected = NullPointerException.class)
        public void withOneNull() {
            new ModuleGroup(MOVE,INTERACTION,null,ENERGY);
        }

        @Test(expected = NullPointerException.class)
        public void withOneNullInOptionalParameters() {
            new ModuleGroup(MOVE,INTERACTION,HULL,ENERGY, OTHER_1, null);
        }
    }

    public static final class ConstructorFromList {

        @Test
        public void complete() {
            List<ActionId> idList = Arrays.asList(MOVE,INTERACTION,HULL,ENERGY,OTHER_1, OTHER_2);
            ModuleGroup modules = new ModuleGroup(idList);
            Assert.assertEquals(MOVE, modules.getMove());
            Assert.assertEquals(INTERACTION, modules.getInteraction());
            Assert.assertEquals(HULL, modules.getHull());
            Assert.assertEquals(ENERGY, modules.getEnergy());
            Assert.assertEquals(MOVE, modules.getAll().get(0));
            Assert.assertEquals(INTERACTION, modules.getAll().get(1));
            Assert.assertEquals(HULL, modules.getAll().get(2));
            Assert.assertEquals(ENERGY, modules.getAll().get(3));
            Assert.assertEquals(6, modules.getAll().size());
            Assert.assertEquals(2, modules.getModules().size());
            Assert.assertEquals(OTHER_1, modules.getModules().get(0));
            Assert.assertEquals(OTHER_2, modules.getModules().get(1));
            Assert.assertEquals(OTHER_1, modules.getAll().get(4));
            Assert.assertEquals(OTHER_2, modules.getAll().get(5));
        }

        @Test(expected = IllegalArgumentException.class)
        public void smallerThan4() {
            List<ActionId> idList = Arrays.asList(MOVE, INTERACTION, HULL);
            new ModuleGroup(idList);
        }

        @Test(expected = NullPointerException.class)
        public void containingNullValues() {
            List<ActionId> idList = Arrays.asList(MOVE, INTERACTION, HULL, ENERGY, null);
            new ModuleGroup(idList);
        }

        @Test(expected = NullPointerException.class)
        public void whichIsNull() {
            new ModuleGroup(null);
        }
    }

    public static final class Get {

        @Test(expected = UnsupportedOperationException.class)
        public void completeListModified() {
            ModuleGroup modules = new ModuleGroup(MOVE,INTERACTION,HULL,ENERGY,OTHER_1, OTHER_2);
            modules.getAll().add(ActionId.get(44));
        }

        @Test(expected = UnsupportedOperationException.class)
        public void moduleListModified() {
            ModuleGroup modules = new ModuleGroup(MOVE,INTERACTION,HULL,ENERGY,OTHER_1, OTHER_2);
            modules.getModules().add(ActionId.get(44));
        }
    }

    public static class HashCode {

        @Test
        public void forSameValue() {
            ModuleGroup modules = new ModuleGroup(MOVE,INTERACTION,HULL,ENERGY,OTHER_1, OTHER_2);
            ModuleGroup modules2 = new ModuleGroup(MOVE,INTERACTION,HULL,ENERGY,OTHER_1, OTHER_2);
            Assert.assertEquals(modules.hashCode(), modules2.hashCode());
        }

    }

    public static class Equals {

        @Test
        public void forSameValue() {
            ModuleGroup modules = new ModuleGroup(MOVE,INTERACTION,HULL,ENERGY,OTHER_1, OTHER_2);
            ModuleGroup modules2 = new ModuleGroup(MOVE,INTERACTION,HULL,ENERGY,OTHER_1, OTHER_2);
            Assert.assertEquals(modules, modules2);
        }

        @Test
        public void forNull() {
            ModuleGroup modules = new ModuleGroup(MOVE,INTERACTION,HULL,ENERGY,OTHER_1, OTHER_2);
            ModuleGroup modules2 = null;
            Assert.assertNotEquals(modules, modules2);
        }

        @Test
        public void forDifferentOrder() {
            ModuleGroup modules = new ModuleGroup(MOVE,INTERACTION,HULL,ENERGY,OTHER_1, OTHER_2);
            ModuleGroup modules2 = new ModuleGroup(MOVE,INTERACTION,HULL,ENERGY,OTHER_2, OTHER_1);
            Assert.assertNotEquals(modules, modules2);
        }

        @Test
        public void forNotSameSize() {
            ModuleGroup modules = new ModuleGroup(MOVE,INTERACTION,HULL,ENERGY,OTHER_1, OTHER_2);
            ModuleGroup modules2 = new ModuleGroup(MOVE,INTERACTION,HULL,ENERGY, OTHER_1);
            Assert.assertNotEquals(modules, modules2);
        }

        @Test
        public void forOtherType() {
            ModuleGroup modules = new ModuleGroup(MOVE,INTERACTION,HULL,ENERGY,OTHER_1, OTHER_2);
            ModuleGroup modules2 = new ModuleGroup(MOVE,INTERACTION,HULL,ENERGY,OTHER_2, OTHER_1);
            Assert.assertNotEquals(modules, modules2.toString());
        }

    }
}
