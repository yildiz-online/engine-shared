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

import be.yildiz.common.collections.Lists;
import be.yildiz.common.id.PlayerId;
import be.yildiz.shared.mission.reward.Reward;
import be.yildiz.shared.mission.reward.RewardFactory;
import be.yildiz.shared.mission.reward.RewardId;
import be.yildiz.shared.mission.reward.RewardManager;
import be.yildiz.shared.mission.task.*;
import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import java.util.Set;

/**
 * @author Grégory Van den Borre
 */
@RunWith(Enclosed.class)
public class MissionManagerTest {

    public static class TaskCompleted {

        @Test
        public void noExistingStatus() {
            MissionManager<BaseMission> m = givenAMissionManager();
            m.prepareMission(MissionId.valueOf(1), PlayerId.valueOf(1));
            m.startMission(MissionId.valueOf(1), PlayerId.valueOf(1));
            m.taskCompleted(TaskId.valueOf(1), MissionId.valueOf(1), PlayerId.valueOf(1));
            Set<TaskStatus> status = m.getTaskStatusByMission(PlayerId.valueOf(1), MissionId.valueOf(1));
            Assert.assertEquals(1, status.size());
            Assert.assertEquals("SUCCESS", status.iterator().next().status);
        }

        @Test
        public void replaceExistingStatus() {
            MissionManager<BaseMission> m = givenAMissionManager();
            m.prepareMission(MissionId.valueOf(1), PlayerId.valueOf(1));
            m.startMission(MissionId.valueOf(1), PlayerId.valueOf(1));
            m.updateTaskStatus(TaskId.valueOf(1), MissionId.valueOf(1), PlayerId.valueOf(1), "1/2");
            Set<TaskStatus> status = m.getTaskStatusByMission(PlayerId.valueOf(1), MissionId.valueOf(1));
            Assert.assertEquals(1, status.size());
            Assert.assertEquals("1/2", status.iterator().next().status);
            m.taskCompleted(TaskId.valueOf(1), MissionId.valueOf(1), PlayerId.valueOf(1));
            status = m.getTaskStatusByMission(PlayerId.valueOf(1), MissionId.valueOf(1));
            Assert.assertEquals(1, status.size());
            Assert.assertEquals("SUCCESS", status.iterator().next().status);
        }

    }

    public static MissionManager<BaseMission> givenAMissionManager() {
        MissionManager mm = new MissionManager<>(new TaskFactory() {
            @Override
            public Task createTask(TaskId id, PlayerId p, MissionId missionId) {
                return new Task() {
                    @Override
                    public void addListener(TaskStatusListener taskStatusListener) {

                    }

                    @Override
                    public TaskId getId() {
                        return id;
                    }

                    @Override
                    public boolean isCompleted() {
                        return false;
                    }

                    @Override
                    public boolean isFailed() {
                        return false;
                    }
                };
            }
        },
        new RewardManager(new RewardFactory() {
            @Override
            public void createReward(RewardId id) {

            }

            @Override
            public Reward getReward(RewardId id) {
                return player -> {};
            }
        }));
        mm.registerMission(new BaseMission(MissionId.valueOf(1), Lists.newList(TaskId.valueOf(1)), p -> true, RewardId.valueOf(1)));
        return mm;
    }

}