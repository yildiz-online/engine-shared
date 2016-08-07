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
import be.yildiz.common.id.PlayerId;
import be.yildiz.shared.construction.entity.EntityConstructionQueue.EntityRepresentationConstruction;
import be.yildiz.shared.entity.Entity;

import java.util.List;

/**
 * @param <T>
 * @author Grégory Van den Borre
 */
public class EntityConstructionQueueManager<T extends Entity> implements EntityConstructionListener<T> {

    private final List<EntityConstructionQueueListener> listeners = Lists.newList();

    private final BuilderManager builderManager;

    public EntityConstructionQueueManager(BuilderManager builderManager) {
        super();
        this.builderManager = builderManager;
    }

    public void willNotify(final EntityConstructionQueueListener l) {
        this.listeners.add(l);
    }

    /**
     * Cancel a construction and remove it from the queue.
     *
     * @param p     Player canceling the construction.
     * @param index Index of the construction.
     */
    public void cancel(final PlayerId p, final int index) {
        List<Builder> builders = builderManager.getBuilderByPlayer(p);
        for (Builder b : builders) {
            if (b.getQueue().remove(index)) {
                listeners.forEach(l -> l.notify(b.getQueue()));
            }
        }
    }

    public void update(final EntityConstructionQueue items) {
        Builder b = this.builderManager.getBuilderById(items.getBuilderId());
        b.setQueue(items);
        listeners.forEach(l -> l.notify(b.getQueue()));
    }

    /**
     * Add an entity in a list, if the list was empty, notify listener to start building.
     *
     * @param playerId
     * @param builderId
     * @param toBuild
     */
    public void addEntity(final PlayerId playerId, final EntityId builderId, final EntityRepresentationConstruction toBuild) {
        Builder b = this.builderManager.getBuilderById(builderId);
        b.addInQueue(toBuild);
        if (b.getQueue().getList().size() == 1) {
            listeners.forEach(l -> l.add(toBuild, playerId, builderId));
        }
        listeners.forEach(l -> l.notify(b.getQueue()));
    }

    @Override
    public void entityComplete(final T entity, final EntityId builder, final int index) {
        if (builder.equals(EntityId.WORLD)) {
            return;
        }
        Builder b = this.builderManager.getBuilderById(builder);
        b.removeFromQueue(index);
        if (!b.getQueue().isEmpty()) {
            EntityRepresentationConstruction nextToBuild = b.getQueue().getList().get(0);
            listeners.forEach(l -> l.add(nextToBuild, entity.getOwner(), builder));
        }
        listeners.forEach(l -> l.notify(b.getQueue()));
    }

    @Override
    public void entityConstructionCanceled(WaitingEntity w) {
        Builder b = this.builderManager.getBuilderById(w.builderId);
        b.removeFromQueue(w.representation.index);
        if (!b.getQueue().isEmpty()) {
            EntityRepresentationConstruction nextToBuild = b.getQueue().getList().get(0);
            listeners.forEach(l -> l.add(nextToBuild, w.entity.getOwner(), w.builderId));
        }
        listeners.forEach(l -> l.notify(b.getQueue()));
    }
}
