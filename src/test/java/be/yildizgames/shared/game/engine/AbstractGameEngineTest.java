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
import be.yildizgames.common.frame.FrameListener;
import be.yildizgames.common.model.Version;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

/**
 * @author Grégory Van den Borre
 */
public class AbstractGameEngineTest {

    @Nested
    public class Constructor {

        @Test
        public void happyFlow() {
            AbstractGameEngine engine = new DummyGameEngine();
            Assertions.assertEquals(Version.release(1,0,0,0), engine.getGameVersion());
        }

        @Test
        public void withNull() {
            Assertions.assertThrows(ImplementationException.class, () -> new DummyGameEngine(null));
        }
    }

    @Nested
    public class AddFrameListener {

        @Test
        public void withNull() {
            Assertions.assertThrows(ImplementationException.class, () -> new DummyGameEngine().addFrameListener(null));
        }
    }

    @Nested
    public class RunOneFrame {

        @Test
        public void happyFlow() {
            DummyGameEngine engine = new DummyGameEngine();
            Assertions.assertFalse(engine.frameRun);
            engine.runOneFrame();
            Assertions.assertTrue(engine.frameRun);
        }

        @Test
        public void checkListener() {
            DummyGameEngine engine = new DummyGameEngine();
            DummyFrameListener dfl = new DummyFrameListener();
            engine.addFrameListener(dfl);
            Assertions.assertEquals(0, dfl.ran);
            engine.runOneFrame();
            Assertions.assertEquals(1, dfl.ran);
            engine.runOneFrame();
            Assertions.assertEquals(2, dfl.ran);
        }

        @Test
        public void checkRemovedListener() {
            DummyGameEngine engine = new DummyGameEngine();
            DummyFrameListener dfl = new DummyFrameListener();
            engine.addFrameListener(dfl);
            Assertions.assertEquals(0, dfl.ran);
            engine.runOneFrame();
            Assertions.assertEquals(1, dfl.ran);
            engine.removeFrameListener(dfl);
            Assertions.assertEquals(1, dfl.ran);
        }

    }

    public static class DummyGameEngine extends AbstractGameEngine {

        public boolean frameRun;

        /**
         * Simple constructor.
         *
         * @param version Game version.
         */
        public DummyGameEngine(Version version) {
            super(version);
        }

        public DummyGameEngine() {
            super(Version.release(1,0,0,0));
        }

        @Override
        public void start() {
        }

        @Override
        protected void runOneFrameImpl() {
            frameRun = true;
        }
    }

    public static class DummyFrameListener implements FrameListener {

        private int ran = 0;

        private final boolean keepRunning;

        public DummyFrameListener(boolean keepRunning) {
            this.keepRunning = keepRunning;
        }

        public DummyFrameListener() {
            this.keepRunning = true;
        }

        @Override
        public boolean frameStarted() {
            this.ran++;
            return this.keepRunning;
        }

        @Override
        public boolean frameEnded(long l) {
            return this.keepRunning;
        }

        @Override
        public boolean frameEnded() {
            return this.keepRunning;
        }
    }

}
