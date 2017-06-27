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

import be.yildiz.common.id.EntityId;
import be.yildiz.module.network.exceptions.InvalidNetworkMessage;
import be.yildiz.module.network.protocol.MessageWrapper;
import be.yildiz.module.network.protocol.NetworkMessage;
import be.yildiz.module.network.protocol.ServerResponse;

/**
 * Message sent from the server to the client when an Entity is no longer visible.
 *
 * @author Grégory Van den Borre
 */
public final class RemoveVisibilityResponse extends NetworkMessage implements ServerResponse {

    /**
     * No longer visible Entity Id.
     */
    private final EntityId entity;

    /**
     * Full constructor, parse the message to build the object.
     *
     * @param message Message received from the server.
     * @throws InvalidNetworkMessage in case of error while parsing the message.
     */
    public RemoveVisibilityResponse(final MessageWrapper message) throws InvalidNetworkMessage {
        super(message);
        this.entity = this.from(EntityId.class);
    }

    /**
     * Full constructor.
     *
     * @param entity Id of the no longer visible Entity.
     */
    public RemoveVisibilityResponse(final EntityId entity) {
        super(NetworkMessage.to(entity, EntityId.class));
        this.entity = entity;
    }

    /**
     * @return The ordinal value of ServerCommand UNIT_NO_LONGER_VISIBLE.
     */
    @Override
    public int command() {
        return ServerCommand.UNIT_NO_LONGER_VISIBLE.value;
    }

    public EntityId getEntity() {
        return entity;
    }
}
