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

package be.yildiz.shared.research;

import be.yildiz.common.collections.Lists;
import be.yildiz.common.collections.Maps;
import be.yildiz.common.collections.Sets;
import be.yildiz.common.id.PlayerId;
import be.yildiz.common.log.Logger;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

//FIXME use an object PlayerResearch instead of a manager

/**
 * @author Grégory Van den Borre
 */
public final class ResearchManager {

    private static final ResearchManager INSTANCE = new ResearchManager();

    /**
     * List of all listeners.
     */
    private final List<ResearchListener> listenerList = Lists.newList();

    private final Map<PlayerId, Set<ResearchId>> researches = Maps.newMap();

    private ResearchManager() {
        super();
    }

    public static ResearchManager getInstance() {
        return INSTANCE;
    }

    /**
     * Add a new research.
     *
     * @param res    Research to add.
     * @param player Player doing the research.
     */
    public void addResearch(final ResearchId res, final PlayerId player) {
        this.researches.putIfAbsent(player, Sets.newSet());
        Set<ResearchId> list = this.researches.get(player);
        if (list.contains(res)) {
            this.listenerList.forEach(l -> l.researchAlreadyDone(res, player));
        } else {
            list.add(res);
            this.listenerList.forEach(l -> l.researchCompleted(res, player));
        }
    }

    /**
     * Add a listener for research event.
     *
     * @param listener Listener to add.
     */
    public void addListener(final ResearchListener listener) {
        this.listenerList.add(listener);
    }

    /**
     * Retrieve the research state for a given research.
     *
     * @param player Player to check.
     * @param id Research to check.
     * @return The state of the given research.
     */
    public ResearchState getResearchState(final PlayerId player, final ResearchId id) {
        this.researches.putIfAbsent(player, Sets.newSet());
        Set<ResearchId> list = this.researches.get(player);
        if (list.contains(id)) {
            return ResearchState.DONE;
        }
        Research research = Research.get(id);
        if(research == null) {
            Logger.warning("Research " + id + " not registered.");
            return ResearchState.UNAVAILABLE;
        }
        if (!research.getPrerequisite().isPresent()) {
            return ResearchState.AVAILABLE;
        }

        return research.getPrerequisite()
                .filter(list::contains)
                .isPresent() ? ResearchState.AVAILABLE : ResearchState.UNAVAILABLE;
    }

    /**
     * Check if a given player has a research complete.
     * @param player Player to check.
     * @param res Research to check.
     * @return <code>true</code> If the given player has completed to given research.
     */
    public boolean hasResearch(final PlayerId player, final ResearchId res) {
        return this.researches.putIfAbsent(player, Sets.newSet()).contains(res);
    }

    /**
     * Get all the researches for a player.
     * @param player Player to get the researches.
     * @return All the researches completed for the given player.
     */
    public Set<ResearchId> getResearchList(final PlayerId player) {
        return this.researches.getOrDefault(player, Collections.emptySet());
    }

    /**
     * State of a research for a player.
     *
     * @author Van den Borre Grégory
     */
    public enum ResearchState {

        /**
         * Research not already done with all prerequisites filled.
         */
        AVAILABLE,

        /**
         * Research not already done with missing prerequisite.
         */
        UNAVAILABLE,

        /**
         * Research already done.
         */
        DONE
    }
}
