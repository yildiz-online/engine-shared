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

import be.yildiz.common.collections.Lists;
import be.yildiz.common.id.EntityId;
import be.yildiz.common.util.Time;
import be.yildiz.shared.data.EntityType;
import be.yildiz.shared.entity.module.ModuleGroup;

import java.util.Collections;
import java.util.List;

/**
 * Simple list of representation for entities in a queue waiting for construction.
 *
 * @author Grégory Van den Borre
 */
public final class EntityConstructionQueue {

    /**
     * Wrapped list of elements,
     */
    private final List<EntityRepresentationConstruction> entities = Lists.newList();

    /**
     * Id of the builder holding this queue.
     */
    private final EntityId builderId;

    /**
     * Queue maximum size.
     */
    private int maxSize = 0;

    /**
     * Create a new instance.
     *
     * @param builderId Id of the builder holding this queue.
     * @param maxSize Maximum size for the queue.
     * @throws NullPointerException If builderId is null.
     */
    public EntityConstructionQueue(final EntityId builderId, final int maxSize) {
        super();
        assert builderId != null;
        this.builderId = builderId;
        this.maxSize = maxSize;
    }

    /**
     * Add a new entity to build in the queue.
     *
     * @param e Entity to build data.
     */
    public void add(final EntityRepresentationConstruction e) {
        if(this.entities.size() == this.maxSize) {
            throw new EntityConstructionQueueFullException();
        }
        this.entities.add(e);
    }

    public boolean isEmpty() {
        return this.entities.isEmpty();
    }

    /**
     * Reset the construction list with new values.
     *
     * @param list New values to set in the list.
     * @throws EntityConstructionQueueFullException If the list size is bigger than the max size.
     */
    public void set(List<EntityRepresentationConstruction> list) {
        this.entities.clear();
        if (list.size() > this.maxSize) {
            throw new EntityConstructionQueueFullException();
        }
        if (list.contains(null)) {
            throw new NullPointerException("The list contains null values.");
        }
        this.entities.addAll(list);
    }

    public List<EntityRepresentationConstruction> getList() {
        return Collections.unmodifiableList(entities);
    }

    public boolean remove(int request) {
        for (EntityRepresentationConstruction e : this.entities) {
            if (e.index == request) {
                this.entities.remove(e);
                return true;
            }
        }
        return false;
    }

    /**
     * Compute the number of entities for a given type.
     *
     * @param type Type to check.
     * @return The number of entities in the list matching the type.
     */
    public int getNumberOfEntities(final EntityType type) {
        int result = 0;
        for (EntityRepresentationConstruction c : this.entities) {
            if (c.type.equals(type)) {
                result++;
            }
        }
        return result;
    }

    public boolean hasOnlyOneElement() {
        return this.entities.size() == 1;
    }

    public EntityId getBuilderId() {
        return builderId;
    }

    /**
     * An entity representation construction is the state of the build of an entity,
     * it contains the type, the modules to be built and the unique build index.
     */
    public static final class EntityRepresentationConstruction {

        /**
         * Type of the entity to build.
         */
        public final EntityType type;

        /**
         * Modules used in this entity.
         */
        public final ModuleGroup data;

        /**
         * Construction unique index.
         */
        public final int index;

        /**
         * Time left before the construction is complete.
         */
        private Time timeLeft;

        public EntityRepresentationConstruction(EntityType type, ModuleGroup data, int index, Time timeLeft) {
            this.type = type;
            this.data = data;
            this.index = index;
            this.timeLeft = timeLeft;
        }

        /**
         * @return The time before construction completion in milliseconds.
         */
        public long getTime() {
            return this.timeLeft.timeInMs;
        }

        /**
         * Update the time left.
         *
         * @param timeToRemove Time spent since the last update.
         */
        public void reduceTimeLeft(final long timeToRemove) {
            long t = timeLeft.subtractMs(timeToRemove);
            if (t < 0) {
                t = 0;
            }
            this.timeLeft = Time.milliSeconds(t);
        }

        /**
         * @return True if the time required to build the entity is elapsed.
         */
        public boolean isTimeElapsed() {
            return this.timeLeft.timeInMs <= 0;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }

            EntityRepresentationConstruction that = (EntityRepresentationConstruction) o;

            if (index != that.index) {
                return false;
            }
            if (!type.equals(that.type)) {
                return false;
            }
            if (!data.equals(that.data)) {
                return false;
            }
            return timeLeft.equals(that.timeLeft);
        }

        @Override
        public int hashCode() {
            int result = type.hashCode();
            result = 31 * result + data.hashCode();
            result = 31 * result + index;
            result = 31 * result + timeLeft.hashCode();
            return result;
        }

        @Override
        public String toString() {
            return "EntityRepresentationConstruction{" +
                    "type=" + type +
                    ", data=" + data +
                    ", index=" + index +
                    ", timeLeft=" + timeLeft +
                    '}';
        }
    }
}
