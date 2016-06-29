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

package be.yildiz.shared.player;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.security.InvalidParameterException;

/**
 * @author Grégory Van den Borre
 */
public final class PlayerStatusTest {

    @Rule
    public final ExpectedException rule = ExpectedException.none();

    /**
     * Test method for
     * {@link be.yildiz.shared.player.PlayerStatus#getFromOrdinal(int)}.
     */
    @Test
    public void testGetFromOrdinal1() {
        this.rule.expect(InvalidParameterException.class);
        PlayerStatus.getFromOrdinal(-1);
    }

    /**
     * Test method for
     * {@link be.yildiz.shared.player.PlayerStatus#getFromOrdinal(int)}.
     */
    @Test
    public void testGetFromOrdinal2() {
        this.rule.expect(InvalidParameterException.class);
        PlayerStatus.getFromOrdinal(PlayerStatus.values().length + 2);
    }

    /**
     * Test method for
     * {@link be.yildiz.shared.player.PlayerStatus#getFromOrdinal(int)}.
     */
    @Test
    public void testGetFromOrdinal3() {
        for (int i = 0; i < PlayerStatus.values().length; i++) {
            Assert.assertEquals(PlayerStatus.values()[i], PlayerStatus.getFromOrdinal(i));
        }
        Assert.assertNotSame(PlayerStatus.values()[0], PlayerStatus.getFromOrdinal(1));
    }
}
