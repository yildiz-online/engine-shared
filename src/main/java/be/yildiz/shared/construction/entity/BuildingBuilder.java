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

package be.yildiz.shared.construction.entity;

import be.yildiz.common.id.EntityId;
import be.yildiz.common.id.PlayerId;
import be.yildiz.common.vector.Point3D;
import be.yildiz.shared.building.Building;
import be.yildiz.shared.construction.entity.EntityConstructionQueue.EntityRepresentationConstruction;
import lombok.NonNull;

import java.util.List;

/**
 * A building able to create new entities.
 * <p>
 * Immutable class.
 *
 * @param <B> Building implementation type.
 *
 * @author Grégory Van den Borre
 */
public abstract class BuildingBuilder<B extends Building> implements Builder {

    /**
     * Builder unique id.
     */
    private final EntityId builderId;

    /**
     * Position where the newly build entities will be located once built.
     */
    private final Point3D buildPosition;

    /**
     * Associated building.
     */
    private final Building building;

    private final EntityConstructionQueue queue;

    /**
     * Player owning this building.
     */
    private final PlayerId owner;

    /**
     * Instantiate a new BuildingBuilder.
     *
     * @param builderId     Builder unique id.
     * @param owner         Player owning this building.
     * @param buildPosition Position in the world.
     * @param building      Associated building.
     * @param queueMaxSize Maximum size of the builder queue.
     */
    protected BuildingBuilder( final EntityId builderId, final PlayerId owner, final Point3D buildPosition, final B building, final int queueMaxSize) {
        super();
        assert builderId != null;
        assert owner != null;
        assert buildPosition != null;
        assert building != null;
        this.builderId = builderId;
        this.buildPosition = buildPosition;
        this.building = building;
        this.queue = new EntityConstructionQueue(builderId, queueMaxSize);
        this.owner = owner;
    }

    @Override
    public EntityId getCity() {
        return this.building.getCity();
    }

    @Override
    public void setQueue(List<EntityRepresentationConstruction> l) throws EntityConstructionQueueFullException {
        this.queue.set(l);
    }

    @Override
    public void removeFromQueue(final int index) {
        this.queue.remove(index);
    }

    @Override
    public void addInQueue(EntityRepresentationConstruction r) throws EntityConstructionQueueFullException {
        this.queue.add(r);
    }

    @Override
    public EntityId getBuilderId() {
        return builderId;
    }

    @Override
    public Point3D getBuildPosition() {
        return buildPosition;
    }

    @Override
    public EntityConstructionQueue getQueue() {
        return queue;
    }

    @Override
    public PlayerId getOwner() {
        return owner;
    }
}
