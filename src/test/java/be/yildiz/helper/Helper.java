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

/**
 * @author Grégory Van den Borre
 */
public class Helper {



  /*  public static Hull anInvincibleModule(EntityInConstruction eic) {
        return new InvincibleTemplate(eic.getHp()).materialize(new ProtectInvincible(eic.getId(), eic.getModules().getHull(), new EmptyProtectMaterialization(eic.getId())));
    }

    public static BaseEntity anEntity(long id) {
        return anEntity(id, 5);
    }

    public static BaseEntity anEntity(long id, int player) {
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

    public static BaseEntity anEntity(long id, int player, EntityManager<BaseEntity> manager) {
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
        BaseEntity b = new BaseEntity(eic, em);
        manager.addEntity(b);
        return b;
    }*/

    /**
     * @return An entity with player id 5 and id 6.
     */
  /*  public static Entity givenAnEntity() {
        return anEntity(5, 6);
    }

    public static ModuleGroup givenAModuleGroup() {
        return new ModuleGroup.ModuleGroupBuilder().fromList(
                Lists.newList(
                        ActionId.valueOf(1),
                        ActionId.valueOf(2),
                        ActionId.valueOf(3),
                        ActionId.valueOf(4),
                        ActionId.valueOf(5),
                        ActionId.valueOf(6),
                        ActionId.valueOf(7),
                        ActionId.valueOf(8))
        ).build();
    }*/
}
