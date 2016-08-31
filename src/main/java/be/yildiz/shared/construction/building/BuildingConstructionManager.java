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

package be.yildiz.shared.construction.building;

import be.yildiz.common.collections.CollectionUtil;
import be.yildiz.common.collections.Lists;
import be.yildiz.common.collections.Maps;
import be.yildiz.common.collections.Sets;
import be.yildiz.common.framelistener.EndFrameListener;
import be.yildiz.shared.building.Building;
import be.yildiz.shared.building.BuildingData;
import be.yildiz.shared.city.City;
import be.yildiz.shared.city.CityManager;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Check all builder List and execute their build method. Primary task is Call all builder to create their units, if they don't have anything to create, they are removed from the builder list.
 *
 * @author Grégory Van den Borre
 */
public class BuildingConstructionManager<B extends Building, D extends BuildingData, C extends City<B, D>> extends EndFrameListener {

    /**
     * Associated city manager to retrieve cities.
     */
    private final CityManager<B,D,C> cityManager;

    /**
     * List of construction waiting to be build.
     */
    private final List<WaitingBuilding<B>> constructionToBuildList = Lists.newList();

    /**
     * List of the building to build by city.
     */
    private final Map<C, Set<WaitingBuilding<B>>> constructionToBuildByCity = Maps.newMap();

    /**
     * Listener to notify when a construction is completed.
     */
    private final Set<BuildingConstructionListener<B, D, C>> listenerList = Sets.newInsertionOrderedSet();

    /**
     * Factory to build the entities.
     */
    private final BuildingFactory<B> associatedFactory;

    /**
     * Create a new BuilderManager.
     *
     * @param cityManager Associated BaseCityManager.
     * @param factory Factory to create the materialization for the buildings.
     */
    public BuildingConstructionManager(final CityManager<B,D,C> cityManager, final BuildingFactory<B> factory) {
        super();
        this.cityManager = cityManager;
        this.associatedFactory = factory;
    }

    /**
     * Add a building to build in the builder list if time left is above 0, otherwise, building is built immediately.
     *
     * @param b        Data to build the Building.
     * @param timeLeft Time to wait before the build is complete.
     */
    public void createBuilding(final B b, final long timeLeft) {
        C city = this.cityManager.getCityById(b.getCity());
        if (timeLeft > 0) {
            WaitingBuilding<B> data = new WaitingBuilding<>(b, timeLeft);
            CollectionUtil.getOrCreateSetFromMap(this.constructionToBuildByCity, city).add(data);
            this.constructionToBuildList.add(data);
        } else {
            this.associatedFactory.createBuilding(b);
            this.listenerList.forEach(l -> l.buildingComplete(city, b));
        }
    }

    /**
     * Create a building with no delay.
     * @param b Building to create.
     */
    public void createBuilding(final B b) {
        this.createBuilding(b, 0);
    }

    /**
     * Call the building logic for all builder in the list.
     *
     * @param time Time since the last call.
     */
    @Override
    public boolean frameEnded(final long time) {
        for (int i = 0; i < this.constructionToBuildList.size(); i++) {
            WaitingBuilding<B> waitingBuilding = this.constructionToBuildList.get(i);
            waitingBuilding.time -= time;
            B building = waitingBuilding.getB();
            C city = this.cityManager.getCityById(building.getCity());
            if (waitingBuilding.time <= 0) {
                this.associatedFactory.createBuilding(building);
                this.listenerList.forEach(l -> l.buildingComplete(city, building));
                this.constructionToBuildByCity.get(city).remove(waitingBuilding);
                this.constructionToBuildList.remove(i);
                i--;
            } else {
                this.listenerList.forEach(l -> l.buildingInConstruction(city, building, waitingBuilding.time));
            }
        }
        return true;
    }

    /**
     * Add one or several listener to notify when a construction is completed.
     *
     * @param listeners Listener to notify.
     */
    public void willNotify(final BuildingConstructionListener<B, D, C>... listeners) {
        if (listeners != null) {
            for (BuildingConstructionListener<B, D, C> listener : listeners) {
                this.listenerList.add(listener);
            }
        }
    }

    /**
     * Remove a listener to notify when a construction is completed.
     *
     * @param listener Listener to remove.
     */
    public void removeListener(final BuildingConstructionListener<B, D, C> listener) {
        this.listenerList.remove(listener);
    }

    /**
     * @return The list of buildings in the building queue.
     */
    public List<WaitingBuilding<B>> getBuildingList() {
        return Collections.unmodifiableList(this.constructionToBuildList);
    }

    /**
     * @param c City.
     * @return The list of buildings to build for a city.
     */
    public Set<WaitingBuilding<B>> getBuildingList(C c) {
        return Collections.unmodifiableSet(this.constructionToBuildByCity.getOrDefault(c, Sets.newSet()));
    }

}
