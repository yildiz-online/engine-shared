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

import be.yildiz.common.collections.Lists;
import be.yildiz.common.id.PlayerId;
import be.yildiz.shared.mission.MissionId;

import java.util.List;

/**
 * A mission is composed of one or several task.
 * Those task must be completed to complete the mission.
 * A task can be optional or mandatory.
 * A mission must contains at least one mandatory task.
 * To complete a task, a player must do some action, or have a certain status.
 * @author Grégory Van den Borre
 */
public class BaseTask implements Task {

    private final TaskId id;

    private final List<TaskStatusListener> listeners = Lists.newList();
    private final PlayerId player;
    private final MissionId missionId;

    private String status = "";

    protected BaseTask(TaskId id, MissionId missionId, PlayerId p) {
        super();
        assert id != null;
        assert p != null;
        assert missionId != null;
        this.id = id;
        this.player = p;
        this.missionId = missionId;
    }

    protected final void setFailed() {
        this.status = "FAILED";
        this.listeners.forEach(t -> t.taskFailed(this.id, this.missionId, this.player));
    }

    protected final void setCompleted() {
        this.status = "SUCCESS";
        this.listeners.forEach(t -> t.taskCompleted(this.id, this.missionId, this.player));
    }

    @Override
    public final void addListener(final TaskStatusListener taskStatusListener) {
        this.listeners.add(taskStatusListener);
    }

    @Override
    public TaskId getId() {
        return id;
    }

    @Override
    public boolean isCompleted() {
        return "SUCCESS".equals(this.status);
    }

    @Override
    public boolean isFailed() {
        return "FAILED".equals(this.status);
    }
}
