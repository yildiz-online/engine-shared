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

import be.yildiz.common.id.PlayerId;
import be.yildiz.module.network.exceptions.InvalidNetworkMessage;
import be.yildiz.module.network.protocol.MessageWrapper;
import be.yildiz.module.network.protocol.NetworkMessage;
import be.yildiz.module.network.protocol.ServerResponse;
import lombok.EqualsAndHashCode;

/**
 * Response from the server when an chat message is received, contains the user id and the message.
 *
 * @author Grégory Van den Borre
 */
@EqualsAndHashCode(callSuper = false)
public final class ChatResponse extends NetworkMessage implements ServerResponse {

    /**
     * Player sending the message id.
     */
    private final PlayerId player;

    /**
     * Message sent by the player.
     */
    private final String chatMessage;

    /**
     * Full constructor.
     *
     * @param message Message from the server to parse.
     * @throws InvalidNetworkMessage If an error occurs while parsing the message.
     */
    public ChatResponse(final MessageWrapper message) throws InvalidNetworkMessage {
        super(message);
        this.player = this.getPlayerId();
        this.chatMessage = this.getString();
    }

    /**
     * Full constructor.
     *
     * @param player  Id of the Player sending the message.
     * @param message Message to send.
     */
    public ChatResponse(final PlayerId player, final String message) {
        super(NetworkMessage.convertParams(player, message));
        this.player = player;
        this.chatMessage = message;
    }

    /**
     * @return Value of ServerCommand CHAT.
     */
    @Override
    public int command() {
        return ServerCommand.CHAT.value;
    }

    public PlayerId getPlayer() {
        return player;
    }

    public String getChatMessage() {
        return chatMessage;
    }
}
