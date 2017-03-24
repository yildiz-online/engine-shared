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

import be.yildiz.common.collections.CollectionUtil;
import be.yildiz.common.util.Util;

import java.util.Arrays;

/**
 * Resource value.
 *
 * @author Grégory Van den Borre
 */
public class ResourceValue {

    /**
     * Values of the resources.
     */
    private final float[] values;

    /**
     * Full constructor.
     *
     * @param values Values to set a initialization, a copy will be used.
     */
    public ResourceValue (final float[] values) {
        super();
        assert  values != null;
        this.values = CollectionUtil.arrayCopy(values);
    }

    /**
     * Buying logic, check if resources are enough to pay the parameter price, it is the case, price is removed from the resources amount and the method return <code>true</code>, else, nothing is done
     * and the method returns <code>false</code>.
     *
     * @param price Amount of resources to remove.
     * @return <code>true</code> if the player had enough resources to pay the price and the transaction has been done, <code>false</code> otherwise.
     */
    final boolean buy(final ResourceValue price) {
        if (!CollectionUtil.checkBiggerOrEqual(this.values, price.values)) {
            return false;
        }
        for (int i = 0; i < this.values.length; i++) {
            this.values[i] -= price.values[i];
        }
        return true;
    }

    /**
     * @return A copy of the array containing the values.
     */
    public final float[] getArray() {
        return Arrays.copyOf(this.values, this.values.length);
    }

    /**
     * @param position Value position in resource array.
     * @return The value at the given position.
     */
    public final float getValue(final int position) {
        return this.values[position];
    }

    /**
     * Compute the new value following the given resource rate. New value will be rate multiplied with time in second added to the last value.
     *
     * @param ratio Resource increase rate.
     * @param delta Time since the last call in millisecond.
     * @param limit Maximum values for this resources.
     */
    public final void add(final ResourceRatio ratio, final long delta, final ResourceLimit limit) {
        for (int i = 0; i < this.values.length; i++) {
            this.values[i] = Util.setLimitedValue(this.values[i] + ratio.getValues(i) * delta * 0.001f, limit.getLimits(i));

        }
    }

    /**
     * Add an amount of resources to this one.
     *
     * @param toAdd Resources to add.
     * @param limit Maximum values for this resources.
     */
    public final void add(final ResourceValue toAdd, final ResourceLimit limit) {
        for (int i = 0; i < this.values.length; i++) {
            this.values[i] = Util.setLimitedValue(this.values[i] + toAdd.values[i], limit.getLimits(i));
        }
    }

    /**
     * Check if the resources are enough to buy.
     *
     * @param price Price to buy.
     * @return <code>true</code> if resources are bigger than the price.
     */
    final boolean canBuy(final ResourceValue price) {
        return CollectionUtil.checkBiggerOrEqual(this.values, price.values);
    }

    @Override
    public final String toString() {
        StringBuilder sb = new StringBuilder();
        for (float f : this.values) {
            sb.append(f + ",");
        }
        return sb.toString();
    }

    final void setValues(final ResourceValue other) {
        CollectionUtil.cloneArray(this.values, other.values);
    }
}
