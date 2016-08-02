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

import be.yildiz.common.collections.Lists;
import be.yildiz.common.id.EntityId;
import be.yildiz.common.util.Time;
import be.yildiz.shared.data.EntityType;
import be.yildiz.shared.entity.module.ModuleGroup;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

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

    @Getter
    private final EntityId builderId;

    public EntityConstructionQueue(EntityId builderId) {
        super();
        this.builderId = builderId;
    }

    public void add(EntityRepresentationConstruction e) {
        this.entities.add(e);
    }

    public boolean isEmpty() {
        return this.entities.isEmpty();
    }

    public void set(EntityConstructionQueue l) {
        this.entities.clear();
        List<EntityRepresentationConstruction> list = l.getList();
        if(list.contains(null)) {
            throw new NullPointerException("The list contains null values.");
        }
        this.entities.addAll(l.getList());
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

    /**
     * An entity representation construction is the state of the build of an entity,
     * it contains the type, the modules to be built and the unique build index.
     */
    @EqualsAndHashCode
    @ToString
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


        public void reduceTimeLeft(final long timeToRemove) {
            long t = timeLeft.subtractMs(timeToRemove);
            if(t < 0) {
                t = 0;
            }
            this.timeLeft = Time.milliSeconds(t);
        }

        public boolean isTimeElapsed() {
            return this.timeLeft.timeInMs == 0;
        }
    }
}
