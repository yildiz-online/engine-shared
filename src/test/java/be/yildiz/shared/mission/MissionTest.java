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

import be.yildiz.common.id.PlayerId;
import be.yildiz.shared.mission.reward.RewardId;
import be.yildiz.shared.mission.task.TaskId;
import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Grégory Van den Borre
 */
@RunWith(Enclosed.class)
public class MissionTest {

    public static Mission givenANewMission() {
        List<TaskId> l = Collections.singletonList(TaskId.valueOf(5L));
        return new BaseMission(MissionId.valueOf(1), l, p -> true, RewardId.valueOf(1));
    }

    public static class Constructor {

        private final MissionId id = MissionId.valueOf(2);

        @Test
        public void happyFlow() {
            givenANewMission();
        }

        @Test(expected = AssertionError.class)
        public void withNullList() {
            new BaseMission(id, null, p -> true, RewardId.valueOf(1));
        }

        @Test(expected = IllegalArgumentException.class)
        public void willListContainsNull() {
            List<TaskId> l = new ArrayList<>();
            l.add(TaskId.valueOf(5L));
            l.add(null);
            new BaseMission(id, l, p -> true, RewardId.valueOf(1));
        }

        @Test(expected = AssertionError.class)
        public void withNullPrerequisite() {
            List<TaskId> l = Collections.singletonList(TaskId.valueOf(5L));
            new BaseMission(id, l, null, RewardId.valueOf(1));
        }

        @Test(expected = IllegalArgumentException.class)
        public void withEmptyTaskList() {
            List<TaskId> l = new ArrayList<>();
            new BaseMission(id, l, p -> true, RewardId.valueOf(1));
        }

    }

    public static class CanStart {

        private final MissionId id = MissionId.valueOf(2);

        @Test
        public void withTruePrerequisite() {
            List<TaskId> l = new ArrayList<>();
            l.add(TaskId.valueOf(5L));
            Mission m = new BaseMission(id, l, p -> true, RewardId.valueOf(1));
            Assert.assertTrue(m.canStartFor(PlayerId.WORLD));
        }

        @Test
        public void withFalsePrerequisite() {
            List<TaskId> l = new ArrayList<>();
            l.add(TaskId.valueOf(5L));
            Mission m = new BaseMission(id, l, p -> false, RewardId.valueOf(1));
            Assert.assertFalse(m.canStartFor(PlayerId.WORLD));
        }
    }
}
