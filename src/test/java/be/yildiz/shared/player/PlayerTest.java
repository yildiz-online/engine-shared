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

import be.yildiz.common.id.PlayerId;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * @author Grégory Van den Borre
 */
public final class PlayerTest {

    public static int value = 5;
    @Rule
    public final ExpectedException rule = ExpectedException.none();

    public void testPlayer() {
        // FIXME exception when creating 2 identical players?
        Player p = new Player(PlayerId.get(3), "aPlayer", false, false);
        Assert.assertEquals(p.id, PlayerId.get(3));
        Assert.assertEquals("aPlayer", p.name);
        rule.expect(NullPointerException.class);
        p = new Player(null, "aa", false, false);
        rule.expect(NullPointerException.class);
        p = new Player(PlayerId.get(5), null, false, false);
    }

    /**
     * Test method for {@link be.yildiz.shared.player.Player#id}.
     */
    @Test
    public void testGetId() {
        Player p = new Player(PlayerId.get(1257552), "aName", false, false);
        Assert.assertEquals(PlayerId.get(1257552), p.id);
        Assert.assertNotSame(PlayerId.get(487415774), p.id);
    }

    /**
     * Test method for {@link be.yildiz.shared.player.Player#name}.
     */
    @Test
    public void testGetName() {
        Player p = new Player(PlayerId.get(1257552), "aName", false, false);
        Assert.assertEquals("aName", p.name);
    }

    /**
     * Only one instance of a player can be created by player manager, equality is only ==.
     */
    @Test
    public void testEqualsObject() {
        Player p2 = new Player(PlayerId.get(4751), "odfhytuijh", false, false);
        Player p3 = new Player(PlayerId.get(4751), "odfhytuijh", false, false);
        Player p = new Player(PlayerId.get(1257552), "aName", false, false);
        Assert.assertNotSame(p, p2);
        Assert.assertNotSame(p2, p3);
        Assert.assertNotSame(p2, null);
        Assert.assertNotSame(p2, new Integer(5));
    }

    /***/
    @Test
    public void testToString() {
        Player p = new Player(PlayerId.get(1257552), "aName", false, false);
        Assert.assertEquals("aName", p.toString());
    }
}
