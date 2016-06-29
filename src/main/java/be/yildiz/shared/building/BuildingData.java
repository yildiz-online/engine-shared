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

package be.yildiz.shared.building;

import be.yildiz.shared.data.EntityType;
import be.yildiz.shared.data.Level;
import be.yildiz.shared.data.TimeToBuild;
import be.yildiz.shared.resources.ResourceValue;
import be.yildiz.shared.resources.bonus.BonusResources;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Contains all the data to create a building.
 *
 * @author Grégory Van den Borre
 */
public interface BuildingData {

    /**
     * @return The type of the building.
     */
    EntityType getType();

    /**
     * @return The price of this type.
     */
    ResourceValue getPrice();

    /**
     * @return The time to build this type.
     */
    TimeToBuild getTimeToBuild();

    /**
     * @return The bonus provided by this building current allocated staff.
     */
    BonusResources getStaffBonus(int staffAllocated);

    /**
     * @return The bonus provided by this building current level.
     */
    BonusResources getLevelBonus(Level level);

    boolean isEmpty();

    boolean isBuilder();

    boolean hasRatioBonus();

    /**
     * @param level Level matching the data to retrieve.
     * @return The building price for a given level.
     */
    ResourceValue getPrice(final Level level);

    /**
     * Retrieve the time to build the building at a given level.
     *
     * @param level Level matching the data to retrieve.
     * @return The time to build this building.
     */
    TimeToBuild getTimeToBuild(final Level level);

    /**
     * Retrieve the max population the building at a given level.
     *
     * @param level Level matching the data to retrieve.
     * @return The time max population in this building.
     * @requires level <= this.maxLevel
     */
    int getMaxPopulation(final Level level);

    /**
     * @return The building maximum level.
     */
    Level getMaxLevel();

    boolean isBuildable();

    /**
     * Contains the time and price to build a building at a specified level.
     *
     * @author Van den Borre Grégory
     */
    @AllArgsConstructor
    @Getter
    final class LevelData {

        /**
         * Level of the data.
         */
        private final int level;

        /**
         * Time needed to create the type.
         */
        private final TimeToBuild timeToBuild;

        /**
         * price to create it, stored in a array in case of more than one type of resource is needed.
         */
        private final ResourceValue price;

        /**
         * Maximum possible worker in this building.
         */
        private final int maxPopulation;
    }
}
