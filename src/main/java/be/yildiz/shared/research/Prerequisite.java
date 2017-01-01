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

package be.yildiz.shared.research;

import lombok.Getter;

/**
 * Represent a condition to fill to make an action.
 *
 * @author Grégory Van den Borre
 */
@FunctionalInterface
public interface Prerequisite {

    /**
     * Condition to fill implementation.
     *
     * @return The result of the condition.
     */
    ConditionResult fillCondition();

    /**
     * @author Van den Borre Grégory
     * @version 1.0(3 avr. 2012)
     */
    final class ConditionResult {

        /**
         * <code>true</code> if the condition is filled.
         */
        @Getter
        private final boolean filled;

        /**
         * Cause description if not filled.
         */
        @Getter
        private final String cause;

        /**
         * Full constructor.
         *
         * @param isFilled Flag to check if condition is filled.
         * @param cause    Cause if condition is not filled, if filled this parameter
         *                 is ignored.
         */
        public ConditionResult(final boolean isFilled, final String cause) {
            super();
            this.filled = isFilled;
            if (this.filled) {
                this.cause = "";
            } else {
                this.cause = cause;
            }
        }
    }
}
