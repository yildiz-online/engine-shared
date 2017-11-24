/*
 * This file is part of the Yildiz-Engine project, licenced under the MIT License  (MIT)
 *
 *  Copyright (c) 2017 Grégory Van den Borre
 *
 *  More infos available: https://www.yildiz-games.be
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 *  documentation files (the "Software"), to deal in the Software without restriction, including without
 *  limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 *  of the Software, and to permit persons to whom the Software is furnished to do so,
 *  subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be included in all copies or substantial
 *  portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 *  WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS
 *  OR COPYRIGHT  HOLDERS BE LIABLE FOR ANY CLAIM,
 *  DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE  SOFTWARE.
 *
 */

package be.yildiz.shared;

import be.yildiz.common.collections.Lists;
import be.yildiz.common.id.PlayerId;
import be.yildiz.module.physics.CollisionListener;
import be.yildiz.module.physics.CollisionResult;
import be.yildizgames.engine.feature.entity.Entity;
import be.yildizgames.engine.feature.entity.EntityManager;
import be.yildizgames.engine.feature.entity.LosListener;
import be.yildizgames.engine.feature.player.Player;
import be.yildizgames.engine.feature.player.PlayerManager;

import java.util.List;

/**
 * Manage and dispatch the line of sight events.
 *
 * @author Grégory Van den Borre
 */
public class LosManager<T extends Entity> implements CollisionListener {

    /**
     * Listeners to notify about los events changes.
     */
    private final List<LosListener<T>> listenerList = Lists.newList();

    /**
     * Associated entity manager.
     */
    private final EntityManager<T> entityManager;

    public LosManager(EntityManager<T> entityManager) {
        super();
        this.entityManager = entityManager;
    }

    /**
     * An entity has entered in the field of view of another entity.
     *
     * @param r Result of the collision.
     */
    //@Requires r != null
    //@Ensures r.getObject2.isSeenBy(r.getObject1.getOwner()) == true
    //@Ensures r.object1.getOwner().isSeeing(r.object2) == true
    @Override
    public final void newCollision(final CollisionResult r) {
        T viewer = this.entityManager.findById(r.object1);
        T seen = this.entityManager.findById(r.object2);
        if (viewer.hasSameOwnerAs(seen) || !viewer.see(seen)) {
            return;
        }
        PlayerId playerViewer = viewer.getOwner();
        Player p = PlayerManager.getInstance().findFromId(playerViewer);
        if (p.see(seen.getId())) {
            this.listenerList.forEach(l -> l.playerSee(playerViewer, seen));
        }
        this.listenerList.forEach(l -> l.see(viewer, seen));
    }

    /**
     * Occurs when 2 entities are no longer colliding, notify the listeners that they are not visible if they were and only if they do not belong to the same player.
     */
    @Override
    public final void lostCollision(final CollisionResult r) {
        T viewer = this.entityManager.findById(r.object1);
        T unseen = this.entityManager.findById(r.object2);
        if (!viewer.getOwner().equals(unseen.getOwner()) && viewer.isSeeing(unseen)) {
            if (PlayerManager.getInstance().findFromId(viewer.getOwner()).noLongerSee(unseen.getId())) {
                this.listenerList.forEach(l -> l.noLongerSee(viewer, unseen));
            }
        }
    }

    /**
     * Add a new listener to notify about LOS event changes.
     *
     * @param listener Listener to add.
     */
    public final void willNotify(final LosListener<T> listener) {
        this.listenerList.add(listener);
    }
}
