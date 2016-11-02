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

import be.yildiz.module.network.exceptions.InvalidNetworkMessage;
import be.yildiz.module.network.protocol.MessageWrapper;
import be.yildiz.module.network.protocol.NetworkMessage;
import be.yildiz.module.network.protocol.ServerResponse;
import be.yildiz.shared.mission.MissionId;
import lombok.Getter;

/**
 * @author Grégory Van den Borre
 */
public class MissionStatusResponse extends NetworkMessage implements ServerResponse {

    @Getter
    private final MissionId missionId;

    @Getter
    private final MissionStatus status;

    /**
     * Full constructor, parse the message to build the object.
     *
     * @param message Message received from the server.
     * @throws InvalidNetworkMessage in case of error while parsing the message.
     */
    public MissionStatusResponse(final MessageWrapper message) throws InvalidNetworkMessage {
        super(message);
        this.missionId = new MissionId(this.getInt());
        try {
            this.status = MissionStatus.values()[this.getInt()];
        }catch (IndexOutOfBoundsException e) {
            throw new InvalidNetworkMessage("Invalid mission status", e);
        }
    }

    public MissionStatusResponse(final MissionId id, MissionStatus status) {
        super(NetworkMessage.convertParams(id.value, status.ordinal()));
        this.missionId = id;
        this.status = status;
    }

    @Override
    public int command() {
        return ServerCommand.MISSION_STATUS.value;
    }

    public enum MissionStatus {
        READY, STARTED, COMPLETED, FAILED
    }
}
