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

package be.yildiz.shared.protocol.request;

import be.yildiz.common.id.PlayerId;
import be.yildiz.module.network.exceptions.InvalidNetworkMessage;
import be.yildiz.module.network.protocol.MessageWrapper;
import be.yildiz.module.network.protocol.NetworkMessage;
import be.yildiz.module.network.protocol.ServerRequest;
import lombok.Getter;

/**
 * Request to monitor a player.
 *
 * @author Grégory Van den Borre
 */
public final class AdminMonitorPlayer extends NetworkMessage implements ServerRequest {

    /**
     * Player to monitor.
     */
    @Getter
    private final PlayerId player;

    /**
     * <code>true</code> to monitor a player, <code>false</code> to stop monitoring it.
     */
    @Getter
    private final boolean monitor;

    /**
     * Full Constructor. Create the object from a message received.
     *
     * @param message Message to parse to build the object.
     * @throws InvalidNetworkMessage If the message cannot be correctly parsed.
     */
    public AdminMonitorPlayer(final MessageWrapper message) throws InvalidNetworkMessage {
        super(message);
        this.player = this.getPlayerId();
        this.monitor = this.getBoolean();
    }

    /**
     * Create a request to be sent.
     *
     * @param player  Player to monitor.
     * @param monitor <code>true</code> to start monitoring, <code>false</code> to stop it.
     */
    public AdminMonitorPlayer(final PlayerId player, final boolean monitor) {
        super(NetworkMessage.convertParams(player, Boolean.valueOf(monitor)));
        this.player = player;
        this.monitor = monitor;
    }

    @Override
    public int command() {
        return ClientCommand.ADMIN_MONITOR_PLAYER.ordinal();
    }
}
