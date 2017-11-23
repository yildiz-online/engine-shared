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
@SuppressWarnings({"null", "boxing"})
final class AttackRangeTest {

    @Test
    void testHashCode() {
        AttackRange d1 = new AttackRange(5);
        AttackRange d2 = new AttackRange(new Integer(5));
        assertEquals(d2.hashCode(), d1.hashCode());
    }

    @Test
    void testAttackRange() {
        AttackRange d = new AttackRange(10);
        assertEquals(10, d.distance, 0.000001f);
        d = AttackRange.ZERO;
        assertEquals(0, d.distance, 0.000001f);
        assertThrows(NullPointerException.class, () -> new AttackRange((Integer)null));
    }

    @Test
    void testAttackRange2() {
        assertThrows(AssertionError.class, () -> new AttackRange(-10));
    }

    @Test
    void testEqualsObject() {
        AttackRange d1 = new AttackRange(5);
        AttackRange d2 = new AttackRange(5);
        AttackRange d3 = new AttackRange(6);
        assertEquals(d1, d1);
        assertEquals(d1, d2);
        assertEquals(d1, new AttackRange(new Integer(5)));
        assertNotEquals(d1, new Object());
        assertNotEquals(d1, d3);
    }

}
