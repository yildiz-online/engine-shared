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

package be.yildiz.shared.ia.goal;

import be.yildiz.shared.entity.action.AbstractAttack;
import be.yildiz.shared.entity.action.Action;
import be.yildiz.shared.entity.action.Move;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

/**
 * A goal represent something an entity is trying to achieve, it combines an action with a level of desirability to complete this action.
 *
 * @author Grégory Van den Borre
 */
@Getter
public final class Goal implements Comparable<Goal> {

    /**
     * Associated action.
     */
    private final Action action;
    @Getter
    private final boolean move;
    @Getter
    private final boolean attack;
    /**
     * Goal desirability, between 0(no interest) and 1(high priority).
     */
    @Setter
    private Desirability desirability;

    /**
     * Build a new goal.
     *
     * @param action Goal type.
     */
    public Goal(@NonNull final Action action) {
        super();
        this.action = action;
        this.desirability = Desirability.NONE;
        this.move = false;
        this.attack = false;
    }

    public Goal(@NonNull final Move action) {
        super();
        this.action = action;
        this.desirability = Desirability.NONE;
        this.move = true;
        this.attack = false;
    }

    public Goal(@NonNull final AbstractAttack action) {
        super();
        this.action = action;
        this.desirability = Desirability.NONE;
        this.move = false;
        this.attack = true;
    }

    /**
     * Shortcut method to set this goal to no desirability.
     */
    public void setUndesirable() {
        this.desirability = Desirability.NONE;
    }

    @Override
    public final int hashCode() {
        return this.action.hashCode();
    }

    @Override
    public boolean equals(final Object obj) {
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
    public int compareTo(final Goal o) {
        // inverted to have reversed order, higher must be first
        return o.desirability.compareTo(this.desirability);
    }

    public enum Desirability {

        NONE, LOW, MEDIUM, HIGH
    }
}
