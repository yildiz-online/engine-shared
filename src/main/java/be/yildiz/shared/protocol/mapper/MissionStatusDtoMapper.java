/*
 * This file is part of the Yildiz-Engine project, licenced under the MIT License  (MIT)
 *
 *  Copyright (c) 2017 Grégory Van den Borre
 *
 *  More infos available: https://www.yildiz-games.be
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 *  documentation files (the "Software"), to deal in the Software without restriction, including without
 *  limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 *  of the Software, and to permit persons to whom the Software is furnished to do so,
 *  subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be included in all copies or substantial
 *  portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 *  WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS
 *  OR COPYRIGHT  HOLDERS BE LIABLE FOR ANY CLAIM,
 *  DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE  SOFTWARE.
 *
 */

package be.yildiz.shared.protocol.mapper;

import be.yildiz.module.network.exceptions.InvalidNetworkMessage;
import be.yildiz.module.network.protocol.MessageSeparation;
import be.yildiz.module.network.protocol.mapper.CollectionMapper;
import be.yildiz.module.network.protocol.mapper.ObjectMapper;
import be.yildiz.shared.mission.task.TaskStatus;
import be.yildiz.shared.protocol.MissionStatusDto;

/**
 * @author Grégory Van den Borre
 */
public class MissionStatusDtoMapper implements ObjectMapper<MissionStatusDto>{

    private static final MissionStatusDtoMapper INSTANCE = new MissionStatusDtoMapper();

    private MissionStatusDtoMapper() {
        super();
    }

    public static MissionStatusDtoMapper getInstance() {
        return INSTANCE;
    }

    private final CollectionMapper<TaskStatus> taskMapper = new CollectionMapper<>(TaskStatusMapper.getInstance());

    @Override
    public MissionStatusDto from(String s) throws InvalidNetworkMessage {
        assert s != null;
        String[] v = s.split(MessageSeparation.OBJECTS_SEPARATOR);
        try {
            return new MissionStatusDto(
                    MissionIdMapper.getInstance().from(v[0]),
                    MissionStatusMapper.getInstance().from(v[1]),
                    taskMapper.from(v[2])
            );
        } catch (IndexOutOfBoundsException e) {
            throw new InvalidNetworkMessage(e);
        }
    }

    @Override
    public String to(MissionStatusDto dto) {
        assert dto != null;
        return MissionIdMapper.getInstance().to(dto.missionId)
                + MessageSeparation.OBJECTS_SEPARATOR
                + MissionStatusMapper.getInstance().to(dto.status)
                + MessageSeparation.OBJECTS_SEPARATOR
                + taskMapper.to(dto.tasks);
    }
}
