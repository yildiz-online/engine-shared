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

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author Grégory Van den Borre
 */
class PlayerToCreateTest {

    @Nested
    class Constructor {

        @Test
        void happyFlow() {
            PlayerToCreate ptc = new PlayerToCreate("l", "p", "e");
            assertEquals("l", ptc.getLogin());
            assertEquals("p", ptc.getPassword());
            assertEquals("e", ptc.getEmail());
        }

        @Test
        void withNullLogin() {
            assertThrows(AssertionError.class, () -> new PlayerToCreate(null, "p", "e"));
        }

        @Test
        void withNullPassword() {
            assertThrows(AssertionError.class, () -> new PlayerToCreate("l", null, "e"));
        }

        @Test
        void withNullEmail() {
            assertThrows(AssertionError.class, () -> new PlayerToCreate("l", "p", null));
        }


        @Test
        void withEmptyLogin() {
            assertThrows(AssertionError.class, () -> new PlayerToCreate(null, "p", "e"));
        }

        @Test
        void withEmptyPassword() {
            assertThrows(AssertionError.class, () -> new PlayerToCreate("l", null, "e"));
        }

        @Test
        void withEmptyEmail() {
            assertThrows(AssertionError.class, () -> new PlayerToCreate("l", "p", null));
        }
    }
}