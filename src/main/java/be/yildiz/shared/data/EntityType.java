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

package be.yildiz.shared.data;

import be.yildiz.common.collections.Maps;
import lombok.EqualsAndHashCode;

import java.util.Map;

/**
 * Simple wrapper class for an entity type. An Entity type is composed of a type
 * and a name, both must be unique in the application instance.
 *
 * @author Grégory Van den Borre
 * @immutable
 * @specfield type : int : positive not null value, 2 different types cannot
 * have the same type value.
 * @specfield name : String : not null value, 2 different types cannot have the
 * same name.
 */
@EqualsAndHashCode
public final class EntityType {

    /**
     * Map the type to its index value.
     */
    private static final Map<Integer, EntityType> MAP = Maps.newMap();

    /**
     * Constant for the world type.
     */
    public static final EntityType WORLD = new EntityType(0, "world");

    /**
     * Index value.
     */
    public final int type;

    /**
     * Entity type name.
     */
    public final String name;

    /**
     * Full constructor.
     *
     * @param value Entity type index, must be unique.
     * @param name  Type name, must be unique.
     * @requires value >=0 && value != null &&
     * !EntityType.MAP.containsKey(value);
     * @requires name != null && !name.empty &&
     * !EntityType.MAP.containsValue(name);
     * @ensures EntityType.MAP.get(value) == this;
     * @ensures this.type = value;
     * @ensures this.name = name;
     */
    public EntityType(final int value, final String name) {
        super();
        assert !EntityType.MAP.containsKey(value) : "Type already exists";
        assert !EntityType.MAP.containsValue(name) : "Name already exists";

        this.name = name;
        this.type = value;
        EntityType.MAP.putIfAbsent(Integer.valueOf(value), this);
        assert this.invariant();
    }

    /**
     * Retrieve a type from its index.
     *
     * @param index Entity index value.
     * @return The Type matching the index value.
     * @throws NullPointerException if there is no value matching the index.
     */
    public static EntityType get(final int index) {
        assert EntityType.MAP.containsKey(Integer.valueOf(index));
        return EntityType.MAP.get(Integer.valueOf(index));
    }

    @Override
    public String toString() {
        return this.name;
    }

    private boolean invariant() {
        assert this.type >= 0 : "Type must be positive";
        assert (Integer) this.type != null : "Type must not be null";
        assert this.name != null : "Name must not be null";
        assert EntityType.MAP.get(this.type) == this : "This object is not registered";
        return true;
    }
}
