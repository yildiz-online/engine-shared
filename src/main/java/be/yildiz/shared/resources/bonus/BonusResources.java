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

package be.yildiz.shared.resources.bonus;

import java.util.Arrays;

/**
 * A bonus for resources, it can contains bonus for the resource limits, or the resource ratios. This class is meant to be inherited.
 * Immutable class.
 *
 * @author Grégory Van den Borre
 *
 */
public class BonusResources {

    /**
     * Bonus to the ratio to add to the current ratio.
     */

    private final float[] ratio;

    /**
     * Bonus to the limit to add to the current resource limit.
     */
    private final float[] limit;

    /**
     * The index should be unique for every different kinds of boni, to provide an easy way to compute equality between them. The default value is -1.
     */
    private final int index;

    /**
     * Create a new bonus, containing values for a ratio and a limit resource, equality will be based on the index.
     *
     * @param ratio Bonus ratio values.
     * @param limit Bonus limit values.
     * @param index Unique index.
     * @throws NullPointerException     if ratio is <code>null</code>.
     * @throws NullPointerException     if limit is <code>null</code>.
     * @throws IllegalArgumentException if ratio.length != limit.length
     */
    //@requires index >= 0
    protected BonusResources(final float[] ratio, final float[] limit, final int index) {
        super();
        this.ratio = Arrays.copyOf(ratio, ratio.length);
        this.limit = Arrays.copyOf(limit, limit.length);
        this.index = index;
    }

    /**
     * Create a new bonus, containing values for a ratio and a limit resource, index will be -1 and equality will be based on object itself.
     *
     * @param ratio Ratio to add.
     * @param limit Limit to add.
     * @throws NullPointerException     if ratio is <code>null</code>.
     * @throws NullPointerException     if limit is <code>null</code>.
     * @throws IllegalArgumentException if ratio.length != limit.length
     */
    protected BonusResources(final float[] ratio, final float[] limit) {
        this(ratio, limit, -1);
    }

    /**
     * @return <code>true</code> if at least of the ratio value is not positive.
     */
    //@requires none.
    //@modifies none.
    //@ensures This object state is not affected.
    public final boolean hasMalus() {
        for (float f : this.ratio) {
            if (f < 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * Get a value from this bonus ratio.
     *
     * @param i Index of the ratio value to retrieve.
     * @return The ratio value matching the given index.
     */
    //@requires i < this.ratio.length.
    //@modifies none.
    //@ensures This object state is not affected.
    public final float getRatio(final int i) {
        return this.ratio[i];
    }

    /**
     * Get a value from this bonus limit.
     *
     * @param i Index of the limit value to retrieve.
     * @return The limit value matching the given index.
     */
    //@requires i < this.limit.length.
    //@modifies none.
    //@ensures This object state is not affected.
    public final float getLimit(final int i) {
        return this.limit[i];
    }

    @Override
    public final int hashCode() {
        return this.index;
    }

    /**
     * Equality computing will depends if this object was built with an index or not, if an index was provided, the computation will be based on both bonus index equality. Otherwise, computation will
     * be based on default Object equals.
     *
     * @param obj Object to test equality with this one.
     * @return <code>true</code> if objects are considered equals.
     */
    //@requires none.
    //@modifies none.
    //@ensures to return true if given object is equal to this one.
    @Override
    public final boolean equals(final Object obj) {
        if (!(obj instanceof BonusResources)) {
            return false;
        }
        BonusResources other = (BonusResources) obj;
        if (this.index != -1) {
            return other.index == this.index;
        }
        return this == obj;
    }
}
