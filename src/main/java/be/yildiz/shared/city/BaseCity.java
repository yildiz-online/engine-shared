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

import be.yildiz.common.collections.Lists;
import be.yildiz.common.id.EntityId;
import be.yildiz.common.id.PlayerId;
import be.yildiz.common.vector.Point3D;
import be.yildiz.shared.building.Building;
import be.yildiz.shared.building.BuildingData;
import be.yildiz.shared.data.BuildingPosition;
import be.yildiz.shared.data.EntityType;
import be.yildiz.shared.entity.Entity;
import be.yildiz.shared.resources.ResourceValue;
import be.yildiz.shared.resources.ResourcesProducer;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * A BaseCity.
 *
 * @author Grégory Van den Borre
 *         <T> Building implementation. <D> BuildingData implementation.
 * @specfield id:EntityId:BaseCity unique id.
 * @specfield name:BaseCity name in the game, must not be unique, can only contains alpha numeric characters, size is 1-10.
 * @specfield owner:PlayerId:Player owner of this city.
 * @specfield producer:ResourcesProducer:Resource production for this city.
 * @specfield position:Point3D:BaseCity position in the world.
 */
public class BaseCity<T extends Building, D extends BuildingData> implements City<T, D> {

    /**
     * Buildings positions in the world.
     */
    private final Point3D[] positionOffset;

    /**
     * Associated entity.
     */
    private final Entity entity;

    /**
     * List of buildings.
     */
    @Getter
    private final List<T> buildings;

    /**
     * Resource production for this city.
     */
    @Getter
    private final ResourcesProducer producer;

    /**
     * Data for buildings in the city.
     */
    private final Map<EntityType, D> datas;

    /**
     * Create a new BaseCity.
     *
     * @param entity The entity representing this city in the world.
     * @param initialResource The resources available in the city when creating it.
     * @param positionOffset Building positions.
     * @param datas List of building types and their data available.
     */
    protected BaseCity(final Entity entity, final ResourceValue initialResource, final Point3D[] positionOffset, Map<EntityType, D> datas) {
        super();
        this.entity = entity;
        this.datas = datas;
        this.buildings = Lists.newList();
        this.positionOffset = Arrays.copyOf(positionOffset, positionOffset.length);
        for (int i = 0; i < this.positionOffset.length; i++) {
            this.positionOffset[i] = this.positionOffset[i].add(entity.getPosition());
        }
        this.producer = new ResourcesProducer(entity.getId(), System.currentTimeMillis(), initialResource);
    }

    @Override
    public D getByType(final EntityType type) {
        return this.datas.get(type);
    }

    @Override
    public String getName() {
        return this.entity.getName();
    }

    @Override
    public boolean hasNegativeProductionRatio() {
        return this.producer.hasNegativeRatio();
    }

    @Override
    public Point3D getBuildingPosition(final BuildingPosition position) {
        return positionOffset[position.value];
    }

    @Override
    public int getAllocatedStaff() {
        int total = 0;
        for (T b : this.buildings) {
            total += b.getOldStaff();
        }
        return total;
    }

    @Override
    public T getBuilding(final BuildingPosition position) {
        return this.buildings.get(position.value);
    }

    @Override
    public void createConstruction(final T building) {
        if (this.buildings.size() > building.getBuildingPosition().value) {
            this.buildings.remove(building.getBuildingPosition().value);
        }
        this.buildings.add(building.getBuildingPosition().value, building);
    }

    @Override
    public Set<EntityType> getAllowedType() {
        Set<EntityType> l = this.datas.keySet();
        for (Building b : this.buildings) {
            l.remove(b.getType());
        }
        return l;
    }

    @Override
    public List<D> getAllType() {
        return Lists.newList(this.datas.values());
    }

    @Override
    public int getMaximumBuildings() {
        return this.positionOffset.length;
    }

    @Override
    public EntityId getId() {
        return this.entity.getId();
    }

    @Override
    public PlayerId getOwner() {
        return this.entity.getOwner();
    }

    @Override
    public Point3D getPosition() {
        return this.entity.getPosition();
    }

    @Override
    public void initializeProducer() {
        this.getProducer().setInitialised();
    }
}
