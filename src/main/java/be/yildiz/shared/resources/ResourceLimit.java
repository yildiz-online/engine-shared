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
import be.yildiz.common.collections.Sets;
import be.yildiz.shared.resources.bonus.BonusResources;

import java.util.Arrays;
import java.util.Set;

/**
 * Set the limit values for a resource object.
 *
 * @author Grégory Van den Borre
 */
public final class ResourceLimit {

    /**
     * Limit values.
     */
    private final float[] limits;

    /**
     * List of bonus to change the limit values.
     */
    private final Set<BonusResources> bonus = Sets.newSet();

    /**
     * Build a new limit from an array values.
     *
     * @param limits Values.
     */
    ResourceLimit(final float[] limits) {
        super();
        this.limits = CollectionUtil.arrayCopy(limits);
    }

    /**
     * Return a limit value.
     *
     * @param position Resource position in array.
     * @return The limit value for the matching resource.
     */
    protected float getLimits(final int position) {
        return this.limits[position];
    }

    /**
     * Add a bonus to the list, if an equal bonus already exist, it will be replaced by this one.
     *
     * @param bonusToAdd Bonus to add.
     */
    public void addBonus(final BonusResources bonusToAdd) {
        this.bonus.remove(bonusToAdd);
        this.bonus.add(bonusToAdd);
        Arrays.fill(this.limits, 0);
        for (BonusResources max : this.bonus) {
            for (int i = 0; i < this.limits.length; i++) {
                this.limits[i] += max.getLimit(i);
            }
        }
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(this.limits);
    }

    @Override
    public boolean equals(final Object obj) {
        if (!(obj instanceof ResourceLimit)) {
            return false;
        }
        return Arrays.equals(this.limits, ((ResourceLimit) obj).limits);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Resources limit:");
        for (int i = 0; i < this.limits.length; i++) {
            sb.append(i + " : " + this.limits[i] + ",");
        }
        return sb.toString();
    }
}
