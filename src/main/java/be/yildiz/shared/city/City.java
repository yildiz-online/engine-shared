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

package be.yildiz.shared.city;

import be.yildiz.common.id.EntityId;
import be.yildiz.common.id.PlayerId;
import be.yildiz.common.vector.Point3D;
import be.yildiz.shared.building.Building;
import be.yildiz.shared.building.BuildingData;
import be.yildiz.shared.data.BuildingPosition;
import be.yildiz.shared.data.EntityType;
import be.yildizgames.engine.feature.resource.ResourcesProducer;

import java.util.List;
import java.util.Set;

/**
 * @author Grégory Van den Borre
 */
public interface City<T extends Building, D extends BuildingData> {

    /**
     * City name.
     * @return The name of the city, this must not be unique.
     */
    //@Ensures ("result != null")
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

    /**
     * Provide the list of all possible building data available.
     * @return The list.
     */
    //@Ensures ("result != null")
    List<D> getAllType();

    /**
     * Provide the maximum number of building allowed in this city.
     * @return The maximum number of buildings.
     */
    //@Ensures ("result >= 0")
    int getMaximumBuildings();

    /**
     * Provide the city id, retrieved from the associated entity.
     * @return The city unique id.
     */
    //@Ensures ("result != null")
    EntityId getId();

    /**
     * Provide the id of the owner of the city.
     * @return The owner of the city.
     */
    //@Ensures ("result != null")
    PlayerId getOwner();

    /**
     * Provide the position of the city in the world.
     * @return The city position.
     */
    //@Ensures("result != null")
    Point3D getPosition();

    /**
     * Set the state of the city resource producer to initialized so that it can start producing resources.
     */
    void initializeProducer();

    ResourcesProducer getProducer();

    List<T> getBuildings();

    D getByType(EntityType e);
}
