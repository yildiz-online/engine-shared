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

package be.yildiz.shared.player;

import be.yildiz.common.id.PlayerId;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Grégory Van den Borre
 */
final class PlayerTest {

    @Test
    void testPlayer() {
        // FIXME exception when creating 2 identical players?
        Player p = new Player(PlayerId.valueOf(3), "aPlayer");
        assertEquals(p.id, PlayerId.valueOf(3));
        assertEquals("aPlayer", p.name);
        assertThrows(AssertionError.class, () -> new Player(null, "aa"));
        assertThrows(AssertionError.class, () -> new Player(PlayerId.valueOf(5), null));
    }

    /**
     * Test method for {@link be.yildiz.shared.player.Player#id}.
     */
    @Test
    void testGetId() {
        Player p = new Player(PlayerId.valueOf(1257552), "aName");
        assertEquals(PlayerId.valueOf(1257552), p.id);
        assertNotSame(PlayerId.valueOf(487415774), p.id);
    }

    /**
     * Test method for {@link be.yildiz.shared.player.Player#name}.
     */
    @Test
    void testGetName() {
        Player p = new Player(PlayerId.valueOf(1257552), "aName");
        assertEquals("aName", p.name);
    }

    /**
     * Only one instance of a player can be created by player manager, equality is only ==.
     */
    @Test
    void testEqualsObject() {
        Player p2 = new Player(PlayerId.valueOf(4751), "odfhytuijh");
        Player p3 = new Player(PlayerId.valueOf(4751), "odfhytuijh");
        Player p = new Player(PlayerId.valueOf(1257552), "aName");
        assertNotSame(p, p2);
        assertNotSame(p2, p3);
        assertNotSame(p2, null);
        assertNotSame(p2, 5);
    }

    /***/
    @Test
    void testToString() {
        Player p = new Player(PlayerId.valueOf(1257552), "aName");
        assertEquals("aName", p.toString());
    }
}
