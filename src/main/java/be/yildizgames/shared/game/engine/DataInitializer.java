/*
 * This file is part of the Yildiz-Engine project, licenced under the MIT License  (MIT)
 *
 * Copyright (c) 2019 Grégory Van den Borre
 *
 * More infos available: https://engine.yildiz-games.be
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this
 * software and associated documentation files (the "Software"), to deal in the Software
 * without restriction, including without limitation the rights to use, copy, modify, merge,
 * publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons
 * to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS  OR COPYRIGHT  HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE  SOFTWARE.
 */

package be.yildizgames.shared.game.engine;

import be.yildizgames.common.exception.implementation.ImplementationException;

import java.util.ArrayList;
import java.util.List;

/**
 * Keep a list of Initializable class to be initialized when the engine starts.
 *
 * @author Grégory Van den Borre
 */
public final class DataInitializer {

    /**
     * List of all class to initialize.
     */
    private final List<Initializable> initList = new ArrayList<>();

    /**
     * Flag to check if the data have already been initialized.
     */
    private boolean initialized;

    /**
     * Simple constructor.
     */
    public DataInitializer() {
        super();
    }

    /**
     * Run the initialize method of all registered objects.
     */
    public void initialize() {
        if (this.initialized) {
            throw new IllegalStateException("Object has already been initialized.");
        }
        this.initList.forEach(Initializable::initialize);
        this.initialized = true;
    }

    /**
     * Add an Initializable class to be load when engine starts.
     *
     * @param initializable This object will be initialized when the GameEngine run method
     *                      is called.
     */
    public void addInitializable(final Initializable initializable) {
        ImplementationException.throwForNull(initializable);
        if (this.initialized) {
            throw new IllegalStateException("Object has already been initialized.");
        }
        this.initList.add(initializable);
    }

}
