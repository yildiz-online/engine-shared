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

package be.yildiz.shared.city;

import be.yildiz.common.id.EntityId;
import be.yildiz.common.id.PlayerId;
import be.yildiz.helper.Helper;
import be.yildiz.shared.building.Building;
import be.yildiz.shared.building.BuildingData;
import be.yildiz.shared.entity.Entity;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * @author Grégory Van den Borre
 */
public class CityManagerTest {

    @Rule
    public final ExpectedException rule = ExpectedException.none();

    @Test
    public void testSetOwnerCityPlayer() {
        Entity e = Helper.givenAnEntity();
    }

    @Test
    public void createCity() {
        Entity e = Helper.givenAnEntity();
        CityManager cm = givenACityManager();
        Assert.assertEquals(0, cm.getCities().size());
        Assert.assertEquals(0, cm.getCities().size());
        Assert.assertEquals(0, cm.getCities(e.getOwner()).size());
        cm.createCity(e);
        Assert.assertEquals(1, cm.getCities().size());
        Assert.assertEquals(PlayerId.get(6), cm.getCityById(EntityId.get(5L)).getOwner());
        Assert.assertEquals(1, cm.getCities(e.getOwner()).size());
        Assert.assertNotNull(cm.getCityById(e.getId()));
    }

    private CityManager<Building, BuildingData, BaseCity<Building, BuildingData>> givenACityManager() {
        return new CityManagerMock();
    }
}
