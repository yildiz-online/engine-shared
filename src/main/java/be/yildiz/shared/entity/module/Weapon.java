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
import be.yildiz.shared.data.AttackDamage;
import be.yildiz.shared.data.AttackRange;
import be.yildiz.shared.data.AttackTime;
import be.yildiz.shared.entity.action.AbstractAttack;

/**
 * @author Grégory Van den Borre
 */
public class Weapon extends Module<AbstractAttack> {

    public Weapon(AbstractAttack action, ActionId id, AttackDamage damage, AttackRange range, AttackTime time) {
        super(action, id);
        action.setDamage(damage);
        action.setRange(range);
        action.setAttackTime(time);
    }

    public static abstract class WeaponTemplate<T extends AbstractAttack> {

        protected WeaponTemplate() {
            super();
        }

        public abstract Weapon materialize(T action);
    }
}
