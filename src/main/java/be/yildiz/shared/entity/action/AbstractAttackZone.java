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
import be.yildiz.common.vector.Point3D;
import be.yildiz.shared.entity.Entity;
import be.yildiz.shared.entity.TargetRetriever;
import be.yildiz.shared.entity.fields.Target;

import java.util.Set;

/**
 * Base class for an attack zone, it contains the target retriever and a zone
 * materialization.
 *
 * @author Grégory Van den Borre
 */
public abstract class AbstractAttackZone<T extends Entity> extends AbstractAttack {

    /**
     * Logic to compute the entities in the target zone.
     */
    private final TargetRetriever<T> targetRetriever;

    /**
     * Size of the damage zone.
     */
    private final int damageZone;

    /**
     * Materialization for the zone.
     */
    private ZoneMaterializer materializer;

    /**
     * Position to target.
     */
    private Point3D targetPosition;

    /**
     * Build the Attack Zone.
     *
     * @param attacker   Entity using the action.
     * @param retriever  Logic to compute the entities to find in the zone.
     */
    public AbstractAttackZone(ActionId id, final EntityId attacker, final TargetRetriever<T> retriever) {
        super(attacker, id);
        //FIXME implements
        this.damageZone = 0;
        this.targetRetriever = retriever;
    }

    protected final Set<Target> retrieveTarget() {
        return this.targetRetriever.retrieve(this.targetPosition, damageZone);
    }

    @Override
    public void setTarget(final Target target) {
        this.setDestination(target.getPosition());
    }

    @Override
    public Point3D getDestination() {
        return this.targetPosition;
    }

    @Override
    public void setDestination(Point3D destination) {
        this.targetPosition = destination;
        this.materializer.setTargetPosition(destination);
    }
}
