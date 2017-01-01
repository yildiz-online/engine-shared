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

package be.yildiz.shared.building;

import be.yildiz.common.id.EntityId;
import be.yildiz.common.util.Time;
import be.yildiz.shared.data.BuildingPosition;
import be.yildiz.shared.data.EntityType;
import be.yildiz.shared.data.Level;
import be.yildiz.shared.data.TimeToBuild;
import be.yildiz.shared.resources.ResourceValue;
import be.yildiz.shared.resources.bonus.BonusResources;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author Grégory Van den Borre
 */
public class BaseBuildingTest {

    public static final EntityId OK_CITY = EntityId.get(6L);

    public static final int OK_STAFF = 2;

    public static final Level OK_LEVEL = new Level(5);

    public static final BuildingPosition OK_POSITION = new BuildingPosition(2);
    public static final Level MAX_LEVEL = new Level(32);
    public static final EntityType TYPE = new EntityType(10, "test");
    public static final ResourceValue NEXT_LEVEL_PRICE = new ResourceValue(new float[]{10, 10, 10});
    public static final TimeToBuild NEXT_LEVEL_TIME = new TimeToBuild(Time.seconds(10));
    public static final BuildingData OK_DATA = getData();

    private static BuildingData getData() {
        return new BuildingData() {
            @Override
            public EntityType getType() {
                return TYPE;
            }

            @Override
            public ResourceValue getPrice() {
                return NEXT_LEVEL_PRICE;
            }

            @Override
            public TimeToBuild getTimeToBuild() {
                return NEXT_LEVEL_TIME;
            }

            @Override
            public BonusResources getStaffBonus(int staffAllocated) {
                return null;
            }

            @Override
            public BonusResources getLevelBonus(Level level) {
                return null;
            }

            @Override
            public boolean isEmpty() {
                return false;
            }

            @Override
            public boolean isBuilder() {
                return false;
            }

            @Override
            public boolean hasRatioBonus() {
                return false;
            }

            @Override
            public ResourceValue getPrice(Level level) {
                return this.getPrice();
            }

            @Override
            public TimeToBuild getTimeToBuild(Level level) {
                return this.getTimeToBuild();
            }

            @Override
            public int getMaxPopulation(Level level) {
                return 20;
            }

            @Override
            public Level getMaxLevel() {
                return MAX_LEVEL;
            }

            @Override
            public boolean isBuildable() {
                return true;
            }
        };
    }


    @Test
    public void testConstructor() {
        Building b = new BaseBuilding(OK_CITY, OK_DATA, OK_POSITION, OK_LEVEL, OK_STAFF);
        Assert.assertEquals(OK_CITY, b.getCity());
        Assert.assertEquals(OK_POSITION, b.getBuildingPosition());
        Assert.assertEquals(OK_LEVEL, b.getLevel());
        Assert.assertEquals(OK_STAFF, b.getStaff());
        Assert.assertEquals(OK_STAFF, b.getOldStaff());
    }

    @Test(expected = NullPointerException.class)
    public void testConstructorNullCity() {
        new BaseBuilding(null, OK_DATA, OK_POSITION, OK_LEVEL, OK_STAFF);
    }

    @Test(expected = NullPointerException.class)
    public void testConstructorNullData() {
        new BaseBuilding(OK_CITY, null, OK_POSITION, OK_LEVEL, OK_STAFF);
    }

    @Test(expected = NullPointerException.class)
    public void testConstructorNullPosition() {
        new BaseBuilding(OK_CITY, OK_DATA, null, OK_LEVEL, OK_STAFF);
    }

    @Test(expected = NullPointerException.class)
    public void testConstructorNullLevel() {
        new BaseBuilding(OK_CITY, OK_DATA, OK_POSITION, null, OK_STAFF);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorNegativeStaff() {
        new BaseBuilding(OK_CITY, OK_DATA, OK_POSITION, OK_LEVEL, -1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorLevelTooHigh() {
        new BaseBuilding(OK_CITY, OK_DATA, OK_POSITION, MAX_LEVEL.add(1), OK_STAFF);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorAssignedStaffTooHigh() {
        new BaseBuilding(OK_CITY, OK_DATA, OK_POSITION, OK_LEVEL, 35);
    }

    @Test
    public void testSetStaff() {
        Building b = givenABuillding();
        b.setStaff(10);
        Assert.assertEquals(OK_STAFF, b.getOldStaff());
        Assert.assertEquals(10, b.getStaff());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testTooHighStaff() {
        Building b = givenABuillding();
        b.setStaff(35);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNegativeStaff() {
        Building b = new BaseBuilding(OK_CITY, OK_DATA, OK_POSITION, OK_LEVEL, OK_STAFF);
        b.setStaff(-1);
    }

    @Test
    public void testSetLevel() {
        Building b = givenABuillding();
        b.setLevel(new Level(14));
        Assert.assertEquals(new Level(14), b.getLevel());
    }

    @Test(expected = NullPointerException.class)
    public void testSetLevelNull() {
        Building b = givenABuillding();
        b.setLevel(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetLevelTooHigh() {
        Building b = givenABuillding();
        b.setLevel(MAX_LEVEL.add(1));
    }

    @Test
    public void testGetNextLevelPrice() {
        Building b = givenABuillding();
        Assert.assertEquals(NEXT_LEVEL_PRICE, b.getNextLevelPrice());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetNextLevelPriceLevelMax() {
        Building b = givenABuillding();
        b.setLevel(MAX_LEVEL);
        Assert.assertEquals(NEXT_LEVEL_PRICE, b.getNextLevelPrice());
    }

    @Test
    public void testGetNextLevelTime() {
        Building b = givenABuillding();
        Assert.assertEquals(NEXT_LEVEL_TIME, b.getNextLevelTimeToBuild());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetNextLevelTimeLevelMax() {
        Building b = givenABuillding();
        b.setLevel(MAX_LEVEL);
        Assert.assertEquals(NEXT_LEVEL_TIME, b.getNextLevelTimeToBuild());
    }

    private Building givenABuillding() {
        return new BaseBuilding(OK_CITY, OK_DATA, OK_POSITION, OK_LEVEL, OK_STAFF);
    }

}
