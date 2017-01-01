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

package be.yildiz.shared.protocol.response;

/**
 * List of commands that can be sent from the server to the client.
 *
 * @author Grégory Van den Borre
 */
public enum ServerCommand {

    /**
     * Send information about the current map.
     */
    MAP_INFO(1),

    /**
     * Send information about a player.
     */
    PLAYER_INFO(2),

    /**
     * Send information to build an Entity.
     */
    BUILD(3),

    /**
     * Send information for an Entity to change its Player owner.
     */
    CHANGE_OWNER(4),

    /**
     * Send information for an Entity to be destroyed.
     */
    DESTROY(5),

    /**
     * Send the current version accepted by the server.
     */
    VERSION(6),

    /**
     * Send a chat message.
     */
    CHAT(7),

    /**
     * Send information for an Entity to be hit.
     */
    HIT(8),

    /**
     * Send information for an Entity to update its position.
     */
    POSITION(9),

    /**
     * Send information for an Entity to be no longer visible by the client.
     */
    UNIT_NO_LONGER_VISIBLE(10),

    /**
     * Send information about the current amount of resource.
     */
    RESOURCE(11),

    /**
     * Send information about resources transfered from a player to another.
     */
    RESOURCE_TRANSFER(12),

    /**
     * Send information for an Entity to stop attacking..
     */
    STOP_ATTACK(13),

    /**
     * Send information for a building to be build.
     */
    BUILDING_INFO(14),

    /**
     * Command sent when trying to authenticate.
     */
    LOGIN(15),

    /**
     * Command listing all researches for the player.
     */
    RESEARCH_INFO(16),

    INITIALISED(17),

    CLOSE_SESSION(18),

    MESSAGE_LIST(19),

    ACTION(20),

    ENTITY_CONSTRUCTION_QUEUE(21),

    MISSION_STATUS(22),

    AUTHENTICATION_RESPONSE(99);

    public final int value;

    ServerCommand(final int value) {
        this.value = value;
    }

    public static ServerCommand byValue(int value) {
        for (ServerCommand c : ServerCommand.values()) {
            if (value == c.value) {
                return c;
            }
        }
        return null;
    }
}
