//        This file is part of the Yildiz-Online project, licenced under the MIT License
//        (MIT)
//
//        Copyright (c) 2016 Grégory Van den Borre
//
//        More infos available: http://yildiz.bitbucket.org
//
//        Permission is hereby granted, free of charge, to any person obtaining a copy
//        of this software and associated documentation files (the "Software"), to deal
//        in the Software without restriction, including without limitation the rights
//        to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
//        copies of the Software, and to permit persons to whom the Software is
//        furnished to do so, subject to the following conditions:
//
//        The above copyright notice and this permission notice shall be included in all
//        copies or substantial portions of the Software.
//
//        THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
//        IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
//        FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
//        AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
//        LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
//        OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
//        SOFTWARE.

package be.yildiz.shared.resources;

import be.yildiz.common.collections.Sets;
import be.yildiz.shared.resources.bonus.BonusResources;
import com.google.java.contract.Ensures;

import java.util.Arrays;
import java.util.Set;

/**
 * Contains the resources generation rate..
 *
 * @author Grégory Van den Borre
 */
public final class ResourceRatio {

    /**
     * List of rate values.
     */
    private final float[] values;

    /**
     * List of bonus to apply to compute the ratio values.
     */
    private final Set<BonusResources> bonusList = Sets.newSet();

    /**
     * Full constructor.
     *
     * @param ratioValues Values to affect, internally a copy will be used, modifying this object wont affect the newly created ResourceRatio object.
     */
    ResourceRatio(final float[] ratioValues) {
        super();
        this.values = Arrays.copyOf(ratioValues, ratioValues.length);
    }

    /**
     * @return The generation rate for a given resource.
     */
    public float getValues(final int position) {
        return this.values[position];
    }

    /**
     * Add a new bonus to the resources generation.
     *
     * @param bonus Bonus to add.
     */
    void addBonus(final BonusResources bonus) {
        this.bonusList.remove(bonus);
        this.bonusList.add(bonus);
        this.recompute();
    }

    /**
     * Remove a bonus from the resources generation.
     *
     * @param bonus Bonus to remove.
     */
    @Ensures("!this.bonusList.contains(bonus)")
    void removeBonus(final BonusResources bonus) {
        this.bonusList.remove(bonus);
        this.recompute();
    }

    /**
     * Recompute the bonus values.
     */
    private void recompute() {
        Arrays.fill(this.values, 0);
        for (BonusResources bonus : this.bonusList) {
            for (int i = 0; i < this.values.length; i++) {
                this.values[i] += bonus.getRatio(i);
            }
        }
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Arrays.hashCode(values);
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        ResourceRatio other = (ResourceRatio) obj;
        return Arrays.equals(this.values, other.values);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Resources ratio:");
        for (int i = 0; i < this.values.length; i++) {
            sb.append(i + " : " + this.values[i] + ",");
        }
        return sb.toString();
    }

    /**
     * @return <code>true</code> If any value of this ratio is smaller than 0.
     */
    public boolean hasNegative() {
        for (float v : this.values) {
            if (v < 0) {
                return true;
            }
        }
        return false;
    }

}
