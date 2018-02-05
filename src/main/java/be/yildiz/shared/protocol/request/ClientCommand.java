/*
 * This file is part of the Yildiz-Engine project, licenced under the MIT License  (MIT)
 *
 *  Copyright (c) 2018 Grégory Van den Borre
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

package be.yildiz.shared.protocol.request;

/**
 * Enum containing the default commands.
 *
 * @author Grégory Van den Borre
 */
public enum ClientCommand {

    /**
     * Create a new entity.
     */
    BUILD_ENTITY(),

    /**
     * Send a chat message.
     */
    CHAT(),

    /**
     * Create a new building.
     */
    CREATE_BUILDING(),

    /**
     * Request for authentication.
     */
    LOGIN(),

    /**
     * Request to start a new be.yildizgames.engine.feature.research.
     */
    RESEARCH,

    /**
     * Request to allocate staff in a building.
     */
    STAFF,

    /**
     * Request to disconnect the client.
     */
    CLOSE_SESSION,

    SEND_MESSAGE,

    SAVE_MODULE_CONFIG,

    /**
     * Administrator request to create an account.
     */
    ADMIN_CREATE_ACCOUNT,

    /**
     * Administrator request to close the server.
     */
    ADMIN_CLOSE_SERVER,

    /**
     * Administrator request to monitor a player.
     */
    ADMIN_MONITOR_PLAYER,

    /**
     * Cancel a build request.
     */
    BUILD_CANCEL,

    ACTION
}
