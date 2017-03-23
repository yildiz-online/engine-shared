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

package be.yildiz.shared.entity.module;

import be.yildiz.common.id.ActionId;
import be.yildiz.shared.entity.action.ProduceEnergy;

/**
 * @author Grégory Van den Borre
 */
public class EnergyGenerator extends Module<ProduceEnergy> {

    private final int energyMax;

    public EnergyGenerator(ProduceEnergy action, ActionId id, int energyMax) {
        super(action, id);
        this.energyMax = energyMax;
    }

    public final int getEnergyMax() {
        return energyMax;
    }

    public abstract static class EnergyGeneratorTemplate<T extends ProduceEnergy> {

        public final int maxEnergy;

        protected EnergyGeneratorTemplate(final int maxEnergy) {
            super();
            this.maxEnergy = maxEnergy;
        }

        public abstract EnergyGenerator materialize(T action);
    }
}
