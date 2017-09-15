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
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Grégory Van den Borre
 */
class MissionTest {

    static Mission givenANewMission() {
        List<TaskId> l = Collections.singletonList(TaskId.valueOf(5L));
        return new BaseMission(MissionId.valueOf(1), l, p -> true, RewardId.valueOf(1));
    }

    @Nested
    class Constructor {

        private final MissionId id = MissionId.valueOf(2);

        @Test
        void happyFlow() {
            givenANewMission();
        }

        @Test
        void withNullList() {
            assertThrows(AssertionError.class, () -> new BaseMission(id, null, p -> true, RewardId.valueOf(1)));
        }

        @Test
        void willListContainsNull() {
            List<TaskId> l = new ArrayList<>();
            l.add(TaskId.valueOf(5L));
            l.add(null);
            assertThrows(IllegalArgumentException.class, () -> new BaseMission(id, l, p -> true, RewardId.valueOf(1)));
        }

        @Test
        void withNullPrerequisite() {
            List<TaskId> l = Collections.singletonList(TaskId.valueOf(5L));
            assertThrows(AssertionError.class, () -> new BaseMission(id, l, null, RewardId.valueOf(1)));
        }

        @Test
        void withEmptyTaskList() {
            List<TaskId> l = new ArrayList<>();
            assertThrows(IllegalArgumentException.class, () -> new BaseMission(id, l, p -> true, RewardId.valueOf(1)));
        }

    }

    @Nested
    class CanStart {

        private final MissionId id = MissionId.valueOf(2);

        @Test
        void withTruePrerequisite() {
            List<TaskId> l = new ArrayList<>();
            l.add(TaskId.valueOf(5L));
            Mission m = new BaseMission(id, l, p -> true, RewardId.valueOf(1));
            assertTrue(m.canStartFor(PlayerId.WORLD));
        }

        @Test
        void withFalsePrerequisite() {
            List<TaskId> l = new ArrayList<>();
            l.add(TaskId.valueOf(5L));
            Mission m = new BaseMission(id, l, p -> false, RewardId.valueOf(1));
            assertFalse(m.canStartFor(PlayerId.WORLD));
        }
    }
}
