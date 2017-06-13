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

import java.util.Arrays;

/**
 * The different states applicable to a mission.
 * @author Grégory Van den Borre
 */
public enum MissionStatus {

    /**
     * If the player has not fulfilled the necessary prerequisites to start the mission.
     */
    UNAVAILABLE(0),

    /**
     * If the player has fulfilled the prerequisite to start but has not accepted yet.
     */
    WAITING_FOR_ACCEPTANCE(1),

    /**
     * If the player has accepted the mission.
     */
    STARTED(2),

    /**
     * If the player has completed the mission with success.
     */
    SUCCESS(3),

    /**
     * If the player has completed the mission with failure.
     */
    FAILED(4),

    /**
     * If the player stops the mission before completing it.
     */
    ABORTED(5),

    /**
     * If the player do not want to start the mission, only possible for optional missions.
     */
    REFUSED(6);

    private final int value;

    MissionStatus(int value) {
        this.value = value;
    }

    public static MissionStatus valueOf(int value) {
        return Arrays.stream(MissionStatus.values())
                .filter(m -> m.value == value)
                .findFirst()
                .orElseThrow(AssertionError::new);
    }

    public int getValue() {
        return value;
    }
}
