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

package be.yildiz.shared.protocol.response;

import be.yildiz.common.id.PlayerId;
import be.yildiz.module.network.exceptions.InvalidNetworkMessage;
import be.yildiz.module.network.protocol.MessageWrapper;
import be.yildiz.module.network.protocol.NetworkMessage;
import be.yildiz.module.network.protocol.ServerResponse;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

/**
 * Response from the server when resources have been transfered, contains the id of the player who lost resources, the id of the player gaining resources and the amount.
 *
 * @author Grégory Van den Borre
 */
public final class ResourceTransfertResponse extends NetworkMessage implements ServerResponse {

    /**
     * Player receiving resources.
     */
    @Getter
    private final PlayerId receiver;
    /**
     * Player giving resources.
     */
    @Getter
    private final PlayerId giver;
    /**
     * Resources values.
     */
    @Getter
    private final List<Float> resources;
    /**
     * Cause of this transfer.
     */
    @Getter
    private final TransfertCause cause;

    /**
     * Full constructor.
     *
     * @param message Message from the server to parse.
     * @throws InvalidNetworkMessage If an error occurs while parsing the message.
     */
    public ResourceTransfertResponse(final MessageWrapper message) throws InvalidNetworkMessage {
        super(message);
        this.receiver = this.getPlayerId();
        this.giver = this.getPlayerId();
        this.resources = this.getFloatList();
        this.cause = TransfertCause.values()[this.getInt()];
    }

    /**
     * Full constructor.
     *
     * @param receiver  Id of the player receiving resources.
     * @param giver     Id of the player giving the resources.
     * @param resources Amount of transfered resources.
     * @param cause     Cause of the transfert.
     */
    public ResourceTransfertResponse(final PlayerId receiver, final PlayerId giver, final List<Float> resources, final TransfertCause cause) {
        super(NetworkMessage.convertParams(receiver, giver, Arrays.asList(resources), Integer.valueOf(cause.ordinal())));
        this.receiver = receiver;
        this.giver = giver;
        this.resources = resources;
        this.cause = cause;
    }

    /**
     * @return The value of ServerCommand RESOURCE_TRANSFERT.
     */
    @Override
    public int command() {
        return ServerCommand.RESOURCE_TRANSFERT.value;
    }


    /**
     * Possible causes for a resource transfer.
     *
     * @author Van den Borre
     */
    public enum TransfertCause {

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
