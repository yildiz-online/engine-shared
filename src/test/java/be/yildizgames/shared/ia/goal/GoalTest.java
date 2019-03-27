/*
 *
 * This file is part of the Yildiz-Engine project, licenced under the MIT License  (MIT)
 *
 * Copyright (c) 2019 Grégory Van den Borre
 *
 * More infos available: https://engine.yildiz-games.be
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 * documentation files (the "Software"), to deal in the Software without restriction, including without
 * limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 * of the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial
 *  portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 *  WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS
 * OR COPYRIGHT  HOLDERS BE LIABLE FOR ANY CLAIM,
 *  DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE  SOFTWARE.
 *
 *
 */

package be.yildizgames.shared.ia.goal;

import be.yildizgames.common.exception.implementation.ImplementationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

/**
 * @author Grégory Van den Borre
 */
public class GoalTest {

    @Nested
    public class Constructor {

        @Test
        public void happyFlow() {
            Goal<String> goal = new Goal<>("test");
            Assertions.assertEquals("test", goal.getAction());
            Assertions.assertEquals(Goal.Desirability.NONE, goal.getDesirability());
        }

        @Test
        public void withNull() {
            Assertions.assertThrows(ImplementationException.class, () -> new Goal<String>(null));
        }
    }

    @Nested
    public class SetDesirability {

        @Test
        public void happyFlow() {
            Goal<String> goal = new Goal<>("test");
            goal.setDesirability(Goal.Desirability.HIGH);
            Assertions.assertEquals(Goal.Desirability.HIGH, goal.getDesirability());
        }

        @Test
        public void withNull() {
            Assertions.assertThrows(ImplementationException.class, () -> new Goal<>("test").setDesirability(null));
        }
    }

    @Nested
    public class HashCode {

        @Test
        public void action() {
            Goal<String> goal = new Goal<>("test");
            Assertions.assertEquals("test".hashCode(), goal.hashCode());
        }

        @Test
        public void sameAction() {
            Goal<String> goal = new Goal<>("test");
            Assertions.assertEquals(new Goal<>("test").hashCode(), goal.hashCode());
        }

        @Test
        public void differentAction() {
            Goal<String> goal = new Goal<>("test");
            Assertions.assertNotEquals(new Goal<>("test2").hashCode(), goal.hashCode());
        }
    }

    @Nested
    public class Equals {

        @Test
        public void sameObject() {
            Goal<String> goal = new Goal<>("test");
            Assertions.assertEquals(goal, goal);
        }

        @Test
        public void sameAction() {
            Goal<String> goal = new Goal<>("test");
            Assertions.assertEquals(new Goal<>("test"), goal);
        }

        @Test
        public void differentAction() {
            Goal<String> goal = new Goal<>("test");
            Assertions.assertNotEquals(new Goal<>("test2"), goal);
        }

        @Test
        public void differentType() {
            Goal<String> goal = new Goal<>("test");
            Assertions.assertFalse(goal.equals("test"));
        }

        @Test
        public void withNull() {
            Goal<String> goal = new Goal<>("test");
            Assertions.assertNotEquals(null, goal);
        }
    }

    @Nested
    public class SetUndesirable {

        @Test
        public void happyFlow() {
            Goal<String> goal = new Goal<>("test");
            goal.setDesirability(Goal.Desirability.HIGH);
            Assertions.assertEquals(Goal.Desirability.HIGH, goal.getDesirability());
            goal.setUndesirable();
            Assertions.assertEquals(Goal.Desirability.NONE, goal.getDesirability());
        }
    }

    @Nested
    public class CompareTo {

        @Test
        public void sameDesirability() {
            Goal<String> goal = new Goal<>("test");
            goal.setDesirability(Goal.Desirability.HIGH);
            Assertions.assertEquals(0, goal.compareTo(goal));
        }

        @Test
        public void higherDesirability() {
            Goal<String> goal = new Goal<>("test");
            Goal<String> goal2 = new Goal<>("test2");
            goal2.setDesirability(Goal.Desirability.LOW);
            Assertions.assertEquals(1, goal.compareTo(goal2));
        }

        @Test
        public void lowerDesirability() {
            Goal<String> goal = new Goal<>("test");
            goal.setDesirability(Goal.Desirability.LOW);
            Goal<String> goal2 = new Goal<>("test2");
            Assertions.assertEquals(-1, goal.compareTo(goal2));
        }
    }

    @Nested
    public class DesirabilityOrder {

        @Test
        public void happyFlow() {
            Assertions.assertTrue(Goal.Desirability.NONE.ordinal() < Goal.Desirability.LOW.ordinal());
            Assertions.assertTrue(Goal.Desirability.LOW.ordinal() < Goal.Desirability.MEDIUM.ordinal());
            Assertions.assertTrue(Goal.Desirability.MEDIUM.ordinal() < Goal.Desirability.HIGH.ordinal());
        }
    }

}
