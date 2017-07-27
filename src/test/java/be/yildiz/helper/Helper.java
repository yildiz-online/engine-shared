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

package be.yildiz.helper;

import be.yildiz.common.id.EntityId;
import be.yildiz.common.id.PlayerId;
import be.yildiz.common.vector.Point3D;
import be.yildiz.shared.data.EntityType;
import be.yildiz.shared.entity.BaseEntity;
import be.yildiz.shared.entity.Entity;
import be.yildiz.shared.entity.EntityInConstruction;
import be.yildiz.shared.entity.action.ProtectInvincible;
import be.yildiz.shared.entity.action.materialization.EmptyProtectMaterialization;
import be.yildiz.shared.entity.module.EmptyModule;
import be.yildiz.shared.entity.module.EntityModules;
import be.yildiz.shared.entity.module.Hull;
import be.yildiz.shared.entity.module.detector.BlindDetector;
import be.yildiz.shared.entity.module.energy.NoEnergyGenerator;
import be.yildiz.shared.entity.module.hull.InvincibleTemplate;
import be.yildiz.shared.entity.module.interaction.NoWeaponModule;
import be.yildiz.shared.entity.module.move.StaticModule;

/**
 * @author Grégory Van den Borre
 */
public class Helper {

    public static final EntityId ID_OK = EntityId.valueOf(3L);
    public static final PlayerId OWNER_OK = PlayerId.valueOf(2);
    public static final Point3D POSITION_OK = Point3D.valueOf(1, 2, 3);
    public static final Point3D DIRECTION_OK = Point3D.valueOf(5, 8, 3);
    public static final EntityType TYPE_OK = new EntityType(4, "test");

    public static Hull anInvincibleModule(EntityInConstruction eic) {
        return new InvincibleTemplate(eic.getHp()).materialize(new ProtectInvincible(eic.getId(), eic.getModules().getHull(), new EmptyProtectMaterialization(eic.getId())));
    }

    public static Entity anEntity(long id) {
        return anEntity(id, 5);
    }

    public static Entity anEntity(long id, int player) {
        EntityInConstruction eic = new EntityInConstruction(EntityType.WORLD, EntityId.valueOf(id), PlayerId.valueOf(player), "Test", EntityInConstruction.WORLD.getModules(), Point3D.ZERO, Point3D.INVERT_Z, 0, 0);
        EntityModules em = new EntityModules(
                anInvincibleModule(eic),
                new NoEnergyGenerator(eic.getId()),
                new BlindDetector(eic.getId()),
                new NoWeaponModule(eic.getId()),
                new StaticModule(eic.getId()),
                new EmptyModule(eic.getId()),
                new EmptyModule(eic.getId()),
                new EmptyModule(eic.getId())
        );
        return new BaseEntity(eic, em);
    }

    /**
     * @return An entity with player id 5 and id 6.
     */
    public static Entity givenAnEntity() {
        return anEntity(5, 6);
    }
}
