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

package be.yildiz.shared.data;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * @author Grégory Van den Borre
 */
@SuppressWarnings({"null", "boxing"})
public final class AttackRangeTest {

    @Rule
    public final ExpectedException rule = ExpectedException.none();

    @Test
    public void testHashCode() {
        AttackRange d1 = new AttackRange(5);
        AttackRange d2 = new AttackRange(new Integer(5));
        Assert.assertEquals(d2.hashCode(), d1.hashCode());
    }

    @Test
    public void testAttackRange() {
        AttackRange d = new AttackRange(10);
        Assert.assertEquals(10, d.distance, 0.000001f);
        d = AttackRange.ZERO;
        Assert.assertEquals(0, d.distance, 0.000001f);
        Integer i = null;
        this.rule.expect(NullPointerException.class);
        d = new AttackRange(i);
    }

    @Test
    public void testAttackRange2() {
        this.rule.expect(IllegalArgumentException.class);
        new AttackRange(-10);

    }

    @Test
    public void testEqualsObject() {
        AttackRange d1 = new AttackRange(5);
        AttackRange d2 = new AttackRange(5);
        AttackRange d3 = new AttackRange(6);
        Assert.assertEquals(d1, d1);
        Assert.assertEquals(d1, d2);
        Assert.assertEquals(d1, new AttackRange(new Integer(5)));
        Assert.assertNotEquals(d1, new Object());
        Assert.assertNotEquals(d1, d3);
    }

}
