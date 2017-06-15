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

import be.yildiz.common.collections.Lists;
import be.yildiz.module.network.exceptions.InvalidNetworkMessage;
import be.yildiz.module.network.protocol.MessageWrapper;
import be.yildiz.module.network.protocol.NetworkMessage;
import be.yildiz.module.network.protocol.ServerResponse;
import be.yildiz.shared.mission.MissionId;
import be.yildiz.shared.mission.MissionStatus;
import be.yildiz.shared.mission.task.TaskStatus;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Grégory Van den Borre
 */
public class MissionStatusResponse extends NetworkMessage implements ServerResponse {

    private final MissionId missionId;

    private final MissionStatus status;

    private final List<TaskStatus> tasks;

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
            this.status = MissionStatus.valueOf(this.getInt());
            this.tasks = Lists.newList();
        }catch (AssertionError e) {
            throw new InvalidNetworkMessage("Invalid mission status", e);
        }
    }

    public MissionStatusResponse(final MissionId id, MissionStatus status, List<TaskStatus> tasks) {
        super(convert(id, status, tasks));
        this.missionId = id;
        this.status = status;
        this.tasks = tasks;
    }

    @Override
    public int command() {
        return ServerCommand.MISSION_STATUS.value;
    }

    public MissionId getMissionId() {
        return missionId;
    }

    public MissionStatus getStatus() {
        return status;
    }

    public List<TaskStatus> getTasks() {
        return this.tasks;
    }

    private static String[] convert(MissionId id, MissionStatus status, List<TaskStatus> tasks) {
        List<String> convertedTasks = tasks.stream()
                .map(t -> String.valueOf(t.id.getValue()) + "#" + t.status)
                .collect(Collectors.toList());
        return NetworkMessage.convertParams(id, status, convertedTasks);
    }

    private static List<TaskStatus> decode(List<String> toParse) {
        List<TaskStatus> result = Lists.newList();
        for(String v : toParse) {
            String[] sep = v.split("@");

        }

    }
}
