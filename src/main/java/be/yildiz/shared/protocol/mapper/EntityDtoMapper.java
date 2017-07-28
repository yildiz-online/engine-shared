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

import be.yildiz.module.network.exceptions.InvalidNetworkMessage;
import be.yildiz.module.network.protocol.MessageSeparation;
import be.yildiz.module.network.protocol.mapper.IntegerMapper;
import be.yildiz.module.network.protocol.mapper.ObjectMapper;
import be.yildiz.module.network.protocol.mapper.PlayerIdMapper;
import be.yildiz.shared.protocol.EntityDto;

/**
 * @author Grégory Van den Borre
 */
public class EntityDtoMapper implements ObjectMapper<EntityDto> {

    @Override
    public EntityDto from(String s) throws InvalidNetworkMessage {
        assert s != null;
        String[] v = s.split(MessageSeparation.OBJECTS_SEPARATOR);
        try {
            return new EntityDto(
                    EntityIdMapper.getInstance().from(v[0]),
                    v[1],
                    EntityTypeMapper.getInstance().from(v[2]),
                    PlayerIdMapper.getInstance().from(v[3]),
                    Point3DMapper.getInstance().from(v[4]),
                    Point3DMapper.getInstance().from(v[5]),
                    IntegerMapper.getInstance().from(v[6]),
                    IntegerMapper.getInstance().from(v[7]),
                    ModuleGroupMapper.getInstance().from(v[8]),
                    EntityIdMapper.getInstance().from(v[9]),
                    IntegerMapper.getInstance().from(v[10])
            );
        } catch (IndexOutOfBoundsException e) {
            throw new InvalidNetworkMessage(e);
        }
    }

    @Override
    public String to(EntityDto dto) {
        assert dto != null;
        return EntityIdMapper.getInstance().to(dto.entity)
                + MessageSeparation.OBJECTS_SEPARATOR
                + dto.name
                + MessageSeparation.OBJECTS_SEPARATOR
                + EntityTypeMapper.getInstance().to(dto.type)
                + MessageSeparation.OBJECTS_SEPARATOR
                + PlayerIdMapper.getInstance().to(dto.owner)
                + MessageSeparation.OBJECTS_SEPARATOR
                + Point3DMapper.getInstance().to(dto.position)
                + MessageSeparation.OBJECTS_SEPARATOR
                + Point3DMapper.getInstance().to(dto.orientation)
                + MessageSeparation.OBJECTS_SEPARATOR
                + IntegerMapper.getInstance().to(dto.hitPoint)
                + MessageSeparation.OBJECTS_SEPARATOR
                + IntegerMapper.getInstance().to(dto.energy)
                + MessageSeparation.OBJECTS_SEPARATOR
                + ModuleGroupMapper.getInstance().to(dto.modules)
                + MessageSeparation.OBJECTS_SEPARATOR
                + EntityIdMapper.getInstance().to(dto.builderId)
                + MessageSeparation.OBJECTS_SEPARATOR
                + IntegerMapper.getInstance().to(dto.index);

    }
}
