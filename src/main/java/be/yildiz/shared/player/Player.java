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

package be.yildiz.shared.player;

import be.yildiz.common.collections.Sets;
import be.yildiz.common.id.EntityId;
import be.yildiz.common.id.PlayerId;

import java.util.Set;

/**
 * Base class for player in the game, class is immutable.
 *
 * @author Grégory Van den Borre
 */
public final class Player {

    /**
     * World constant, to use with neutral units.
     */
    public static final Player WORLD = new Player(PlayerId.WORLD, "world");

    /**
     * Player unique id.
     */
    public final PlayerId id;

    /**
     * Player unique name.
     */
    public final String name;

    /**
     * Player's role.
     */
    public final PlayerRight role;

    /**
     * Build a player.
     * @param id Player unique id.
     * @param name Player unique name.
     * @param role Player role.
     */
    Player(final PlayerId id, final String name, final PlayerRight role) {
        super();
        this.id = id;
        this.name = name;
        this.role = role;
    }

    /**
     * Build a player with the role Player.
     * @param id Player unique id.
     * @param name Player unique name.
     */
    Player(final PlayerId id, final String name) {
        this(id, name, PlayerRight.PLAYER);
    }

    /**
     * List of all allied players.
     */
    private final Set<PlayerId> allies = Sets.newSet();

    private final Set<EntityId> see = Sets.newSet();

    /**
     * Add an ally.
     *
     * @param ally Player to set as ally.
     */
    public void addAlly(final Player ally) {
        this.allies.add(ally.id);
    }

    /**
     * @return The hash code computed from the id.
     */
    @Override
    public int hashCode() {
        return this.id.hashCode();
    }

    /**
     * @param obj Object to test for equality.
     * @return <code>true</code> if 2 players are the same, as a player is unique, only one instance can be kept in memory.
     */
    @Override
    public boolean equals(final Object obj) {
        return this == obj;
    }

    /**
     * @return The player's name.
     */
    @Override
    public String toString() {
        return this.name;
    }

    /**
     * Check if a player is ally with another.
     *
     * @param other Other player to check.
     * @return <code>true</code> if players are allies.
     */
    boolean isAlly(final Player other) {
        return this.allies.contains(other.id);
    }

    public boolean see(EntityId id) {
        return this.see.add(id);
    }

    public boolean noLongerSee(EntityId id) {
        return this.see.remove(id);
    }

    public boolean isSeeing(EntityId id) {
        return this.see.contains(id);
    }

}
