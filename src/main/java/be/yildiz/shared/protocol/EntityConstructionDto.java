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

package be.yildiz.shared.protocol;

import be.yildiz.common.id.EntityId;
import be.yildiz.shared.data.EntityType;
import be.yildiz.shared.entity.module.ModuleGroup;

/**
 * @author Grégory Van den Borre
 */
public class EntityConstructionDto {

    /**
     * Builder entity id.
     */
    public final EntityId builderId;

    /**
     * Type of the entity to build.
     */
    public final EntityType type;

    /**
     * Ids of the modules associated to the entity to build.
     */
    public final ModuleGroup moduleIds;

    /**
     * A request index to match with the message response.
     */
    public final int requestIndex;

    public EntityConstructionDto(EntityId builderId, EntityType type, ModuleGroup moduleIds, int requestIndex) {
        this.builderId = builderId;
        this.type = type;
        this.moduleIds = moduleIds;
        this.requestIndex = requestIndex;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        EntityConstructionDto that = (EntityConstructionDto) o;
        return requestIndex == that.requestIndex && builderId.equals(that.builderId) && type.equals(that.type) && moduleIds.equals(that.moduleIds);
    }

    @Override
    public int hashCode() {
        int result = builderId.hashCode();
        result = 31 * result + type.hashCode();
        result = 31 * result + moduleIds.hashCode();
        result = 31 * result + requestIndex;
        return result;
    }
}
