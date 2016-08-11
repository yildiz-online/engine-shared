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
import be.yildiz.common.util.Checker;
import be.yildiz.shared.data.*;
import be.yildiz.shared.entity.module.ModulesAllowed;
import be.yildiz.shared.resources.ResourceValue;
import lombok.Getter;

/**
 * Contains all the data proper to an Entity type.
 *
 * @author Grégory Van den Borre
 * @immutable
 * @specfield size:int:Entity size, always > 0.
 * @specfield moduleAllowed:List:List of modules allowed to be used.
 * @invariant size > 0
 * @invariant moduleAllowed != null
 */
public class GameEntityData extends GameData implements ConstructionData, EntityData {

    /**
     * Constant for world data.
     */
    public static final GameEntityData WORLD = new GameEntityData(EntityType.WORLD, 1000, Instance.UNIQUE, Level.ZERO, new ModulesAllowed(), new ResourceValue(new float[]{}), TimeToBuild.ZERO, false);

    /**
     * Entity size, must be > 0
     */
    @Getter
    private final int size;

    /**
     * List of modules allowed to be used.
     */
    @Getter
    private final ModulesAllowed modulesAllowed;

    /**
     * Flag to tell if the entity can be built by the player.
     */
    @Getter
    private final boolean buildable;

    @Getter
    private final ResourceValue price;

    @Getter
    private final TimeToBuild timeToBuild;

    /**
     * Full constructor.
     *
     * @param type          Object type.
     * @param size          Object size.
     * @param instances     Number of units of that type allowed.
     * @param level         Level required to build this object.
     * @param buildable     Flag to tell if the entity can be built by the player.
     */
    protected GameEntityData(final EntityType type, final int size, final Instance instances, final Level level, final ModulesAllowed modulesAllowed, final ResourceValue price, final TimeToBuild timeToBuild, final boolean buildable) {
        super(type, instances, level);
        this.size = size;
        this.modulesAllowed = modulesAllowed;
        this.buildable = buildable;
        this.price = price;
        this.timeToBuild = timeToBuild;
        assert this.invariant() : "Invariant failed";
    }

    public final boolean isAllowed(ActionId module) {
        return true;
    }

    /**
     * Check the invariant.
     *
     * @return <code>true</code>.
     * @throws IllegalArgumentException If the invariant is broken.
     */
    private boolean invariant() {
        if (this.modulesAllowed == null) {
            throw new IllegalArgumentException("modulesAllowed is null");
        }
        Checker.exceptionNotGreaterThanZero(this.size);
        return true;
    }

    public boolean isMoveAllowed(ActionId move) {
        return this.modulesAllowed.isMoveAllowed(move);
    }

    public boolean isWeaponAllowed(ActionId weapon) {
        return this.modulesAllowed.isWeaponAllowed(weapon);
    }

    public boolean isHullAllowed(ActionId hull) {
        return this.modulesAllowed.isHullAllowed(hull);
    }

    public boolean isEnergyAllowed(ActionId energy) {
        return this.modulesAllowed.isEnergyAllowed(energy);
    }

    public boolean isDetectorAllowed(ActionId detector) {
        return this.modulesAllowed.isDetectorAllowed(detector);
    }

    public boolean isOtherAllowed(ActionId module) {
        return this.modulesAllowed.isOtherAllowed(module);
    }

}
