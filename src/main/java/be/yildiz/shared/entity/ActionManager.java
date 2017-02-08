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

package be.yildiz.shared.entity;

import be.yildiz.common.collections.Lists;
import be.yildiz.common.framelistener.EndFrameListener;
import be.yildiz.common.framelistener.FrameManager;
import be.yildiz.shared.entity.action.Action;
import be.yildiz.shared.entity.action.ActionListener;
import lombok.NonNull;

import java.util.List;

/**
 * The action manager will loop over {@link Action} to run them, if the action is active, it will notify the listeners when an it is created, run, and completed. If the action is passive, the
 * listeners will never be notified about it.
 *
 * @author Grégory Van den Borre
 */
public class ActionManager<T extends Entity> extends EndFrameListener {

    /**
     * Listeners to notify when an action is created, has run or is complete.
     */
    private final List<ActionListener> listeners = Lists.newList();

    /**
     * Listeners to notify when an entity is destroyed.
     */
    private final List<DestructionListener<T>> destructionListeners = Lists.newList();

    /**
     * To get the list of active entities.
     */
    private final EntityManager<T> entityManager;

    private final List<ActionListener> listenerToRemove = Lists.newList();

    public ActionManager(final FrameManager frame, final EntityManager<T> em) {
        super();
        this.entityManager = em;
        frame.addFrameListener(this);
    }

    /**
     * Execute the actions required for every entities in the game.
     *
     * @param time Time since the begin of this frame.
     * @return true.
     */
    @Override
    public boolean frameEnded(final long time) {
        List<T> entities = this.entityManager.getEntities();
        this.listenerToRemove.forEach(this.listeners::remove);
        for (int i = 0; i < entities.size(); i++) {
            T e = entities.get(i);
            e.doActions(time);
            e.getActionRunning().forEach(a -> this.listeners.forEach(l -> l.execute(e.getId(), e.getOwner(), a)));
            e.getActionDone().forEach(a -> this.listeners.forEach(l -> l.complete(e.getId(), e.getOwner(), a)));
            if (e.isDeleted()) {
                this.entityManager.removeEntity(e);
                this.destructionListeners.forEach(l -> l.entityDestroyed(e));
                e.delete();
            }
        }
        return true;
    }

    /**
     * Register an action listener to notify when an action is created, run or completed.
     * Beware that nothing prevent to add the same listener several times.
     *
     * @param l Listener to add.
     * @throws NullPointerException if l parameter is <code>null</code>
     * post listener.size() == getSize()@pre + 1
     */
    public void addListener(@NonNull final ActionListener l) {
        this.listeners.add(l);
    }

    public void removeListener(@NonNull final ActionListener l) {
        this.listenerToRemove.add(l);
    }

    public void addDestructionListener(@NonNull final DestructionListener<T> l) {
        this.destructionListeners.add(l);
    }
}
