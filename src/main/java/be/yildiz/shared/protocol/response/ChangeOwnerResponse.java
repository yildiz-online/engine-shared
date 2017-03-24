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
import be.yildiz.common.id.PlayerId;
import be.yildiz.module.network.exceptions.InvalidNetworkMessage;
import be.yildiz.module.network.protocol.MessageWrapper;
import be.yildiz.module.network.protocol.NetworkMessage;
import be.yildiz.module.network.protocol.ServerResponse;

/**
 * Response from the server when an entity changes its owner.
 *
 * @author Grégory Van den Borre
 */
public final class ChangeOwnerResponse extends NetworkMessage implements ServerResponse {

    /**
     * Id of the entity changing its owner.
     */
    private final EntityId entity;

    /**
     * Id of the entity new owner.
     */
    private final PlayerId newOwnerId;

    /**
     * Full constructor.
     *
     * @param message Message from the server to parse.
     * @throws InvalidNetworkMessage If an error occurs while parsing the message.
     */
    public ChangeOwnerResponse(final MessageWrapper message) throws InvalidNetworkMessage {
        super(message);
        this.entity = this.getEntityId();
        this.newOwnerId = this.getPlayerId();
    }

    public ChangeOwnerResponse(final EntityId entity, final PlayerId newOwner) {
        super(NetworkMessage.convertParams(entity, newOwner));
        this.entity = entity;
        this.newOwnerId = newOwner;
    }

    @Override
    public int command() {
        return ServerCommand.CHANGE_OWNER.value;
    }

    public EntityId getEntity() {
        return entity;
    }

    public PlayerId getNewOwnerId() {
        return newOwnerId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ChangeOwnerResponse that = (ChangeOwnerResponse) o;

        if (!entity.equals(that.entity)) {
            return false;
        }
        return newOwnerId.equals(that.newOwnerId);
    }

    @Override
    public int hashCode() {
        int result = entity.hashCode();
        result = 31 * result + newOwnerId.hashCode();
        return result;
    }
}
