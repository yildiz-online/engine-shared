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

package be.yildiz.shared.resources.bonus;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * @author Grégory Van den Borre
 */
public class BonusResourcesTest {

    @Rule
    public ExpectedException rule = ExpectedException.none();

    @Test
    public void testBonusResourcesFloatArrayFloatArrayInt() {
        float[] r = new float[]{1, 2};
        float[] l = new float[]{2, 3};
        BonusResources br = new BonusResources(r, l, 12);
        Assert.assertEquals(1, br.getLimit(0), 0001f);
        Assert.assertEquals(2, br.getLimit(1), 0001f);
        r[0] = 5;
        Assert.assertEquals(1, br.getLimit(0), 0001f);
    }

    @Test
    public void testBonusResourcesFloatArrayFloatArray() {
        float[] r = new float[]{1, 2};
        float[] l = new float[]{2, 3};
        BonusResources br = new BonusResources(r, l);
        Assert.assertEquals(1, br.getLimit(0), 0001f);
        Assert.assertEquals(2, br.getLimit(1), 0001f);
        r[0] = 5;
        Assert.assertEquals(1, br.getLimit(0), 0001f);
    }

    public void testBonusResourcesFloatArrayFloatArrayNotSameSize() {
        float[] r = new float[2];
        float[] l = new float[3];
        this.rule.expect(IllegalArgumentException.class);
        new BonusResources(r, l);
    }

    @Test
    public void testBonusResourcesFloatArrayNullFloatArray() {
        float[] r = null;
        float[] l = new float[2];
        this.rule.expect(NullPointerException.class);
        new BonusResources(r, l);
    }

    @Test
    public void testBonusResourcesFloatArrayFloatArrayNull() {
        float[] r = new float[2];
        float[] l = null;
        this.rule.expect(NullPointerException.class);
        new BonusResources(r, l);
    }

    @Test
    public void testHashCode() {
        float[] r = new float[]{1, 2};
        float[] l = new float[]{2, 3};
        BonusResources br = new BonusResources(r, l);
        Assert.assertEquals(-1, br.hashCode());
        br = new BonusResources(r, l, 17);
        Assert.assertEquals(17, br.hashCode());
    }

    @Test
    public void testEqualsObject() {
        float[] r = new float[]{1, 2};
        float[] l = new float[]{2, 3};
        BonusResources br = new BonusResources(r, l);
        BonusResources br2 = br;
        Assert.assertNotEquals(null, br);
        Assert.assertNotEquals("test", br);
        Assert.assertNotEquals(new BonusResources(r, l), br);
        Assert.assertEquals(br, br2);

        br = new BonusResources(r, l, 7);
        br2 = new BonusResources(new float[]{11, 12, 15}, new float[]{12, 13, 4}, 7);
        Assert.assertEquals(br, br2);
    }

    @Test
    public void testHasMalus() {
        float[] r = new float[]{1, 2};
        float[] l = new float[]{2, 3};
        BonusResources br = new BonusResources(r, l);
        Assert.assertFalse(br.hasMalus());
        r = new float[]{2, -5};
        br = new BonusResources(r, l);
        Assert.assertTrue(br.hasMalus());
        r = new float[]{2, 0};
        br = new BonusResources(r, l);
        Assert.assertFalse(br.hasMalus());
    }

    @Test
    public void testGetRatio() {
        float[] r = new float[]{1, 2};
        float[] l = new float[]{2, 3};
        BonusResources br = new BonusResources(r, l);
        Assert.assertEquals(1, br.getRatio(0), 0.01);
        Assert.assertEquals(2, br.getRatio(1), 0.01);
    }

    @Test
    public void testGetLimit() {
        float[] r = new float[]{1, 2};
        float[] l = new float[]{2, 3};
        BonusResources br = new BonusResources(r, l);
        Assert.assertEquals(2, br.getLimit(0), 0.01);
        Assert.assertEquals(3, br.getLimit(1), 0.01);
    }

}
