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
import be.yildiz.common.collections.Maps;
import be.yildiz.common.id.EntityId;
import be.yildiz.common.id.PlayerId;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Keep track of all existing builders.
 * @author Grégory Van den Borre
 */
public class BuilderManager {

    /**
     * List of builder by their id.
     */
    private final Map<EntityId, Builder> builderList = Maps.newMap();

    /**
     * List of all builders for a given player.
     */
    private final Map<PlayerId, List<Builder>> buildersByPlayer = Maps.newMap();


    /**
     * Retrieve a builder by its id.
     * @param builderId Builder unique id.
     * @return The builder matching the given id.
     */
    //@Ensures("result != null")
    public Optional<Builder> getBuilderById(final EntityId builderId) {
        return Optional.ofNullable(this.builderList.get(builderId));
    }

    /**
     * Register a new builder.
     * @param builder Builder to register.
     */
    public void addBuilder(final Builder builder) {
        this.builderList.put(builder.getBuilderId(), builder);
        if (!this.buildersByPlayer.containsKey(builder.getOwner())) {
            this.buildersByPlayer.put(builder.getOwner(), Lists.newList());
        }
        this.buildersByPlayer.get(builder.getOwner()).add(builder);
    }

    /**
     * Retrieve all builder for a given player.
     *
     * @param player Player owner of the builders.
     * @return the list of builders for a player.
     */
    //@Ensures("result != null")
    public List<Builder> getBuilderByPlayer(final PlayerId player) {
        assert player != null;
        return Collections.unmodifiableList(this.buildersByPlayer.getOrDefault(player, Collections.emptyList()));
    }

}
