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
import be.yildiz.shared.building.staff.Staff;
import be.yildiz.shared.data.BuildingPosition;
import be.yildiz.shared.data.EntityType;
import be.yildiz.shared.data.Level;
import be.yildiz.shared.data.TimeToBuild;
import be.yildizgames.engine.feature.resource.ResourceValue;
import be.yildizgames.engine.feature.resource.bonus.BonusResources;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author Grégory Van den Borre
 */
public class BaseBuildingTest {

    public static final EntityId OK_CITY = EntityId.valueOf(6L);

    public static final Staff OK_STAFF = Staff.valueOf(2);

    public static final Level OK_LEVEL = Level.valueOf(5);

    public static final BuildingPosition OK_POSITION = BuildingPosition.valueOf(2);
    private static final Level MAX_LEVEL = Level.valueOf(32);
    private static final EntityType TYPE = new EntityType(10, "test");
    private static final ResourceValue NEXT_LEVEL_PRICE = new ResourceValue(new float[]{10, 10, 10});
    private static final TimeToBuild NEXT_LEVEL_TIME = new TimeToBuild(Time.seconds(10));
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
            public BonusResources getStaffBonus(Staff staffAllocated) {
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
            public Staff getMaxPopulation(Level level) {
                return Staff.valueOf(20);
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
    void testConstructor() {
        Building b = new BaseBuilding(OK_CITY, OK_DATA, OK_POSITION, OK_LEVEL, OK_STAFF);
        assertEquals(OK_CITY, b.getCity());
        assertEquals(OK_POSITION, b.getBuildingPosition());
        assertEquals(OK_LEVEL, b.getLevel());
        assertEquals(OK_STAFF, b.getStaff());
        assertEquals(OK_STAFF, b.getOldStaff());
    }

    @Test
    void testConstructorNullCity() {
        assertThrows(AssertionError.class, () -> new BaseBuilding(null, OK_DATA, OK_POSITION, OK_LEVEL, OK_STAFF));
    }

    @Test
    void testConstructorNullData() {
        assertThrows(AssertionError.class, () -> new BaseBuilding(OK_CITY, null, OK_POSITION, OK_LEVEL, OK_STAFF));
    }

    @Test
    void testConstructorNullPosition() {
        assertThrows(AssertionError.class, () -> new BaseBuilding(OK_CITY, OK_DATA, null, OK_LEVEL, OK_STAFF));
    }

    @Test
    void testConstructorNullLevel() {
        assertThrows(AssertionError.class, () -> new BaseBuilding(OK_CITY, OK_DATA, OK_POSITION, null, OK_STAFF));
    }

    @Test
    void testConstructorNegativeStaff() {
        assertThrows(AssertionError.class, () -> new BaseBuilding(OK_CITY, OK_DATA, OK_POSITION, OK_LEVEL, Staff.valueOf(-1)));
    }

    @Test
    void testConstructorLevelTooHigh() {
        assertThrows(AssertionError.class, () -> new BaseBuilding(OK_CITY, OK_DATA, OK_POSITION, MAX_LEVEL.add(1), OK_STAFF));
    }

    @Test
    void testConstructorAssignedStaffTooHigh() {
        assertThrows(AssertionError.class, () -> new BaseBuilding(OK_CITY, OK_DATA, OK_POSITION, OK_LEVEL, Staff.valueOf(35)));
    }

    @Test
    void testSetStaff() {
        Building b = givenABuilding();
        b.setStaff(Staff.valueOf(10));
        assertEquals(OK_STAFF, b.getOldStaff());
        assertEquals(Staff.valueOf(10), b.getStaff());
    }

    @Test
    void testTooHighStaff() {
        Building b = givenABuilding();
        assertThrows(AssertionError.class, () -> b.setStaff(Staff.valueOf(35)));
    }

    @Test
    void testNegativeStaff() {
        Building b = new BaseBuilding(OK_CITY, OK_DATA, OK_POSITION, OK_LEVEL, OK_STAFF);
        assertThrows(AssertionError.class, () -> b.setStaff(Staff.valueOf(-1)));
    }

    @Test
    void testSetLevel() {
        Building b = givenABuilding();
        b.setLevel(Level.valueOf(14));
        assertEquals(Level.valueOf(14), b.getLevel());
    }

    @Test
    void testSetLevelNull() {
        Building b = givenABuilding();
        assertThrows(AssertionError.class, () -> b.setLevel(null));
    }

    @Test
    void testSetLevelTooHigh() {
        Building b = givenABuilding();
        assertThrows(AssertionError.class, () -> b.setLevel(MAX_LEVEL.add(1)));
    }

    @Test
    void testGetNextLevelPrice() {
        Building b = givenABuilding();
        assertEquals(NEXT_LEVEL_PRICE, b.getNextLevelPrice());
    }

    @Test
    void testGetNextLevelPriceLevelMax() {
        Building b = givenABuilding();
        b.setLevel(MAX_LEVEL);
        assertThrows(AssertionError.class, b::getNextLevelPrice);
    }

    @Test
    void testGetNextLevelTime() {
        Building b = givenABuilding();
        assertEquals(NEXT_LEVEL_TIME, b.getNextLevelTimeToBuild());
    }

    @Test
    void testGetNextLevelTimeLevelMax() {
        Building b = givenABuilding();
        b.setLevel(MAX_LEVEL);
        assertThrows(AssertionError.class, b::getNextLevelTimeToBuild);
    }

    private Building givenABuilding() {
        return new BaseBuilding(OK_CITY, OK_DATA, OK_POSITION, OK_LEVEL, OK_STAFF);
    }

}
