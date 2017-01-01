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

import be.yildiz.common.collections.CollectionUtil;
import be.yildiz.common.collections.Lists;
import be.yildiz.common.collections.Maps;
import be.yildiz.common.id.EntityId;
import be.yildiz.common.id.PlayerId;
import be.yildiz.shared.building.Building;
import be.yildiz.shared.building.BuildingData;
import be.yildiz.shared.building.BuildingTypeFactory;
import be.yildiz.shared.data.EntityType;
import be.yildiz.shared.entity.Entity;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @param <T> Building implementation.
 * @param <D> Building data implementation.
 * @param <C> City implementation.
 * @author Grégory Van den Borre
 */
public abstract class BaseCityManager<T extends Building, D extends BuildingData, C extends City<T, D>> implements CityManager<T, D, C> {

    /**
     * List of all BaseCity for a Player.
     */
    private final Map<PlayerId, Set<C>> cityList = Maps.newMap();

    /**
     * List of all BaseCity, by Id.
     */
    private final Map<EntityId, C> cities = Maps.newMap();

    private final BuildingTypeFactory<T, D> typeFactory;

    protected BaseCityManager(BuildingTypeFactory<T, D> typeFactory) {
        super();
        this.typeFactory = typeFactory;
    }


    /**
     * Register a new BaseCity in the system.
     *
     * @param entity Associated entity.
     */
    @Override
    public C createCity(final Entity entity) {
        C city = this.createCityImpl(entity);
        CollectionUtil.getOrCreateSetFromMap(this.cityList, entity.getOwner()).add(city);
        this.cities.put(entity.getId(), city);
        return city;
    }

    protected abstract C createCityImpl(final Entity entity);

    @Override
    public C getCityById(final EntityId id) {
        return this.cities.get(id);
    }

    @Override
    public final List<C> getCities() {
        return Lists.newList(this.cities.values());
    }

    @Override
    public Set<C> getCities(final PlayerId player) {
        return this.cityList.getOrDefault(player, Collections.emptySet());
    }

    @Override
    public void createEmptyCityBuildings(C city) {
        this.typeFactory.createEmptyCity(city);
    }

    @Override
    public D getData(EntityType entityType) {
        return this.typeFactory.getRegisteredData().get(entityType);
    }
}
