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

import be.yildiz.shared.city.City;
import be.yildiz.shared.data.EntityType;

import java.util.Map;

/**
 * Register types for buildings.
 *
 * @param <B> Building implementation.
 * @param <D> BuildingData implementation.
 * @author Grégory Van den Borre
 */
public interface BuildingTypeFactory<B extends Building, D extends BuildingData> {

    /**
     * @return The map containing all registered data.
     */
    //@Ensures result != null.
    Map<EntityType, D> getRegisteredData();

    /**
     * Create all the building contained in an empty city.
     *
     * @param city City to build.
     */
    //Requires city != null.
    //@Ensures the city will contains a building for all its building positions.
    void createEmptyCity(City<B, D> city);

}
