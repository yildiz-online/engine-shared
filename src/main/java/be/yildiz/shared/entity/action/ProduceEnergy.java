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

package be.yildiz.shared.entity.action;

import be.yildiz.common.id.ActionId;
import be.yildiz.common.id.EntityId;

/**
 * @author Grégory Van den Borre
 */
public abstract class ProduceEnergy extends AbstractNoInteractionAction {

    protected ProduceEnergy(EntityId e, ActionId id) {
        super(id, e, true);
    }

    @Override
    public final void runImpl(final long time) {
        this.energyReplenishStrategy(time);
    }

    protected abstract void energyReplenishStrategy(long time);

    /**
     * Only run if energy is not at max (and thus if energy.max is smaller than 0).
     *
     * @return <code>true</code> if the energy is not at max.
     */
    @Override
    public boolean checkPrerequisite() {
        return this.energy.getValue() < this.energy.getMax();
    }

    @Override
    protected void initImpl() {
    }

    @Override
    protected void stopImpl() {
    }

    /**
     * Nothing to delete.
     */
    @Override
    public void delete() {
    }
}
