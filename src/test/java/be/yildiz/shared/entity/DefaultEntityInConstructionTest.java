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

import be.yildiz.common.id.EntityId;
import be.yildiz.common.id.PlayerId;
import be.yildiz.common.vector.Point3D;
import be.yildiz.shared.data.EntityType;
import be.yildiz.shared.entity.module.ModuleGroup;
import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

/**
 * @author Grégory Van den Borre
 */
@RunWith(Enclosed.class)
public class DefaultEntityInConstructionTest {

    public static final EntityId ID_OK = EntityId.get(3L);
    public static final PlayerId OWNER_OK = PlayerId.get(2);
    public static final ModuleGroup MODULES_OK = new ModuleGroup();
    public static final Point3D POSITION_OK = Point3D.xyz(1, 2, 3);
    public static final Point3D DIRECTION_OK = Point3D.xyz(5, 8, 3);
    public static final EntityType TYPE_OK = new EntityType(4, "test");

    public static class Constructor {

        @Test
        public void happyFlow() {
            DefaultEntityInConstruction d = new DefaultEntityInConstruction(TYPE_OK, ID_OK, OWNER_OK, MODULES_OK, POSITION_OK, DIRECTION_OK);
            Assert.assertEquals(TYPE_OK, d.getType());
            Assert.assertEquals(ID_OK, d.getId());
            Assert.assertEquals(OWNER_OK, d.getOwner());
            Assert.assertEquals(MODULES_OK, d.getModules());
            Assert.assertEquals(POSITION_OK, d.getPosition());
            Assert.assertEquals(DIRECTION_OK, d.getDirection());
        }

        @Test(expected = NullPointerException.class)
        public void withNullType() {
            new DefaultEntityInConstruction(null, ID_OK, OWNER_OK, MODULES_OK, POSITION_OK, DIRECTION_OK);
        }

        @Test(expected = NullPointerException.class)
        public void withNullId() {
            new DefaultEntityInConstruction(TYPE_OK, null, OWNER_OK, MODULES_OK, POSITION_OK, DIRECTION_OK);
        }

        @Test(expected = NullPointerException.class)
        public void withNullOwner() {
            new DefaultEntityInConstruction(TYPE_OK, ID_OK, null, MODULES_OK, POSITION_OK, DIRECTION_OK);
        }
    }
}
