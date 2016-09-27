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

package be.yildiz.shared.building.staff;

import be.yildiz.shared.building.Building;
import be.yildiz.shared.building.BuildingData;
import be.yildiz.shared.city.City;

/**
 * Listener to notify for event about staff allocation in buildings.
 * @param <B> Building implementation.
 * @param <D> Building data implementation.
 * @param <C> City implementation.
 *
 * @author Grégory Van den Borre
 */
@FunctionalInterface
public interface StaffAllocationListener<B extends Building, D extends BuildingData, C extends City<B, D>> {

    /**
     * Notify for the time left to allocate the staff.
     *
     * @param city     BaseCity where the allocation occurs.
     * @param building Building in the city.
     * @param timeLeft Time left to allocate the staff.
     */
    default void updateTime(C city, B building, long timeLeft) {
    }

    /**
     * Notify when the allocation has occurred.
     *
     * @param city     BaseCity where the allocation occurs.
     * @param building Building in the city.
     * @param number   number of worker allocated.
     */
    void staffAllocated(C city, B building, int number);

}
