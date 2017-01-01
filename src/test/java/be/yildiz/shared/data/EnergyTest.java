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
public class EnergyTest {

    @Rule
    public final ExpectedException rule = ExpectedException.none();

    @SuppressWarnings({"null", "boxing"})
    @Test
    public void testEnergy() {
        Energy d = new Energy(10);
        Assert.assertEquals(10, d.points, 0.000001f);
        d = Energy.ZERO;
        Assert.assertEquals(0, d.points, 0.000001f);
        Integer i = null;
        this.rule.expect(NullPointerException.class);
        d = new Energy(i);
    }

    @Test
    public void testEnergy2() {
        this.rule.expect(IllegalArgumentException.class);
        new Energy(-10);

    }

    @Test
    public void testHashCode() {
        Energy d1 = new Energy(5);
        @SuppressWarnings("boxing")
        Energy d2 = new Energy(new Integer(5));
        Assert.assertEquals(d2.hashCode(), d1.hashCode());
    }

    @SuppressWarnings("boxing")
    @Test
    public void testEquals() {
        Energy d1 = new Energy(5);
        Energy d2 = new Energy(5);
        Energy d3 = new Energy(6);
        Assert.assertEquals(d1, d1);
        Assert.assertEquals(d1, d2);
        Assert.assertEquals(d1, new Energy(new Integer(5)));
        Assert.assertNotEquals(d1, new Object());
        Assert.assertNotEquals(d1, d3);
    }

}
