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

package be.yildiz.shared.protocol.mapper;

import be.yildiz.common.id.ActionId;
import be.yildiz.common.id.EntityId;
import be.yildiz.common.vector.Point3D;
import be.yildiz.module.network.exceptions.InvalidNetworkMessage;
import be.yildiz.module.network.protocol.MessageSeparation;
import be.yildiz.module.network.protocol.mapper.ObjectMapper;
import be.yildiz.shared.protocol.ActionDto;

/**
 * @author Grégory Van den Borre
 */
public class ActionMapper implements ObjectMapper <ActionDto>{

    private final ObjectMapper<ActionId> actionIdMapper;

    private final ObjectMapper<EntityId> entityIdMapper;

    private final ObjectMapper<Point3D> point3DMapper;

    public ActionMapper(ObjectMapper<ActionId> actionIdMapper, ObjectMapper<EntityId> entityIdMapper, ObjectMapper<Point3D> point3DMapper) {
        super();
        this.actionIdMapper = actionIdMapper;
        this.entityIdMapper = entityIdMapper;
        this.point3DMapper = point3DMapper;
    }

    @Override
    public ActionDto from(String s) throws InvalidNetworkMessage {
        String[] v = s.split(MessageSeparation.OBJECTS_SEPARATOR);
        try {
            return new ActionDto(actionIdMapper.from(v[0]), entityIdMapper.from(v[1]), point3DMapper.from(v[2]), entityIdMapper.from(v[3]));
        } catch (IndexOutOfBoundsException e) {
            throw new InvalidNetworkMessage(e);
        }
    }

    @Override
    public String to(ActionDto action) {
        return this.actionIdMapper.to(action.id)
                + MessageSeparation.OBJECTS_SEPARATOR
                + this.entityIdMapper.to(action.entity)
                + MessageSeparation.OBJECTS_SEPARATOR
                + this.point3DMapper.to(action.destination)
                + MessageSeparation.OBJECTS_SEPARATOR
                + this.entityIdMapper.to(action.target);
    }
}