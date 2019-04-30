/*
 *
 * This file is part of the Yildiz-Engine project, licenced under the MIT License  (MIT)
 *
 * Copyright (c) 2019 Grégory Van den Borre
 *
 * More infos available: https://engine.yildiz-games.be
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 * documentation files (the "Software"), to deal in the Software without restriction, including without
 * limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 * of the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial
 *  portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 *  WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS
 * OR COPYRIGHT  HOLDERS BE LIABLE FOR ANY CLAIM,
 *  DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE  SOFTWARE.
 *
 *
 */

package be.yildizgames.shared.game.engine;

import be.yildizgames.common.exception.implementation.ImplementationException;
import be.yildizgames.common.exception.initialization.InitializationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class DataInitializerTest {

    @Nested
    public class Constructor {

        @Test
        public void happyFlow() {
            DataInitializer dataInitializer = new DataInitializer();
            Assertions.assertNotNull(dataInitializer);
        }
    }

    @Nested
    public class AddInitializable {

        @Test
        public void withNull() {
            DataInitializer dataInitializer = new DataInitializer();
            Assertions.assertThrows(ImplementationException.class, () -> dataInitializer.addInitializable(null));
        }

        @Test
        public void alreadyInitialized() {
            DataInitializer dataInitializer = new DataInitializer();
            Initializable initializable = new DummyInitializable();
            dataInitializer.initialize();
            Assertions.assertThrows(IllegalStateException.class, () -> dataInitializer.addInitializable(initializable));
        }

    }

    @Nested
    public class Initialize {

    }

    public static class DummyInitializable implements Initializable {

        @Override
        public void initialize() {

        }
    }
}
