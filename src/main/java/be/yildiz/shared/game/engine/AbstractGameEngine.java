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

package be.yildiz.shared.game.engine;

import be.yildiz.common.Version;
import be.yildiz.common.collections.Lists;
import be.yildiz.common.framelistener.FrameListener;
import be.yildiz.common.framelistener.FrameManager;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

/**
 * Base class for the server and client GameEngine.
 *
 * @author Grégory Van den Borre
 */
public abstract class AbstractGameEngine implements FrameManager, AutoCloseable {

    /**
     * List all frame listener to execute during the main loop execution.
     */
    private final List<FrameListener> frameListenerList = Lists.newList();

    /**
     * Current version.
     */
    private final Version gameVersion;

    /**
     * Main game loop speed limitation.
     */
    private long limit = 0;

    /**
     * Simple constructor.
     * @param version Game version.
     */
    //@pre this.actionManager != null
    //@pre this.staffAllocator != null
    //@pre this.builderManager != null
    //@pre this.worldList != null
    //@pre this.researchManager != null
    //@pre this.frameListenerList != null
    //@pre this.limit == 0
    //@post this.frameListenerList.contains(this.actionManager)
    //@post this.frameListenerList.contains(this.staffAllocator)
    //@post this.frameListenerList.contains(this.builderManager)
    //@post this.losManager != null
    protected AbstractGameEngine(final Version version) {
        super();
        this.gameVersion = version;
    }

    /**
     * Start the main loop.
     */
    public abstract void start();

    /**
     * Common code to run in game loop.
     */
    public final void runOneFrame() {
        final long now = System.currentTimeMillis();
        for (int i = 0; i < this.frameListenerList.size(); i++) {
            if (!this.frameListenerList.get(i).frameStarted()) {
                this.frameListenerList.remove(i);
                i--;
            }
        }
        this.runOneFrameImpl();
        for (int i = 0; i < this.frameListenerList.size(); i++) {
            if (!this.frameListenerList.get(i).frameEnded()) {
                this.frameListenerList.remove(i);
                i--;
            }
        }
        long frameTime = System.currentTimeMillis() - now;
        if (frameTime < this.limit) {
            LockSupport.parkNanos(TimeUnit.MILLISECONDS.toNanos(this.limit - frameTime));
        }
    }

    /**
     * Client or server implementation specific code to run every time the game loop is iterating.
     */
    protected abstract void runOneFrameImpl();

    /**
     * Add a frame listener to be called every time a frame is started or finished.
     *
     * @param listener FrameListener to add.
     */
    @Override
    public final void addFrameListener(final FrameListener listener) {
        this.frameListenerList.add(listener);
    }

    /**
     * Remove a frame listener.
     *
     * @param listener Listener to remove.
     */
    public final void removeFrameListener(final FrameListener listener) {
        this.frameListenerList.remove(listener);
    }

    /**
     * set a frame speed limiter.
     *
     * @param fps Maximum computation number in one second.
     */
    public final void setFrameLimiter(final int fps) {
        final float time = 1000.0f;
        this.limit = (long) (time / fps);
    }

    public final Version getGameVersion() {
        return gameVersion;
    }
}
