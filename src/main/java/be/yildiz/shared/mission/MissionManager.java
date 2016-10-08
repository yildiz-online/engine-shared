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

import be.yildiz.common.collections.CollectionUtil;
import be.yildiz.common.collections.Lists;
import be.yildiz.common.collections.Maps;
import be.yildiz.common.collections.Sets;
import be.yildiz.common.id.PlayerId;
import be.yildiz.shared.mission.task.TaskFactory;
import be.yildiz.shared.mission.task.TaskId;
import be.yildiz.shared.mission.task.TaskStatusListener;
import be.yildiz.shared.player.PlayerProvider;
import lombok.NonNull;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The mission manager will handle the mission creation and assignment to ad hoc players.
 * @author Grégory Van den Borre
 */
public class MissionManager implements TaskStatusListener {

    /**
     * Provides the list of players.
     */
    private final PlayerProvider playerProvider;

    /**
     * The list of all possible missions.
     */
    private final Set<Mission> availableMissions = Sets.newSet();

    private final Map<PlayerId, Set<Mission>> activeMissions = Maps.newMap();

    private final List<MissionStatusListener> listeners = Lists.newList();

    private final Map<PlayerId, Set<TaskId>> taskCompleted = Maps.newMap();

    /**
     * Factory to build the tasks required by the missions.
     */
    private final TaskFactory taskFactory;

    public MissionManager(PlayerProvider playerProvider, TaskFactory taskFactory) {
        this.playerProvider = playerProvider;
        this.taskFactory = taskFactory;
        this.taskFactory.addTaskListener(this);
    }

    public final void addMission(Mission mission) {
        this.availableMissions.add(mission);
        this.playerProvider.getPlayerList()
                .stream()
                .map(p -> p.id)
                .filter(mission::canStartFor)
                .forEach(id -> {
                    CollectionUtil.getOrCreateSetFromMap(this.activeMissions, id).add(mission);
                    mission.getTasks()
                            .forEach(t -> this.taskFactory.createTask(t, id));
                });
    }

    /**
     * Fired when the task is successfully completed.
     *
     * @param taskId   Id of the task completed.
     * @param playerId Id of the player completing the task.
     */
    @Override
    public final void taskCompleted(TaskId taskId, PlayerId playerId) {
        CollectionUtil.getOrCreateSetFromMap(this.taskCompleted, playerId).add(taskId);
        boolean success = this.checkMissionCompleted(taskId, playerId);
        if(success) {
            Mission mission = this.getMissionFromTaskId(taskId, playerId);
            this.activeMissions.get(playerId).remove(mission);
            this.listeners.forEach(l -> l.missionSuccess(mission, playerId));
        }
    }
    /**
     * Fired when the task has failed to complete.
     *
     * @param taskId   Id of the task failed.
     * @param playerId Id of the player failing the task.
     */
    @Override
    public final void taskFailed(TaskId taskId, PlayerId playerId) {
        Mission mission = this.getMissionFromTaskId(taskId, playerId);
        this.activeMissions.get(playerId).remove(mission);
        this.listeners.forEach(l -> l.missionFailed(mission, playerId));
    }

    public final void addMissionListener(@NonNull MissionStatusListener listener) {
        this.listeners.add(listener);
    }

    private boolean checkMissionCompleted(TaskId taskId, PlayerId playerId) {
        Set<TaskId> tasks = CollectionUtil.getOrCreateSetFromMap(this.taskCompleted, playerId);
        Mission m = this.getMissionFromTaskId(taskId, playerId);
        for(TaskId t : m.getTasks()) {
            if(!tasks.contains(t)) {
                return false;
            }
        }
        return true;
    }

    private Mission getMissionFromTaskId(TaskId taskId, PlayerId playerId) {
        return this.activeMissions.get(playerId)
                .stream()
                .filter(m -> m.hasTask(taskId))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}