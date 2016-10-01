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

package be.yildiz.shared.mission;

import be.yildiz.common.collections.Sets;
import be.yildiz.shared.mission.task.TaskId;

import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * A mission is a goal to achieve by a player to receive a reward.
 * To start a mission, the player must fulfil certain prerequisites.
 * In order to receive the reward the player must accept the mission and complete all the mandatory task.
 * The mission can be optional or mandatory.
 * The mission can be aborted, in this case, all the task already accomplished are reset, and the mission can be accepted again.
 * Once all the mandatory task are completed the mission status is set to completed.
 * The player can also fail the mission, in this case he may or not be able to restart it.
 * @author Grégory Van den Borre
 *
 *
 */
public class Mission {

    private final Set<TaskId> tasks = Sets.newSet();

    public Mission(List<TaskId> tasks) {
        this.tasks.addAll(tasks);
    }

    public Set<TaskId> getTasks() {
        return Collections.unmodifiableSet(this.tasks);
    }
}
