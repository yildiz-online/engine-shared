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
import be.yildiz.shared.construction.entity.EntityConstructionQueue.EntityRepresentationConstruction;
import be.yildiz.shared.entity.DefaultEntityInConstruction;
import be.yildiz.shared.entity.Entity;
import be.yildiz.shared.entity.EntityToCreate;

import java.util.List;

/**
 *
 * @param <T> Entity implementation.
 * @author Grégory Van den Borre
 */
public interface CompleteEntityConstructionManager<T extends Entity> extends SimpleEntityConstructionManager<T> {

    void createEntity(EntityToCreate entity);

    /**
     * Cancel an entity to build.
     *
     * @param w Entity to cancel.
     */
    //@Requires w != null
    void cancel(WaitingEntity w);

    /**
     * Add a entity to build in the builder list.
     *
     * @param eic  Data to build the Entity.
     * @param builderId Id of the building or entity creating this entity.
     * @param c Metadata about the construction current status.
     */
    void createEntity(DefaultEntityInConstruction eic, EntityId builderId, EntityRepresentationConstruction c);

    /**
     * @return The list of entities in the building queue.
     */
    //@Ensures("result != null")
    List<WaitingEntity> getEntityToBuildList();

    /**
     * @param id Id of the owner.
     * @return The list of entities in the building queue for a given player.
     * @throws NullPointerException if id is null.
     */
    //@Ensures("result != null")
    List<WaitingEntity> getEntityToBuildList(PlayerId id);

}
