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

import be.yildiz.common.collections.Maps;
import be.yildiz.common.collections.Sets;
import be.yildiz.common.id.PlayerId;
import be.yildiz.shared.mission.task.TaskFactory;
import be.yildiz.shared.player.Player;
import be.yildiz.shared.player.PlayerManager;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The mission manager will handle the mission creation and assignment to ad hoc players.
 * @author Grégory Van den Borre
 */
public class MissionManager {

    private final PlayerManager playerManager;

    /**
     * The list of all possible missions.
     */
    private final Set<Mission> availableMissions = Sets.newSet();

    private final Map<PlayerId, List<Mission>> activeMissions = Maps.newMap();
    private final TaskFactory taskFactory;

    public MissionManager(PlayerManager playerManager, TaskFactory taskFactory) {
        this.playerManager = playerManager;
        this.taskFactory = taskFactory;
    }

    public void addMission(Mission mission) {
        this.availableMissions.add(mission);
        for(Player p : this.playerManager.getPlayerList()) {
            if(mission.canStartFor(p.id)) {
                mission.getTasks().forEach(t -> this.taskFactory.createTask(t, p.id));
            }
        }
    }

    //TODO should contains all event dispatchers(action manager, entity construction manager, ...)and add them listener
}