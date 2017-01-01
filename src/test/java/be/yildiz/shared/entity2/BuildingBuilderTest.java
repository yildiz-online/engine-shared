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

package be.yildiz.shared.entity2;

import be.yildiz.common.id.EntityId;
import be.yildiz.common.id.PlayerId;
import be.yildiz.common.vector.Point3D;
import be.yildiz.shared.building.BaseBuilding;
import be.yildiz.shared.building.BaseBuildingTest;
import be.yildiz.shared.building.Building;
import be.yildiz.shared.construction.entity.Builder;
import be.yildiz.shared.construction.entity.BuildingBuilder;
import be.yildiz.shared.data.ConstructionData;
import lombok.NonNull;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * @author Grégory Van den Borre
 */
public class BuildingBuilderTest {

    @Rule
    public final ExpectedException rule = ExpectedException.none();

    @Test
    public void testBuildingBuilder() {
        Building building = new BaseBuilding(BaseBuildingTest.OK_CITY, BaseBuildingTest.OK_DATA, BaseBuildingTest.OK_POSITION, BaseBuildingTest.OK_LEVEL, BaseBuildingTest.OK_STAFF);
        new DummyBuildingBuilder(building);

        this.rule.expect(NullPointerException.class);
        new DummyBuildingBuilder(null, PlayerId.WORLD, Point3D.xyz(10, 15, 20), building);

        this.rule.expect(NullPointerException.class);
        new DummyBuildingBuilder(EntityId.get(10L), PlayerId.WORLD, null, building);

        this.rule.expect(NullPointerException.class);
        new DummyBuildingBuilder(EntityId.get(10L), PlayerId.WORLD, Point3D.xyz(10, 15, 20), null);
    }

    @Test
    public void testRemoveFromQueue() {
    }

    @Test
    public void testAddInQueue() {
    }

    @Test
    public void testGetBuilderId() {
        Building building = new BaseBuilding(BaseBuildingTest.OK_CITY, BaseBuildingTest.OK_DATA, BaseBuildingTest.OK_POSITION, BaseBuildingTest.OK_LEVEL, BaseBuildingTest.OK_STAFF);
        Builder b = new DummyBuildingBuilder(building);
        Assert.assertEquals(EntityId.get(10L), b.getBuilderId());
    }

    @Test
    public void testGetOwner() {
    }

    @Test
    public void testGetBuildPosition() {
    }

    @Test
    public void testGetQueue() {
    }

    @Test
    public void testSetQueue() {
    }

    private static final class DummyBuildingBuilder extends BuildingBuilder<Building> {

        /**
         * Instantiate a new BuildingBuilder.
         *
         * @param building Associated building.
         */
        public DummyBuildingBuilder(@NonNull Building building) {
            super(EntityId.get(10L), PlayerId.WORLD, Point3D.xyz(10, 15, 20), building, 5);
        }

        public DummyBuildingBuilder(EntityId builderId, PlayerId world, Point3D xyz, Building building) {
            super(builderId, world, xyz, building, 5);
        }

        @Override
        public boolean fullfilPrerequisite(ConstructionData type) {
            return true;
        }
    }

}
