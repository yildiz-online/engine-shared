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

package be.yildiz.shared.protocol;

import be.yildiz.common.id.EntityId;
import be.yildiz.common.id.PlayerId;
import be.yildiz.common.vector.Point3D;
import be.yildiz.shared.data.EntityType;
import be.yildiz.shared.entity.Entity;
import be.yildiz.shared.entity.module.ModuleGroup;

/**
 * @author Grégory Van den Borre
 */
public class EntityDto {

    /**
     * Entity associated Id.
     */
    public final EntityId entity;

    /**
     * Entity name.
     */
    public final String name;

    /**
     * Entity type.
     */
    public final EntityType type;

    /**
     * Id of the Player owning this Entity.
     */
    public final PlayerId owner;

    /**
     * Entity position.
     */
    public final Point3D position;

    /**
     * Entity direction.
     */
    public final Point3D orientation;

    /**
     * Entity hit points.
     */
    public final int hitPoint;

    /**
     * Entity energy points.
     */
    public final int energy;

    public final ModuleGroup modules;

    public final EntityId builderId;

    public final int index;

    public EntityDto(EntityId entity, String name, EntityType type, PlayerId owner, Point3D position, Point3D orientation, int hitPoint, int energy, ModuleGroup modules) {
        this(entity,name, type, owner, position, orientation, hitPoint, energy, modules, EntityId.WORLD, 0);
    }

    public EntityDto(EntityId entity, String name, EntityType type, PlayerId owner, Point3D position, Point3D orientation, int hitPoint, int energy, ModuleGroup modules, EntityId builderId, int index) {
        super();
        this.entity = entity;
        this.name = name;
        this.type = type;
        this.owner = owner;
        this.position = position;
        this.orientation = orientation;
        this.hitPoint = hitPoint;
        this.energy = energy;
        this.modules = modules;
        this.builderId = builderId;
        this.index = index;
    }

    public EntityDto(Entity e) {
        this(e.getId(), e.getName(), e.getType(), e.getOwner(), e.getPosition(), e.getDirection(), e.getHitPoints(), e.getEnergyPoints(), e.getModules());
    }
}
