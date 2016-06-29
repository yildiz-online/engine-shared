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

import be.yildiz.common.id.EntityId;
import be.yildiz.common.vector.Point3D;
import be.yildiz.module.network.exceptions.InvalidNetworkMessage;
import be.yildiz.module.network.protocol.MessageWrapper;
import be.yildiz.module.network.protocol.NetworkMessage;
import be.yildiz.module.network.protocol.ServerResponse;
import lombok.Getter;

/**
 * Response from the server to update an Entity position.
 *
 * @author Grégory Van den Borre
 */
@Getter
public final class UpdatePositionResponse extends NetworkMessage implements ServerResponse {

    /**
     * Id of the unit with the position updated.
     */
    private final EntityId entity;

    /**
     * Unit position.
     */
    private final Point3D position;

    /**
     * Unit direction.
     */
    private final Point3D orientation;

    /**
     * Full constructor.
     *
     * @param message Message from the server to parse.
     * @throws InvalidNetworkMessage If an error occurs while parsing the message.
     */
    public UpdatePositionResponse(final MessageWrapper message) throws InvalidNetworkMessage {
        super(message);
        this.entity = this.getEntityId();
        this.position = this.getPoint3D();
        this.orientation = this.getPoint3D();
    }

    /**
     * Full constructor.
     *
     * @param unitId           Id of the unit with the position updated.
     * @param updatedPosition  Updated unit position.
     * @param updatedDirection Updated unit direction.
     */
    public UpdatePositionResponse(final EntityId unitId, final Point3D updatedPosition, final Point3D updatedDirection) {
        super(NetworkMessage.convertParams(unitId, updatedPosition, updatedDirection));
        this.entity = unitId;
        this.position = updatedPosition;
        this.orientation = updatedDirection;
    }

    /**
     * @return Value of ServerCommand POSITION.
     */
    @Override
    public int command() {
        return ServerCommand.POSITION.value;
    }
}
