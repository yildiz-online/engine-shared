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
import be.yildiz.common.log.Logger;
import be.yildiz.shared.entity.module.ModuleGroup;
import lombok.NoArgsConstructor;
import lombok.NonNull;

/**
 * To check the validity of a list of modules against a given data type, it does not check if the user has the right to use those modules.
 *
 * @author Grégory Van den Borre
 */
@NoArgsConstructor
public final class ModuleChecker {

    /**
     * Check a list of ids against a game entity data.
     *
     * @param data    Data to check for allowed ids.
     * @param modules List of modules to check.
     * @return <code>true</code> only if the size of the list is at least 3 elements, and if the data allows the id at the given position in the list to be used.
     */
    public boolean checkIds(@NonNull final GameEntityData data, final ModuleGroup modules) {
        ActionId move = modules.getMove();
        ActionId interaction = modules.getInteraction();
        ActionId hull = modules.getHull();
        ActionId energy = modules.getEnergy();


        if (!data.isMoveAllowed(move)) {
            Logger.warning(move + " not allowed for move for type " + data.getType());
            return false;
        }
        if (!data.isWeaponAllowed(interaction)) {
            Logger.warning(interaction + " not allowed for interactions for type " + data.getType());
            return false;
        }
        if (!data.isHullAllowed(hull)) {
            Logger.warning(interaction + " not allowed for hull for type " + data.getType());
            return false;
        }
        if (!data.isEnergyAllowed(energy)) {
            Logger.warning(interaction + " not allowed for energy for type " + data.getType());
            return false;
        }
        if (!data.isOtherAllowed(modules.getModules())) {
            Logger.warning("Other modules not allowed for modules for type " + data.getType());
            return false;
        }
        return true;
    }

}
