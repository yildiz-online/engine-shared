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

package be.yildiz.shared.entity.bonus;

import be.yildiz.common.collections.Sets;
import be.yildiz.common.id.ActionId;
import lombok.Getter;

import java.util.Arrays;
import java.util.Set;

/**
 * A bonus applicable on an entity, this class must be subclassed with every different bonus, the bonus identity (use by the oneInstance principle) is based on the bonus class.
 *
 * @author Grégory Van den Borre
 */
public class EntityBonus {

    /**
     * Value associated to this bonus.
     */
    public final double value;
    /**
     * If <code>true</code>, this flag will return as equals 2 different instances of the child class, no matter its internal value. If <code>false</code>, equality will be based on bonus values. 2
     * equal bonus cannot be added, the last, will replace the oldest.
     */
    private final boolean oneInstance;
    /**
     * Only module with those ids are affected by the bonus.
     */
    @Getter
    private final Set<ActionId> moduleIds = Sets.newSet();

    /**
     * Full constructor.
     *
     * @param value       Value for this bonus.
     * @param oneInstance If <code>true</code>, 2 instances of the same bonus type cannot exist at the same time, the last will replace the old one when added.
     * @param moduleIds   Modules affected by this bonus.
     */
    protected EntityBonus(final double value, final boolean oneInstance, final ActionId... moduleIds) {
        super();
        this.oneInstance = oneInstance;
        this.value = value;
        this.moduleIds.addAll(Arrays.asList(moduleIds));
    }

    /**
     * Compute the hash code, if oneInstance is <code>true</code>, hash code is computed only on the class name, same value for every instances, if <code>false</code>, result is implementation
     * dependent.
     *
     * @return The computed hash code.
     */
    @Override
    public final int hashCode() {
        if (this.oneInstance) {
            return this.getClass().getName().hashCode();
        }
        final int prime = 31;
        int result = 1;
        result = prime * result;
        result += moduleIds.hashCode();
        return result;
    }

    /**
     * Check for equality, if oneInstance is <code>true</code>, 2 objects of the same class will be considered as equals, if <code>false</code>, result is implementation dependent.
     *
     * @param obj Other object to test.
     * @return <code>true</code> if objects are considered equals, <code>false</code> otherwise.
     */
    @Override
    public final boolean equals(final Object obj) {
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        if (this.oneInstance) {
            return obj.getClass().equals(this.getClass());
        }
        EntityBonus other = (EntityBonus) obj;
        return this.value == other.value && this.moduleIds.equals(other.moduleIds);
    }
}
