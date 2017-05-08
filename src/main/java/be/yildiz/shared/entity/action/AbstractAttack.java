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
import be.yildiz.common.util.ElapsedTimeComputer;
import be.yildiz.shared.data.AttackDamage;
import be.yildiz.shared.data.AttackRange;
import be.yildiz.shared.data.AttackTime;
import be.yildiz.shared.entity.fields.AttackHitResult;

/**
 * @author Grégory Van den Borre
 */
public abstract class AbstractAttack extends Action {

    /**
     * Damages to inflict to the target.
     */
    protected AttackDamage damage;

    /**
     * Range to reach to attack.
     */
    protected AttackRange range;

    /**
     * Timer between 2 shots, do not affect the materializer, only the hit result.
     */
    protected ElapsedTimeComputer timer;

    /**
     * Result of this attack.
     */
    protected AttackHitResult attackHit;

    /**
     * Create a new Attack action.
     * @param id Action id of the associated module.
     * @param attacker Entity attacking.
     */
    protected AbstractAttack(final EntityId attacker, ActionId id) {
        super(id, attacker, false);
    }

    /**
     * this method will mostly contains call to materialization, if any.
     */
    protected abstract void fire();

    /**
     * Stop running the fire method.
     */
    protected abstract void stopFire();

    public void setDamage(final AttackDamage damage) {
        this.damage = damage;
        this.attackHit = new AttackHitResult(this.damage.points, 0);
    }

    public void setRange(final AttackRange range) {
        this.range = range;
    }

    public void setAttackTime(final AttackTime time) {
        this.timer = new ElapsedTimeComputer(time.getTime());
    }
}
