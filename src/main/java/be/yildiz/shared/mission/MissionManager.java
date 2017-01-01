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

package be.yildiz.shared.mission;

import be.yildiz.common.collections.CollectionUtil;
import be.yildiz.common.collections.Lists;
import be.yildiz.common.collections.Maps;
import be.yildiz.common.collections.Sets;
import be.yildiz.common.id.PlayerId;
import be.yildiz.shared.mission.task.TaskFactory;
import be.yildiz.shared.mission.task.TaskId;
import be.yildiz.shared.mission.task.TaskStatusListener;
import be.yildiz.shared.player.Player;
import be.yildiz.shared.player.PlayerCreationListener;
import lombok.NonNull;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * The mission manager will handle the mission creation and assignment to ad hoc players.
 * @author Grégory Van den Borre
 */
public class MissionManager <T extends Mission> implements TaskStatusListener, PlayerCreationListener {

    /**
     * The list of all possible missions.
     */
    private final Map<MissionId, T> availableMissions = Maps.newMap();

    private final Map<PlayerId, Set<MissionId>> missionsReady = Maps.newMap();

    private final Map<PlayerId, Set<MissionId>> activeMissions = Maps.newMap();

    private final List<MissionStatusListener<T>> listeners = Lists.newList();

    private final Map<PlayerId, Set<TaskId>> taskCompleted = Maps.newMap();

    /**
     * Factory to build the tasks required by the missions.
     */
    private final TaskFactory taskFactory;

    public MissionManager(final TaskFactory taskFactory) {
        super();
        this.taskFactory = taskFactory;
        this.taskFactory.addTaskListener(this);
    }

    public final void registerMission(final T mission) {
        this.availableMissions.put(mission.getId(), mission);
    }

    public final void prepareMission(final MissionId missionId, final PlayerId playerId) {
        CollectionUtil.getOrCreateSetFromMap(this.missionsReady, playerId).add(missionId);
        T mission = this.availableMissions.get(missionId);
        mission.getTasks().forEach(t -> this.taskFactory.createTask(t, playerId));
        this.listeners.forEach(l->l.missionReady(mission, playerId));
    }

    public final void startMission(final MissionId missionId, final PlayerId playerId) {
        this.missionsReady.get(playerId).remove(missionId);
        CollectionUtil.getOrCreateSetFromMap(this.activeMissions, playerId).add(missionId);
        T mission = this.availableMissions.get(missionId);
        this.listeners.forEach(l->l.missionStarted(mission, playerId));
    }

    @Override
    public final void taskCompleted(final TaskId taskId, final PlayerId playerId) {
        CollectionUtil.getOrCreateSetFromMap(this.taskCompleted, playerId).add(taskId);
        boolean success = this.checkMissionCompleted(taskId, playerId);
        if(success) {
            T mission = this.getMissionFromTaskId(taskId, playerId);
            this.activeMissions.get(playerId).remove(mission.getId());
            this.listeners.forEach(l -> l.missionSuccess(mission, playerId));
        }
    }

    @Override
    public final void taskFailed(TaskId taskId, PlayerId playerId) {
        T mission = this.getMissionFromTaskId(taskId, playerId);
        this.activeMissions.get(playerId).remove(mission.getId());
        this.listeners.forEach(l -> l.missionFailed(mission, playerId));
    }

    public final void addMissionListener(@NonNull MissionStatusListener<T> listener) {
        this.listeners.add(listener);
    }

    @Override
    public final void playerCreated(Player player) {

    }

    public final Set<T> getMissionReady(PlayerId p) {
        return this.missionsReady
                .getOrDefault(p, Sets.newSet())
                .stream()
                .map(this.availableMissions::get)
                .collect(Collectors.toSet());
    }

    private boolean checkMissionCompleted(final TaskId taskId, final PlayerId playerId) {
        Set<TaskId> tasks = CollectionUtil.getOrCreateSetFromMap(this.taskCompleted, playerId);
        T m = this.getMissionFromTaskId(taskId, playerId);
        for(TaskId t : m.getTasks()) {
            if(!tasks.contains(t)) {
                return false;
            }
        }
        return true;
    }

    private T getMissionFromTaskId(final TaskId taskId, final PlayerId playerId) {
        return this.activeMissions.get(playerId)
                .stream()
                .map(this.availableMissions::get)
                .filter(m -> m.hasTask(taskId))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}