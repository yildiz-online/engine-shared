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

package be.yildiz.shared.mission.task;

import be.yildiz.common.collections.Lists;
import lombok.Getter;
import lombok.NonNull;

import java.util.List;

/**
 * A mission is composed of one or several task.
 * Those task must be completed to complete the mission.
 * A task can be optional or mandatory.
 * A mission must contains at least one mandatory task.
 * To complete a task, a player must do some action, or have a certain status.
 * @author Grégory Van den Borre
 */
public class Task {

    @Getter
    private final TaskId id;

    private final List<TaskStatusListener> listeners = Lists.newList();

    /**
     * Flag to check if the task is completed or not.
     */
    @Getter
    private boolean completed = false;

    /**
     * Flag to check if the task is failed or not.
     */
    @Getter
    private boolean failed = false;

    protected Task(@NonNull TaskId id) {
        this.id = id;
    }

    public final void setFailed() {
        this.failed = true;
        this.listeners.forEach(t -> t.taskFailed(this.getId()));
    }

    public final void setCompleted() {
        this.completed = true;
        this.listeners.forEach(t -> t.taskCompleted(this.getId()));
    }
}
