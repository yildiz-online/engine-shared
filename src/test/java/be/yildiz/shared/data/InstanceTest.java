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

package be.yildiz.shared.data;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * @author Grégory Van den Borre
 */
@SuppressWarnings({"null", "boxing"})
public final class InstanceTest {

    @Rule
    public final ExpectedException rule = ExpectedException.none();

    @Test
    public void testInstance() {
        Instance d = new Instance(10);
        Assert.assertEquals(10, d.number, 0.000001f);
        d = Instance.UNIQUE;
        Assert.assertEquals(1, d.number, 0.000001f);
        d = Instance.NO_LIMIT;
        Assert.assertEquals(Integer.MAX_VALUE, d.number, 0.000001f);
        Integer i = null;
        this.rule.expect(NullPointerException.class);
        d = new Instance(i);
    }

    @Test(expected = AssertionError.class)
    public void testInstance2() {
        new Instance(-10);
    }

    @Test(expected = AssertionError.class)
    public void testInstance3() {
        new Instance(0);
    }

    @Test
    public void testHashCode() {
        Instance d1 = new Instance(5);
        Instance d2 = new Instance(new Integer(5));
        Assert.assertEquals(d2.hashCode(), d1.hashCode());
    }

    @Test
    public void testEquals() {
        Instance d1 = new Instance(5);
        Instance d2 = new Instance(5);
        Instance d3 = new Instance(6);
        Assert.assertEquals(d1, d1);
        Assert.assertEquals(d1, d2);
        Assert.assertEquals(d1, new Instance(new Integer(5)));
        Assert.assertNotEquals(d1, new Object());
        Assert.assertNotEquals(d1, d3);
    }

}
