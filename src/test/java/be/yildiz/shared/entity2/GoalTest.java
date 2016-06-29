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

package be.yildiz.shared.entity2;

import be.yildiz.helper.Helper;
import be.yildiz.shared.entity.action.Action;
import be.yildiz.shared.ia.goal.Goal;
import be.yildiz.shared.ia.goal.Goal.Desirability;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * @author Grégory Van den Borre
 */
public final class GoalTest {

    @Rule
    public final ExpectedException rule = ExpectedException.none();

//    @Test
//    public void testConstructorNullParam() {
//        this.rule.expect(NullPointerException.class);
//        new Goal(null);
//    }

    @Test
    public void testHashCode() {
        Action action = new ActionMock(Helper.anEntity(1, 1));
        Goal goal = new Goal(action);
        Assert.assertEquals(action.hashCode(), goal.hashCode());
    }

    @Test
    public void testEquals() {
        Action action = new ActionMock(Helper.anEntity(1, 1));
        Goal goal = new Goal(action);
        Goal goal2 = new Goal(action);
        Assert.assertEquals(goal, goal2);
        goal2 = new Goal(new ActionMock(Helper.anEntity(2, 1)));
        Assert.assertNotEquals(goal, goal2);
    }

    @Test
    public void testCompareTo() {
        Goal a = new Goal(new ActionMock(Helper.anEntity(1, 1)));
        Goal b = new Goal(new ActionMock(Helper.anEntity(1, 1)));
        a.setDesirability(Desirability.NONE);
        b.setDesirability(Desirability.NONE);
        Assert.assertEquals(0, a.compareTo(b));
        b.setDesirability(Desirability.LOW);
        Assert.assertEquals(1, a.compareTo(b));
        a.setDesirability(Desirability.MEDIUM);
        Assert.assertEquals(-1, a.compareTo(b));
        b.setDesirability(Desirability.HIGH);
        Assert.assertEquals(1, a.compareTo(b));
    }

    @Test
    public void testCreate() {
    }

    @Test
    public void testGetSetDesirability() {
        Goal a = new Goal(new ActionMock(Helper.anEntity(1, 1)));
        Assert.assertEquals(Desirability.NONE, a.getDesirability());
        a.setDesirability(Desirability.LOW);
        Assert.assertEquals(Desirability.LOW, a.getDesirability());
        a.setDesirability(Desirability.HIGH);
        Assert.assertEquals(Desirability.HIGH, a.getDesirability());
    }

    @Test
    public void testGetAction() {
    }
}
