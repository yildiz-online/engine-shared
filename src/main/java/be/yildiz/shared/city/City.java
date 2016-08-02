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
import be.yildiz.common.vector.Point3D;
import be.yildiz.shared.building.Building;
import be.yildiz.shared.building.BuildingData;
import be.yildiz.shared.data.BuildingPosition;
import be.yildiz.shared.data.EntityType;
import be.yildiz.shared.resources.ResourcesProducer;

import java.util.List;
import java.util.Set;

/**
 * @author Grégory Van den Borre
 */
public interface City<T extends Building, D extends BuildingData> {

    String getName();

    /**
     * @return <code>true</code> if this city have a negative production.
     */
    boolean hasNegativeProductionRatio();

    /**
     * Get the 3D building position from its position in the base.
     *
     * @param position Position in the base.
     * @return The position in the world.
     */
    //@Ensures("result != null")
    Point3D getBuildingPosition(BuildingPosition position);

    /**
     * @return All staff allocated in the BaseCity, including the one currently in assignation.
     */
    //@Ensures("result >= 0")
    int getAllocatedStaff();

    /**
     * Give the Building matching a position.
     *
     * @param position Building position.
     * @return The building at the given position.
     */
    T getBuilding(BuildingPosition position);

    /**
     * Add a Building in the BaseCity.
     *
     * @param building Building to add.
     */
    void createConstruction(T building);

    /**
     * Provide the list of building type allowed to be built in this city.
     *
     * @return The list of allowed type to be built.
     */
    Set<EntityType> getAllowedType();

    List<D> getAllType();

    int getMaximumBuildings();

    EntityId getId();

    PlayerId getOwner();

    Point3D getPosition();

    void initializeProducer();

    ResourcesProducer getProducer();
}
