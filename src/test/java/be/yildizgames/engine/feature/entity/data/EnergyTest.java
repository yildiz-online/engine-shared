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

package be.yildizgames.engine.feature.entity.data;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author Grégory Van den Borre
 */
class EnergyTest {


    @SuppressWarnings({"null", "boxing"})
    @Test
    void testEnergy() {
        Energy d = new Energy(10);
        assertEquals(10, d.points, 0.000001f);
        d = Energy.ZERO;
        assertEquals(0, d.points, 0.000001f);
    }

    @Test
    void testEnergy2() {
        assertThrows(AssertionError.class, () -> new Energy(-10));

    }

    @Test
    void testHashCode() {
        Energy d1 = new Energy(5);
        @SuppressWarnings("boxing")
        Energy d2 = new Energy(5);
        assertEquals(d2.hashCode(), d1.hashCode());
    }

    @SuppressWarnings("boxing")
    @Test
    void testEquals() {
        Energy d1 = new Energy(5);
        Energy d2 = new Energy(5);
        Energy d3 = new Energy(6);
        assertEquals(d1, d1);
        assertEquals(d1, d2);
        assertNotEquals(d1, new Object());
        assertNotEquals(d1, d3);
    }

}
