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

package be.yildiz.shared.resources;

/**
 * Factory to create resources specific to the game.
 *
 * @author Grégory Van den Borre
 */
public interface GameResources {

    ResourceValue EMPTY_VALUE = fullValue(0, 0, 0, 0, 0);

    static ResourceValue basicValue(final float metal, final float energy, final float credits) {
        return new ResourceValue(new float[]{metal, energy, credits, 0.0f, 0.0f});
    }

    static ResourceValue basicValue(final float metal, final float energy) {
        return new ResourceValue(new float[]{metal, energy, 0.0f, 0.0f, 0.0f});
    }

    /**
     * Create a new resource values with all values.
     *
     * @param metal         Metal value.
     * @param energy        Energy value.
     * @param credits       Credit value.
     * @param researchPoint Research points value.
     * @param inhabitant    Number of inhabitants.
     * @return The value.
     */
    static ResourceValue fullValue(final float metal, final float energy, final float credits, final float researchPoint, final float inhabitant) {
        return new ResourceValue(new float[]{metal, energy, credits, researchPoint, inhabitant});
    }

    /**
     * Create a new resource values, all values are set to 0, except for research points.
     *
     * @param researchPoint Research points value.
     * @return The value.
     */
    static ResourceValue research(final float researchPoint) {
        return new ResourceValue(new float[]{0.0f, 0.0f, 0.0f, researchPoint, 0.0f});
    }

    static ResourceValue empty() {
        return EMPTY_VALUE;
    }

    static ResourceValue metal(final float metal) {
        return new ResourceValue(new float[]{metal, 0.0f, 0.0f, 0.0f, 0.0f});
    }

    static ResourceValue energy(final float energy) {
        return new ResourceValue(new float[]{0.0f, energy, 0.0f, 0.0f, 0.0f});
    }
}
