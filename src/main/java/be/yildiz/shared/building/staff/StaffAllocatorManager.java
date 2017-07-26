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

package be.yildiz.shared.building.staff;

import be.yildiz.common.collections.Lists;
import be.yildiz.common.collections.Sets;
import be.yildiz.common.framelistener.EndFrameListener;
import be.yildiz.shared.building.Building;
import be.yildiz.shared.building.BuildingData;
import be.yildiz.shared.city.City;
import be.yildiz.shared.city.CityManager;

import java.util.List;
import java.util.Set;

/**
 * Manager to allocate staff in a building.
 * @param <B> Building implementation.
 * @param <D> Building data implementation.
 * @param <C> City implementation.
 *
 * @author Grégory Van den Borre
 */
public class StaffAllocatorManager<B extends Building, D extends BuildingData, C extends City<B, D>> extends EndFrameListener {

    /**
     * List of building waiting for staff allocation.
     */
    private final List<BuildingToAllocate<B>> toAllocateList = Lists.newList();

    /**
     * List of listeners to notify about allocation changes.
     */
    private final Set<StaffAllocationListener<B, D, C>> listenerList = Sets.newInsertionOrderedSet();

    /**
     *
     */
    private final CityManager<B,D,C> cityManager;

    /**
     * Simple constructor.
     * @param cityManager Associated city manager.
     */
    public StaffAllocatorManager(final CityManager<B,D,C> cityManager) {
        super();
        this.cityManager = cityManager;
    }


    /**
     * Allocate staff in a building.
     *
     * @param building Building where the staff is allocated.
     * @param number   Number of worker to allocate.
     * @param time     Time left to complete the allocation.
     */
    public void add(final B building, final Staff number, final long time) {
        BuildingToAllocate<B> toAllocate = new BuildingToAllocate<>(building, number, time);
        // FIXME clean!!!
        this.toAllocateList.add(toAllocate);
        // Staff is allocated now to prevent being reused while countdown is
        // active
        // but listeners will not be notified of it.
        toAllocate.building.setOldStaff();
        toAllocate.building.setStaff(toAllocate.workerNumber);
    }

    @Override
    public boolean frameEnded(final long frameTime) {
        long now = System.currentTimeMillis();
        for (int i = 0; i < this.toAllocateList.size(); i++) {
            BuildingToAllocate<B> toAllocate = this.toAllocateList.get(i);
            C c = this.cityManager.getCityById(toAllocate.building.getCity());
            if (now >= toAllocate.timeAdded + toAllocate.timeToAllocate) {
                toAllocate.building.setOldStaff();
                this.listenerList.forEach(l -> l.staffAllocated(c, toAllocate.building, toAllocate.workerNumber));
                this.toAllocateList.remove(i);
                i--;
            } else {
                long timeLeft = toAllocate.timeToAllocate - (now - toAllocate.timeAdded);
                this.listenerList.forEach(l -> l.updateTime(c, toAllocate.building, timeLeft));
            }
        }
        return true;
    }

    /**
     * Add a new listener to notify if staff allocation events occur.
     *
     * @param listener Listener to add.
     */
    public void willNotify(final StaffAllocationListener<B, D, C> listener) {
        this.listenerList.add(listener);
    }

    /**
     * Simple container for all data to allocate staff in a building.
     *
     * @author Van den Borre Grégory
     */
    private static final class BuildingToAllocate<B> {

        /**
         * Building.
         */
        private final B building;

        /**
         * Number of workers to allocate to that building.
         */
        private final Staff workerNumber;

        /**
         * Time to complete the building allocation.
         */
        private final long timeToAllocate;

        /**
         * Time stamp when this object was created.
         */
        private final long timeAdded;

        /**
         * Full constructor.
         *
         * @param workerNumber Number of workers to allocate.
         */
        private BuildingToAllocate(final B building, final Staff workerNumber, final long timeToAllocate) {
            super();
            this.building = building;
            this.workerNumber = workerNumber;
            this.timeAdded = System.currentTimeMillis();
            this.timeToAllocate = timeToAllocate;
        }
    }
}
