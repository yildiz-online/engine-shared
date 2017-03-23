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

import be.yildiz.shared.data.EntityType;
import be.yildiz.shared.data.Level;
import be.yildiz.shared.data.TimeToBuild;
import be.yildiz.shared.resources.ResourceValue;
import be.yildiz.shared.resources.bonus.BonusResources;

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
     * @return The building price if it is level 1.
     */
    ResourceValue getPrice();

    /**
     * Retrieve the time to build the building at level 1.
     *
     * @return The time to build this building if it is level 1, for other levels, use getForLevel() method.
     */
    TimeToBuild getTimeToBuild();

    /**
     * Provide the appropriated bonus for the staff allocated.
     *
     * @param staff Allocated staff in the building.
     * @return The matching bonus.
     */
    //@requires staff >= 0.
    BonusResources getStaffBonus(int staff);

    /**
     * Provide the appropriated bonus for the building level.
     *
     * @param level Building current level.
     * @return The matching bonus.
     */
    //@requires level not null.
    BonusResources getLevelBonus(Level level);

    /**
     * Check if the building is build or the place is still empty.
     * @return <code>true</code> if the building has not been built yet.
     */
    boolean isEmpty();

    /**
     * Check if the building can build entities or not.
     * @return <code>true</code> if the building is able to create entities.
     */
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
     */
    //@requires level <= this.maxLevel
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

        public LevelData(int level, TimeToBuild timeToBuild, ResourceValue price, int maxPopulation) {
            super();
            this.level = level;
            this.timeToBuild = timeToBuild;
            this.price = price;
            this.maxPopulation = maxPopulation;
        }

        public int getLevel() {
            return level;
        }

        public TimeToBuild getTimeToBuild() {
            return timeToBuild;
        }

        public ResourceValue getPrice() {
            return price;
        }

        public int getMaxPopulation() {
            return maxPopulation;
        }
    }
}
