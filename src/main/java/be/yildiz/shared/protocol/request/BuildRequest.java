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

package be.yildiz.shared.protocol.request;

import be.yildiz.common.id.EntityId;
import be.yildiz.module.network.exceptions.InvalidNetworkMessage;
import be.yildiz.module.network.protocol.MessageWrapper;
import be.yildiz.module.network.protocol.NetworkMessage;
import be.yildiz.module.network.protocol.ServerRequest;
import be.yildiz.shared.data.EntityType;
import be.yildiz.shared.entity.module.Modules;
import lombok.Getter;

/**
 * A request from the client to the server for an entity building another entity.
 *
 * @author Grégory Van den Borre
 */

public final class BuildRequest extends NetworkMessage implements ServerRequest {

    /**
     * Builder entity id.
     */
    @Getter
    private final EntityId builderId;

    /**
     * Type of the entity to build.
     */
    @Getter
    private final EntityType type;

    /**
     * Ids of the modules associated to the entity to build.
     */
    @Getter
    private final Modules moduleIds;

    /**
     * A request index to match with the message response.
     */
    @Getter
    private final int requestIndex;

    /**
     * Full Constructor. Create the object and prepare it to be sent.
     *
     * @param id           Builder entity id.
     * @param entityType   Type of the entity to build.
     * @param modules      List of modules to add.
     * @param requestIndex Index of the request.
     */
    public BuildRequest(final EntityId id, final EntityType entityType, final Modules moduleIds, final int requestIndex) {
        super(NetworkMessage.convertParams(id, Integer.valueOf(entityType.type), moduleIds.getModules(), requestIndex));
        this.type = entityType;
        this.builderId = id;
        this.moduleIds = moduleIds;
        this.requestIndex = requestIndex;
    }

    /**
     * Full Constructor. Create the object from a message received.
     *
     * @param message Message to parse to build the object.
     * @throws InvalidNetworkMessage If the message cannot be correctly parsed.
     */
    public BuildRequest(final MessageWrapper message) throws InvalidNetworkMessage {
        super(message);
        this.builderId = this.getEntityId();
        this.type = EntityType.get(this.getInt());
        this.moduleIds = new Modules(this.getActionIdList());
        this.requestIndex = this.getInt();
    }

    /**
     * @return The value of ClientCommand BUILD.
     */
    @Override
    public int command() {
        return ClientCommand.BUILD_ENTITY.ordinal();
    }
}
