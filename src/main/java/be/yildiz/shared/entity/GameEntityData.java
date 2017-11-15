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

import be.yildiz.common.Instance;
import be.yildiz.common.Level;
import be.yildiz.common.id.ActionId;
import be.yildiz.common.util.Checker;
import be.yildiz.shared.data.EntityType;
import be.yildiz.shared.entity.module.DefaultModuleProvider;
import be.yildiz.shared.entity.module.ModuleGroup;
import be.yildiz.shared.entity.module.ModulesAllowed;
import be.yildiz.shared.entity.module.WorldModuleProvider;
import be.yildizgames.engine.feature.resource.ResourceValue;

import java.time.Duration;

/**
 * Contains all the data proper to an Entity type.
 * Immutable class.
 *
 * @author Grégory Van den Borre
 */
//@specfield size:int:Entity size, always > 0
//@specfield moduleAllowed:List:List of modules allowed to be used.
//@invariant size > 0
//@invariant moduleAllowed != null
public class GameEntityData implements EntityData {

    /**
     * Constant for world data.
     */
    public static final GameEntityData WORLD = new GameEntityData(
            EntityType.WORLD,
            1000,
            Instance.UNIQUE,
            Level.ZERO,
            new WorldModuleProvider(),
            new ModulesAllowed(),
            new ResourceValue(new float[]{}),
            Duration.ZERO,
            false);

    /**
     * Entity size, must be > 0
     */
    private final int size;

    /**
     * List of modules allowed to be used.
     */
    private final ModulesAllowed modulesAllowed;

    private final DefaultModuleProvider defaultModuleProvider;

    /**
     * Flag to tell if the entity can be built by the player.
     */
    private final boolean buildable;

    private final ResourceValue price;

    private final Duration timeToBuild;

    private final EntityType type;

    private final Instance maxInstance;

    private final Level level;

    /**
     * Full constructor.
     *
     * @param type      Object type.
     * @param size      Object size.
     * @param instances Number of units of that type allowed.
     * @param level     Level required to build this object.
     * @param defaultModuleProvider     Contains the default build with no customization.
     * @param modulesAllowed Configuration allowed.
     * @param price Price to build.
     * @param timeToBuild Time to build.
     * @param buildable Flag to tell if the entity can be built by the player.
     */
    protected GameEntityData(final EntityType type, final int size, final Instance instances,
                             final Level level, final DefaultModuleProvider defaultModuleProvider, final ModulesAllowed modulesAllowed, final ResourceValue price,
                             final Duration timeToBuild, final boolean buildable) {
        super();
        assert defaultModuleProvider != null;
        assert modulesAllowed != null;
        assert price != null;
        assert timeToBuild != null;
        Checker.exceptionNotGreaterThanZero(size);
        this.type = type;
        this.maxInstance = instances;
        this.level = level;
        this.size = size;
        this.modulesAllowed = modulesAllowed;
        this.defaultModuleProvider = defaultModuleProvider;
        this.buildable = buildable;
        this.price = price;
        this.timeToBuild = timeToBuild;
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

    public final ModuleGroup getDefaultModules() {
        return this.defaultModuleProvider.getModules();
    }

    @Override
    public int getSize() {
        return size;
    }

    public ModulesAllowed getModulesAllowed() {
        return modulesAllowed;
    }

    public boolean isBuildable() {
        return buildable;
    }

    public ResourceValue getPrice() {
        return price;
    }

    public Duration getTimeToBuild() {
        return timeToBuild;
    }

    @Override
    public EntityType getType() {
        return type;
    }

    public Instance getInstance() {
        return maxInstance;
    }

    public Level getLevel() {
        return level;
    }
}
