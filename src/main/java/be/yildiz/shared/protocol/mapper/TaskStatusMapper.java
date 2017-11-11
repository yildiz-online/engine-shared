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

package be.yildiz.shared.protocol.mapper;

import be.yildiz.shared.mission.task.TaskStatus;
import be.yildizgames.common.mapping.MappingException;
import be.yildizgames.common.mapping.ObjectMapper;
import be.yildizgames.common.mapping.Separator;

/**
 * @author Grégory Van den Borre
 */
class TaskStatusMapper implements ObjectMapper<TaskStatus> {

    private static final TaskStatusMapper INSTANCE = new TaskStatusMapper();

    private TaskStatusMapper() {
        super();
    }

    public static TaskStatusMapper getInstance() {
        return INSTANCE;
    }

    @Override
    public TaskStatus from(String s) throws MappingException {
        assert s != null;
        String[] values = s.split(Separator.VAR_SEPARATOR);
        String status = values.length == 3 ? values[2] : "";
        try {
        return new TaskStatus(
                TaskIdMapper.getInstance().from(values[0]),
                MissionIdMapper.getInstance().from(values[1]),
                status);
        } catch (NumberFormatException | IndexOutOfBoundsException e) {
            throw new MappingException(e.getMessage());
        }
    }

    @Override
    public String to(TaskStatus taskStatus) {
        assert taskStatus != null;
        return TaskIdMapper.getInstance().to(taskStatus.id)
                + Separator.VAR_SEPARATOR
                + MissionIdMapper.getInstance().to(taskStatus.missionId)
                + Separator.VAR_SEPARATOR
                + taskStatus.status;
    }
}
