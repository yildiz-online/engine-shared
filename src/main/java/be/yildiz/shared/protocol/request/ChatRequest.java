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

import be.yildiz.module.network.exceptions.InvalidNetworkMessage;
import be.yildiz.module.network.protocol.MessageWrapper;
import be.yildiz.module.network.protocol.NetworkMessage;
import be.yildiz.module.network.protocol.ServerRequest;
import lombok.Getter;

/**
 * A request from the client to the server for a player sending a chat message.
 *
 * @author Grégory Van den Borre
 */
public final class ChatRequest extends NetworkMessage implements ServerRequest {

    /**
     * Message to send.
     */
    @Getter
    private final String message;

    /**
     * Channel receiving the message.
     */
    @Getter
    private final int channel;

    /**
     * Full Constructor. Create the object and prepare it to be sent.
     *
     * @param message       Message to send.
     * @param channelNumber Channel receiving the message.
     */
    public ChatRequest(final String message, final int channelNumber) {
        super(NetworkMessage.convertParams(message, Integer.valueOf(channelNumber)));
        this.message = message;
        this.channel = channelNumber;
    }

    /**
     * Full Constructor. Create the object from a message received.
     *
     * @param message Message to parse to build the object.
     * @throws InvalidNetworkMessage If the message cannot be correctly parsed.
     */
    public ChatRequest(final MessageWrapper message) throws InvalidNetworkMessage {
        super(message);
        this.message = this.getString();
        this.channel = this.getInt();
    }

    /**
     * @return The value of ClientCommand CHAT.
     */
    @Override
    public int command() {
        return ClientCommand.CHAT.ordinal();
    }
}
