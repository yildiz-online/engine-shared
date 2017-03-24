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

package be.yildiz.shared.construction.entity;

import be.yildiz.common.id.EntityId;
import be.yildiz.common.id.PlayerId;
import be.yildiz.shared.construction.entity.EntityConstructionQueue.EntityRepresentationConstruction;
import be.yildiz.shared.entity.DefaultEntityInConstruction;

/**
 * Class with entity data and building time.
 *
 * @author Grégory Van den Borre
 */
public final class WaitingEntity {

    /**
     * The entity to build data.
     */
    public final DefaultEntityInConstruction entity;

    public final EntityRepresentationConstruction representation;

    /**
     * Unique id of the builder of this entity.
     */
    public final EntityId builderId;

    public WaitingEntity(DefaultEntityInConstruction entity, EntityRepresentationConstruction representation, EntityId builderId) {
        this.entity = entity;
        this.representation = representation;
        this.builderId = builderId;
    }

    public boolean isOwned(final PlayerId player) {
        assert player != null;
        return this.entity.getOwner().equals(player);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        WaitingEntity that = (WaitingEntity) o;

        if (!entity.equals(that.entity)) {
            return false;
        }
        if (!representation.equals(that.representation)) {
            return false;
        }
        return builderId.equals(that.builderId);
    }

    @Override
    public int hashCode() {
        int result = entity.hashCode();
        result = 31 * result + representation.hashCode();
        result = 31 * result + builderId.hashCode();
        return result;
    }
}
