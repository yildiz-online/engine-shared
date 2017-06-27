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

import java.util.Arrays;
import java.util.List;

/**
 * Response from the server when resources have been transferred, contains the id of the player who lost resources, the id of the player gaining resources and the amount.
 *
 * @author Grégory Van den Borre
 */
public final class ResourceTransferResponse extends NetworkMessage implements ServerResponse {

    /**
     * Player receiving resources.
     */
    private final PlayerId receiver;
    /**
     * Player giving resources.
     */
    private final PlayerId giver;
    /**
     * Resources values.
     */
    private final List<Float> resources;
    /**
     * Cause of this transfer.
     */
    private final TransferCause cause;

    /**
     * Full constructor.
     *
     * @param message Message from the server to parse.
     * @throws InvalidNetworkMessage If an error occurs while parsing the message.
     */
    public ResourceTransferResponse(final MessageWrapper message) throws InvalidNetworkMessage {
        super(message);
        this.receiver = this.getPlayerId();
        this.giver = this.getPlayerId();
        this.resources = this.getFloatList();
        this.cause = TransferCause.values()[this.getInt()];
    }

    /**
     * Full constructor.
     *
     * @param receiver  Id of the player receiving resources.
     * @param giver     Id of the player giving the resources.
     * @param resources Amount of transferred resources.
     * @param cause     Cause of the transfer.
     */
    public ResourceTransferResponse(final PlayerId receiver, final PlayerId giver, final List<Float> resources, final TransferCause cause) {
        super(NetworkMessage.convertParams(receiver, giver, Arrays.asList(resources), cause.ordinal()));
        this.receiver = receiver;
        this.giver = giver;
        this.resources = resources;
        this.cause = cause;
    }

    /**
     * @return The value of ServerCommand RESOURCE_TRANSFER.
     */
    @Override
    public int command() {
        return ServerCommand.RESOURCE_TRANSFER.value;
    }

    public PlayerId getReceiver() {
        return receiver;
    }

    public PlayerId getGiver() {
        return giver;
    }

    public List<Float> getResources() {
        return resources;
    }

    public TransferCause getCause() {
        return cause;
    }

    /**
     * Possible causes for a resource transfer.
     *
     * @author Van den Borre
     */
    public enum TransferCause {

        /**
         * Resources have been stolen from another player.
         */
        THEFT,

        /**
         * Resources have been received from another player.
         */
        GIFT,

        /**
         * Resources have been exchanged with another player.
         */
        COMMERCIAL

    }
}
