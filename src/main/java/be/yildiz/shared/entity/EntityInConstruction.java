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

package be.yildiz.shared.entity;

import be.yildiz.common.id.ActionId;
import be.yildiz.common.id.EntityId;
import be.yildiz.common.id.PlayerId;
import be.yildiz.common.util.Checker;
import be.yildiz.common.vector.Point3D;
import be.yildiz.shared.data.EntityType;
import be.yildiz.shared.entity.module.ModuleGroup;
import lombok.Getter;
import lombok.NonNull;

/**
 * An entity in construction contains the state of an entity to be built: its hit points, energy points, position,...
 * Once the real entity is built, it will inherit of this state.
 * @author Grégory Van den Borre
 */
@Getter
public class EntityInConstruction extends DefaultEntityInConstruction {

    public static final EntityInConstruction WORLD = new EntityInConstruction(EntityType.WORLD, EntityId.WORLD, PlayerId.WORLD, "World",
            new ModuleGroup.ModuleGroupBuilder()
            .withMove(ActionId.get(0))
            .withInteraction(ActionId.get(1))
            .withDetector(ActionId.get(42))
            .withHull(ActionId.get(9))
            .withEnergy(ActionId.get(13))
            .withNoAdditional()
            .build(), Point3D.ZERO, Point3D.INVERT_Z, 0, 0);

    /**
     * Name of the entity.
     */
    private final String name;

    /**
     * Entity hit points when built.
     */
    private final int hp;

    /**
     * Entity energy points when built.
     */
    private final int energy;

    public EntityInConstruction(EntityType et, EntityId id, PlayerId owner, @NonNull String name, ModuleGroup modules, Point3D position, Point3D direction, int hp, int energy) {
        super(et, id, owner, modules, position, direction);
        Checker.exceptionNotPositive(hp);
        Checker.exceptionNotPositive(energy);
        this.name = name;
        this.hp = hp;
        this.energy = energy;
    }
}
