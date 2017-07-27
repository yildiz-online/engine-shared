/*
 * This file is part of the Yildiz-Engine project, licenced under the MIT License  (MIT)
 *
 *  Copyright (c) 2017 Grégory Van den Borre
 *
 *  More infos available: https://www.yildiz-games.be
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 *  documentation files (the "Software"), to deal in the Software without restriction, including without
 *  limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 *  of the Software, and to permit persons to whom the Software is furnished to do so,
 *  subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be included in all copies or substantial
 *  portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 *  WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS
 *  OR COPYRIGHT  HOLDERS BE LIABLE FOR ANY CLAIM,
 *  DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE  SOFTWARE.
 *
 */

package be.yildiz.shared.entity.module;

import be.yildiz.common.collections.Maps;
import be.yildiz.common.id.ActionId;
import be.yildiz.shared.entity.action.Action;

import java.util.Map;

/**
 * Contains all the modules of an entity.
 * @author Grégory Van den Borre
 */
public class EntityModules {

    /**
     * Module managing the hit point manipulation for this entity, cannot be null.
     */
    private final Hull hull;
    /**
     * Module managing the energy generation for this entity, cannot be null.
     */
    private final EnergyGenerator energyGenerator;

    private final Detector detector;
    /**
     * Module managing the base weapon for this entity, cannot be null.
     */
    private final Weapon weapon;
    /**
     * Module managing the base move action for this entity, cannot be null.
     */
    private final MoveEngine moveEngine;

    private final Module additional1;

    private final Module additional2;

    private final Module additional3;

    private Map<ActionId, Action> actions = Maps.newMap();

    /**
     * @param moveEngine Move module.
     * @param weapon Interaction module.
     * @param detector Detection module.
     * @param hull Hull module.
     * @param energyGenerator Energy generation module.
     * @param additional1 Optional module.
     * @param additional2 Optional module.
     * @param additional3 Optional module.
     */
    public EntityModules(
            Hull hull,
            EnergyGenerator energyGenerator,
            Detector detector,
            Weapon weapon,
            MoveEngine moveEngine,
            Module additional1,
            Module additional2,
            Module additional3) {
        super();
        this.hull = hull;
        this.energyGenerator = energyGenerator;
        this.detector = detector;
        this.weapon = weapon;
        this.moveEngine = moveEngine;
        this.additional1 = additional1;
        this.additional2 = additional2;
        this.additional3 = additional3;
        this.actions.put(this.moveEngine.getId(), this.moveEngine.getAction());
        this.actions.put(this.weapon.getId(), this.weapon.getAction());
        this.actions.put(this.hull.getId(), this.hull.getAction());
        this.actions.put(this.energyGenerator.getId(), this.energyGenerator.getAction());
        this.actions.put(this.detector.getId(), this.detector.getAction());
        this.actions.put(this.additional1.getId(), this.additional1.getAction());
        this.actions.put(this.additional2.getId(), this.additional2.getAction());
        this.actions.put(this.additional3.getId(), this.additional3.getAction());
    }

    public Action getAction(ActionId id) {
        if(!actions.containsKey(id)) {
            throw new IllegalArgumentException("actionId is invalid: " + id);
        }
        return this.actions.get(id);
    }

    public Hull getHull() {
        return hull;
    }

    public EnergyGenerator getEnergyGenerator() {
        return energyGenerator;
    }

    public Detector getDetector() {
        return detector;
    }

    public Weapon getWeapon() {
        return weapon;
    }

    public MoveEngine getMoveEngine() {
        return moveEngine;
    }

    public Module getAdditional1() {
        return additional1;
    }

    public Module getAdditional2() {
        return additional2;
    }

    public Module getAdditional3() {
        return additional3;
    }

    public void delete() {
        this.moveEngine.delete();
        this.weapon.delete();
        this.energyGenerator.delete();
        this.detector.delete();
        this.additional1.delete();
        this.additional2.delete();
        this.additional3.delete();
        this.hull.delete();
    }

    public ModuleGroup getGroup() {
        return new ModuleGroup.ModuleGroupBuilder()
                .withHull(this.hull.getId())
                .withEnergy(this.energyGenerator.getId())
                .withDetector(this.detector.getId())
                .withMove(this.moveEngine.getId())
                .withInteraction(this.weapon.getId())
                .withAdditional1(this.additional1.getId())
                .withAdditional2(this.additional2.getId())
                .withAdditional3(this.additional3.getId())
                .build();
    }
}
