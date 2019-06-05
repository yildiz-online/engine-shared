/*
 * This file is part of the Yildiz-Engine project, licenced under the MIT License  (MIT)
 *
 * Copyright (c) 2019 Grégory Van den Borre
 *
 * More infos available: https://engine.yildiz-games.be
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this
 * software and associated documentation files (the "Software"), to deal in the Software
 * without restriction, including without limitation the rights to use, copy, modify, merge,
 * publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons
 * to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS  OR COPYRIGHT  HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE  SOFTWARE.
 */

package be.yildizgames.shared.ia.goal;

import java.util.Objects;

/**
 * A goal represent something an entity is trying to achieve, it combines an action with a level of desirability to complete this action.
 *
 * @author Grégory Van den Borre
 */
public final class Goal<T> implements Comparable<Goal<T>> {

    /**
     * Associated action.
     */
    private final T action;

    /**
     * Goal desirability, between 0(no interest) and 1(high priority).
     */
    private Desirability desirability;

    /**
     * Build a new goal.
     *
     * @param action Goal type.
     */
    public Goal(final T action) {
        super();
        Objects.requireNonNull(action);
        this.action = action;
        this.desirability = Desirability.NONE;
    }

    public final T getAction() {
        return this.action;
    }

    public final Desirability getDesirability() {
        return this.desirability;
    }

    public final void setDesirability(Desirability desirability) {
        Objects.requireNonNull(desirability);
        this.desirability = desirability;
    }

    /**
     * Shortcut method to set this goal to no desirability.
     */
    public final void setUndesirable() {
        this.desirability = Desirability.NONE;
    }

    @Override
    public final int hashCode() {
        return this.action.hashCode();
    }

    @Override
    public final boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Goal)) {
            return false;
        }
        Goal other = (Goal) obj;
        return this.action.equals(other.action);
    }

    @Override
    public final int compareTo(final Goal o) {
        return o.desirability.compareTo(this.desirability);
    }

    /**
     * Possible desirability for a given goal.
     * @author Grégory Van den Borre
     */
    public enum Desirability {

        /**
         * This goal would never be done.
         */
        NONE,

        /**
         * This goal would be done if nothing more urgent is planned.
         */
        LOW,

        /**
         * This goal would be done as soon as possible.
         */
        MEDIUM,

        /**
         * This goal will be done right now.
         */
        HIGH
    }
}
