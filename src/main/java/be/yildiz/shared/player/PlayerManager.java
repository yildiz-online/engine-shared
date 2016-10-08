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

import be.yildiz.common.collections.Lists;
import be.yildiz.common.collections.Maps;
import be.yildiz.common.id.PlayerId;
import lombok.NonNull;

import java.util.List;
import java.util.Map;

/**
 * Create and manage players.
 *
 * @author Grégory Van den Borre
 */
public final class PlayerManager implements PlayerProvider {

    /**
     * List of all created player, the key is their id.
     */
    private final Map<PlayerId, Player> playerIdList = Maps.newMap();
    /**
     * List of all created player, the key is their name.
     */
    private final Map<String, Player> playerNameList = Maps.newMap();

    /**
     * Retrieve a player from its id.
     *
     * @param id Id of the player to retrieve.
     * @return The player matching the id, or world if the player is not found.
     */
    public Player findFromId(final PlayerId id) {
        return this.playerIdList.getOrDefault(id, Player.WORLD);
    }

    /**
     * Retrieve a player from its name.
     *
     * @param name Name of the player to retrieve.
     * @return The player matching the name.
     * @throws IllegalArgumentException If a matching player is not found in the list.
     */
    public Player getFromName(final String name) {
        return this.playerNameList.get(name);
    }

    /**
     * Check if a player exists from its Id.
     *
     * @param id Id to check.
     * @return <code>true</code> if the id is found.
     */
    public boolean exists(final PlayerId id) {
        return this.playerIdList.containsKey(id);
    }

    /**
     * Create a new player.
     *
     * @param id    Player unique Id.
     * @param name  Player unique name.
     * @param right Player rights, <code>null</code> is allowed and the player will have Player right.
     * @return The created player.
     * @throws ExistingPlayerException if the name already exists.
     * @throws ExistingPlayerException if the id already exists.
     */
    public Player createPlayer(@NonNull final PlayerId id, @NonNull final String name, @NonNull final PlayerRight right) {
        if (this.playerNameList.containsKey(name)) {
            throw new ExistingPlayerException("A player already exists with name " + name);
        }
        if (this.playerIdList.containsKey(id)) {
            throw new ExistingPlayerException("A player already exists with id " + id);
        }
        Player player = new Player(id, name, right);
        this.playerIdList.put(id, player);
        this.playerNameList.put(name, player);
        return player;
    }

    /**
     * Create a new player.
     *
     * @param id   Player unique Id.
     * @param name Player unique name.
     * @return The created player.
     * @throws ExistingPlayerException if the name already exists.
     * @throws ExistingPlayerException if the id already exists.
     */
    public Player createPlayer(final PlayerId id, final String name) {
        return this.createPlayer(id, name, PlayerRight.PLAYER);
    }

    /**
     * Retrieve the relation between two players.
     *
     * @param player Player to test.
     * @param other  Other player to test.
     * @return The relation between the two players.
     */
    public PlayerRelation getRelation(final Player player, final Player other) {
        if (player.equals(other)) {
            return PlayerRelation.SAME;
        }
        if (player.isAlly(other)) {
            return PlayerRelation.ALLY;
        }
        return PlayerRelation.ENEMY;
    }

    @Override
    public List<Player> getPlayerList() {
        return Lists.newList(this.playerIdList.values());
    }

    /**
     * @param p Player to check.
     * @return <code>true</code> if the player is world.
     */
    public boolean isWorld(final Player p) {
        return Player.WORLD.equals(p);
    }

    /**
     * @param id Player to check.
     * @return <code>true</code> if the player is world.
     */
    public boolean isWorld(final PlayerId id) {
        return Player.WORLD.id.equals(id);
    }

    /**
     * Check if the relation between player 1 and player 2 is valid, the check is done for the player 1, the relation between player 2 and player 1 is not taken into account.
     *
     * @param p1 Player to check for relation validity.
     * @param p2 Player to be checked against.
     * @param r  Relation to test.
     * @return <code>true</code> if the given relation is valid for player 1 toward player 2.
     */
    public boolean isValid(final PlayerId p1, final PlayerId p2, final PlayerRelation r) {
        return this.getRelation(this.findFromId(p1), this.findFromId(p2)).equals(r);
    }

    /**
     * Check if player 1 is hostile toward player 2.
     *
     * @param p1 Player 1 to check.
     * @param p2 Player to be checked against.
     * @return <code>true</code> if player 1 is hostile to player 2.
     */
    public boolean isHostile(final PlayerId p1, final PlayerId p2) {
        return this.getRelation(this.findFromId(p1), this.findFromId(p2)).equals(PlayerRelation.ENEMY);
    }

    /**
     * Check if a player exists.
     * @param name Name of the player to check.
     * @return <code>true</code> if the player is found.
     */
    public boolean exists(final String name) {
        return this.playerNameList.containsKey(name);
    }

    /**
     * Relation between 2 players.
     *
     * @author Van den Borre Grégory
     */
    public enum PlayerRelation {

        /**
         * The players are the same.
         */
        SAME,

        /**
         * Players are enemies.
         */
        ENEMY,

        /**
         * Players are allied.
         */
        ALLY,

        /**
         * Player are neutral.
         */
        NEUTRAL
    }

    /**
     * Exception to throw if an existing player is created.
     *
     * @author Van den Borre Grégory
     */
    public static final class ExistingPlayerException extends RuntimeException {

        /***/
        private static final long serialVersionUID = 1L;

        /**
         * Create a new exception.
         *
         * @param message Message to display.
         */
        public ExistingPlayerException(final String message) {
            super(message);
        }

    }
}
