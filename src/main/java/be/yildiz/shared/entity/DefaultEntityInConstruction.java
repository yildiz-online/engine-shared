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

package be.yildiz.shared.entity;

import be.yildiz.common.id.EntityId;
import be.yildiz.common.id.PlayerId;
import be.yildiz.common.vector.Point3D;
import be.yildiz.shared.data.EntityType;
import be.yildiz.shared.entity.module.ModuleGroup;
import lombok.Getter;
import lombok.NonNull;

/**
 * An entity in construction contains the state of an entity to be built: its hit points, energy points, position,...
 * Once the real entity is built, it will inherit of this state.
 * A default entity in construction is used to build completely new entities.
 *
 * @author Grégory Van den Borre
 */
@Getter
public class DefaultEntityInConstruction {

    /**
     * Type of the entity to build.
     */
    private final EntityType type;

    /**
     * Id of the entity to build.
     */
    private final EntityId id;

    /**
     * Owner of the entity to build.
     */
    private final PlayerId owner;

    /**
     * Modules composing the entity to build.
     */
    private final ModuleGroup modules;

    /**
     * World position of the entity to build.
     */
    private final Point3D position;

    /**
     * World direction of the entity to build.
     */
    private final Point3D direction;

    /**
     * Create a new instance.
     * @param type      Type of the entity to build.
     * @param id        Id of the entity to build.
     * @param owner     Owner of the entity to build.
     * @param modules   Modules composing the entity to build.
     * @param position  World position of the entity to build.
     * @param direction World direction of the entity to build.
     * @throws NullPointerException If any parameter is null.
     */
    public DefaultEntityInConstruction(@NonNull EntityType type, @NonNull EntityId id, @NonNull PlayerId owner, @NonNull ModuleGroup modules, @NonNull Point3D position, @NonNull Point3D direction) {
        this.type = type;
        this.id = id;
        this.owner = owner;
        this.modules = modules;
        this.position = position;
        this.direction = direction;
    }
}
