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

package be.yildiz.shared.resources.bonus;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Grégory Van den Borre
 */
class BonusResourcesTest {

    @Test
    void testBonusResourcesFloatArrayFloatArrayInt() {
        float[] r = new float[]{1, 2};
        float[] l = new float[]{2, 3};
        BonusResources br = new BonusResources(r, l, 12);
        assertEquals(1, br.getLimit(0), 0001f);
        assertEquals(2, br.getLimit(1), 0001f);
        r[0] = 5;
        assertEquals(1, br.getLimit(0), 0001f);
    }

    @Test
    void testBonusResourcesFloatArrayFloatArray() {
        float[] r = new float[]{1, 2};
        float[] l = new float[]{2, 3};
        BonusResources br = new BonusResources(r, l);
        assertEquals(1, br.getLimit(0), 0001f);
        assertEquals(2, br.getLimit(1), 0001f);
        r[0] = 5;
        assertEquals(1, br.getLimit(0), 0001f);
    }

    void testBonusResourcesFloatArrayFloatArrayNotSameSize() {
        float[] r = new float[2];
        float[] l = new float[3];
        assertThrows(IllegalArgumentException.class, () -> new BonusResources(r, l));
    }

    @Test
    void testBonusResourcesFloatArrayNullFloatArray() {
        float[] r = null;
        float[] l = new float[2];
        assertThrows(NullPointerException.class, () -> new BonusResources(r, l));
    }

    @Test
    void testBonusResourcesFloatArrayFloatArrayNull() {
        float[] r = new float[2];
        float[] l = null;
        assertThrows(NullPointerException.class, () -> new BonusResources(r, l));
    }

    @Test
    void testHashCode() {
        float[] r = new float[]{1, 2};
        float[] l = new float[]{2, 3};
        BonusResources br = new BonusResources(r, l);
        assertEquals(-1, br.hashCode());
        br = new BonusResources(r, l, 17);
        assertEquals(17, br.hashCode());
    }

    @Test
    void testEqualsObject() {
        float[] r = new float[]{1, 2};
        float[] l = new float[]{2, 3};
        BonusResources br = new BonusResources(r, l);
        BonusResources br2 = br;
        assertNotEquals(null, br);
        assertNotEquals("test", br);
        assertNotEquals(new BonusResources(r, l), br);
        assertEquals(br, br2);

        br = new BonusResources(r, l, 7);
        br2 = new BonusResources(new float[]{11, 12, 15}, new float[]{12, 13, 4}, 7);
        assertEquals(br, br2);
    }

    @Test
    void testHasMalus() {
        float[] r = new float[]{1, 2};
        float[] l = new float[]{2, 3};
        BonusResources br = new BonusResources(r, l);
        assertFalse(br.hasMalus());
        r = new float[]{2, -5};
        br = new BonusResources(r, l);
        assertTrue(br.hasMalus());
        r = new float[]{2, 0};
        br = new BonusResources(r, l);
        assertFalse(br.hasMalus());
    }

    @Test
    void testGetRatio() {
        float[] r = new float[]{1, 2};
        float[] l = new float[]{2, 3};
        BonusResources br = new BonusResources(r, l);
        assertEquals(1, br.getRatio(0), 0.01);
        assertEquals(2, br.getRatio(1), 0.01);
    }

    @Test
    void testGetLimit() {
        float[] r = new float[]{1, 2};
        float[] l = new float[]{2, 3};
        BonusResources br = new BonusResources(r, l);
        assertEquals(2, br.getLimit(0), 0.01);
        assertEquals(3, br.getLimit(1), 0.01);
    }

}
