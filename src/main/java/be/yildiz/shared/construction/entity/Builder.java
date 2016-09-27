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

package be.yildiz.shared.construction.entity;

import be.yildiz.common.id.EntityId;
import be.yildiz.common.id.PlayerId;
import be.yildiz.common.vector.Point3D;
import be.yildiz.shared.construction.entity.EntityConstructionQueue.EntityRepresentationConstruction;
import be.yildiz.shared.data.ConstructionData;

import java.util.List;

/**
 * Building having the capacity to build entities.
 *
 * @author Grégory Van den Borre
 */
public interface Builder {

    /**
     * @return The unique id of this builder.
     */
    //@Ensures result != null
    EntityId getBuilderId();

    /**
     * @return The unique id of the player owning the builder.
     */
    //@Ensures result != null
    PlayerId getOwner();

    /**
     * @return The position where the built entity will be created.
     */
    //@Ensures result != null
    Point3D getBuildPosition();

    /**
     * Check if this builder is able to create a entity of a given type.
     *
     * @param data Data used for this construction.
     * @return <code>true</code> if this builder can create the entity, false otherwise.
     */
    boolean fullfilPrerequisite(ConstructionData data);

    /**
     * @return The queue used by this builder.
     */
    //@Ensures("result != null")
    EntityConstructionQueue getQueue();

    /**
     * Set the entity construction queue used by the builder.
     *
     * @param queue Queue to set
     */
    void setQueue(List<EntityRepresentationConstruction> queue) throws EntityConstructionQueueFullException;

    /**
     * Remove an item from the building queue.
     *
     * @param index Unique index of the build request.
     */
    void removeFromQueue(int index);

    /**
     * Add an element to build in the queue.
     *
     * @param r Element to build.
     */
    void addInQueue(EntityRepresentationConstruction r) throws EntityConstructionQueueFullException;

    /**
     * @return The unique id of the entity containing this builder.
     */
    //@Ensures result != null
    EntityId getCity();

}
