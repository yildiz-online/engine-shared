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

package be.yildiz.shared.entity.module;

import be.yildiz.common.collections.Lists;
import be.yildiz.common.id.ActionId;

import java.util.Collections;
import java.util.List;

/**
 * @author Grégory Van den Borre
 */
public class ModulesAllowed {

    private final List<ActionId> move = Lists.newList();

    private final List<ActionId> weapon = Lists.newList();

    private final List<ActionId> hull = Lists.newList();

    private final List<ActionId> energy = Lists.newList();

    private final List<ActionId> detector = Lists.newList();

    private final List<ActionId> other = Lists.newList();

    public final ModulesAllowed move(ActionId... ids) {
        if (ids != null) {
            Collections.addAll(this.move, ids);
        }
        return this;
    }

    public final ModulesAllowed weapon(ActionId... ids) {
        if (ids != null) {
            Collections.addAll(this.weapon, ids);
        }
        return this;
    }

    public final ModulesAllowed hull(ActionId... ids) {
        if (ids != null) {
            Collections.addAll(this.hull, ids);
        }
        return this;
    }

    public final ModulesAllowed energy(ActionId... ids) {
        if (ids != null) {
            Collections.addAll(this.energy, ids);
        }
        return this;
    }

    public final ModulesAllowed other(ActionId... ids) {
        if (ids != null) {
            Collections.addAll(this.other, ids);
        }
        return this;
    }

    public boolean isMoveAllowed(ActionId move) {
        return this.move.contains(move);
    }

    public boolean isWeaponAllowed(ActionId weapon) {
        return this.weapon.contains(weapon);
    }

    public boolean isHullAllowed(ActionId hull) {
        return this.hull.contains(hull);
    }

    public boolean isEnergyAllowed(ActionId energy) {
        return this.energy.contains(energy);
    }

    public boolean isOtherAllowed(ActionId modules) {
        return this.other.contains(modules);
    }

    public boolean isDetectorAllowed(ActionId detector) {
        return this.detector.contains(detector);
    }
}
