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
import be.yildiz.common.collections.Sets;
import be.yildiz.common.framelistener.EndFrameListener;
import be.yildiz.common.framelistener.FrameManager;
import be.yildiz.common.id.EntityId;
import be.yildiz.common.id.PlayerId;
import be.yildiz.common.log.Logger;
import be.yildiz.shared.construction.entity.EntityConstructionQueue.EntityRepresentationConstruction;
import be.yildiz.shared.entity.*;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Check all builder List and execute their build method. Primary task is Call all builder to create their units, if they don't have anything to create, they are removed from the builder list.
 * @param <T> Entity implementation.
 * @author Grégory Van den Borre
 */
public class EntityConstructionManager<T extends Entity> extends EndFrameListener implements CompleteEntityConstructionManager<T> {

    /**
     * List of entities waiting to be build.
     */
    private final List<WaitingEntity> entityToBuildList = Lists.newList();

    /**
     * Factory to build the entities.
     */
    private final EntityFactory<T> associatedFactory;

    /**
     * Listener to notify when a construction is completed.
     */
    private final Set<EntityConstructionListener<T>> listenerList = Sets.newInsertionOrderedSet();

    /**
     * Create a new BuilderManager.
     * @param frame Frame manager listening to this object.
     * @param actionManager Unused to remove.
     * @param factory Entity factory to materialize entities.
     */
    public EntityConstructionManager(FrameManager frame, ActionManager<T, ? extends EntityData> actionManager, EntityFactory<T> factory) {
        super();
        this.associatedFactory = factory;
        frame.addFrameListener(this);
    }

    @Override
    public void createEntity(final EntityInConstruction entity, final EntityId builderId, final int index) {
        T buildEntity = this.associatedFactory.createEntity(entity);
        Logger.debug("Entity built " + entity.getId());
        this.listenerList.forEach(l -> l.entityComplete(buildEntity, builderId, index));
    }

    @Override
    public void createEntity(final DefaultEntityInConstruction entity, final EntityId builderId, final EntityRepresentationConstruction c) {
        WaitingEntity data = new WaitingEntity(entity, c, builderId);
        this.entityToBuildList.add(data);
        this.listenerList.forEach(l -> l.addEntityToCreate(data));
    }

    @Override
    public void cancel(final WaitingEntity w) {
        if (this.entityToBuildList.remove(w)) {
            this.listenerList.forEach(l -> l.entityConstructionCanceled(w));
        }
    }

    /**
     * Call the building logic for all builder in the list.
     *
     * @param time Time since the last call.
     */
    @Override
    public boolean frameEnded(final long time) {
        for (int i = 0; i < this.entityToBuildList.size(); i++) {
            WaitingEntity waitingEntity = this.entityToBuildList.get(i);
            waitingEntity.representation.reduceTimeLeft(time);
            if (waitingEntity.representation.isTimeElapsed()) {
                T buildEntity = this.associatedFactory.createEntity(waitingEntity.entity);
                Logger.debug("Entity built " + waitingEntity.entity.getId());
                this.listenerList.forEach(l -> l.entityComplete(buildEntity, waitingEntity.builderId, waitingEntity.representation.index));
                this.entityToBuildList.remove(i);
                i--;
            }

        }
        return true;
    }

    @Override
    public void willNotify(final EntityConstructionListener<T>... listeners) {
        if (listeners != null) {
            for (EntityConstructionListener<T> listener : listeners) {
                this.listenerList.add(listener);
            }
        }

    }

    /**
     * Remove a listener to notify when a construction is completed.
     *
     * @param listener Listener to remove.
     */
    public void removeListener(final EntityConstructionListener<T> listener) {
        this.listenerList.remove(listener);
    }

    @Override
    public List<WaitingEntity> getEntityToBuildList() {
        return Collections.unmodifiableList(this.entityToBuildList);
    }

    @Override
    public List<WaitingEntity> getEntityToBuildList(final PlayerId player) {
        return this.entityToBuildList.stream()
                .filter(w -> w.isOwned(player))
                .collect(Collectors.toList());
    }
}
