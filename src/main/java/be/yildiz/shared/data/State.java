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

package be.yildiz.shared.data;

import lombok.EqualsAndHashCode;

/**
 * Wrapper class to represent a state. The identity of the state is its name, it must be unique.
 * Immutable class.
 *
 * @author Grégory Van den Borre
 * specfield name:String:State name, must be different for every states.
 * invariant name != null
 */
@EqualsAndHashCode
public final class State {

    /**
     * State name.
     */
    public final String name;

    /**
     * Create a new State.
     *
     * @param name State unique name, cannot be null.
     */
    //@Requires The same name is not used for another state.
    public State(final String name) {
        super();
        this.name = name;
        assert this.invariant();
    }

    /**
     * Check the invariant.
     *
     * @return <code>true</code>.
     * @throws IllegalArgumentException If the invariant is broken.
     */
    private boolean invariant() {
        if (this.name == null) {
            throw new IllegalArgumentException("name is null");
        }
        return true;
    }
}
