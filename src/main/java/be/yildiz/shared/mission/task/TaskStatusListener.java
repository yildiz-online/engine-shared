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

package be.yildiz.shared.mission.task;

import be.yildiz.common.id.PlayerId;
import be.yildiz.shared.mission.MissionId;

/**
 * The task listener will notify once a task is completed or has failed.
 * @author Grégory Van den Borre
 */
public interface TaskStatusListener {

    /**
     * Fired when the task is successfully completed.
     * @param taskId Id of the task completed.
     * @param playerId Id of the player completing the task.
     */
    //@Ensures taskId != null
    void taskCompleted(TaskId taskId, MissionId missionId, PlayerId playerId);

    /**
     * Fired when the task has failed to complete.
     * @param taskId Id of the task failed.
     * @param playerId Id of the player failing the task.
     */
    //@Ensures taskId != null
    void taskFailed(TaskId taskId, MissionId missionId, PlayerId playerId);
}