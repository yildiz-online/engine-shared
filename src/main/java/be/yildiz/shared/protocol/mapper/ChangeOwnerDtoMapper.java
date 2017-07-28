/*
 * This file is part of the Yildiz-Engine project, licenced under the MIT License  (MIT)
 *
 *  Copyright (c) 2017 Grégory Van den Borre
 *
 *  More infos available: https://www.yildiz-games.be
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 *  documentation files (the "Software"), to deal in the Software without restriction, including without
 *  limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 *  of the Software, and to permit persons to whom the Software is furnished to do so,
 *  subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be included in all copies or substantial
 *  portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 *  WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS
 *  OR COPYRIGHT  HOLDERS BE LIABLE FOR ANY CLAIM,
 *  DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE  SOFTWARE.
 *
 */

package be.yildiz.shared.protocol.mapper;

import be.yildiz.module.network.exceptions.InvalidNetworkMessage;
import be.yildiz.module.network.protocol.MessageSeparation;
import be.yildiz.module.network.protocol.mapper.ObjectMapper;
import be.yildiz.module.network.protocol.mapper.PlayerIdMapper;
import be.yildiz.shared.protocol.ChangeOwnerDto;

/**
 * @author Grégory Van den Borre
 */
public class ChangeOwnerDtoMapper implements ObjectMapper<ChangeOwnerDto>{

    private static final ChangeOwnerDtoMapper INSTANCE = new ChangeOwnerDtoMapper();

    private ChangeOwnerDtoMapper() {
        super();
    }

    public static ChangeOwnerDtoMapper getInstance() {
        return INSTANCE;
    }


    @Override
    public ChangeOwnerDto from(String s) throws InvalidNetworkMessage {
        assert s != null;
        String[] v = s.split(MessageSeparation.OBJECTS_SEPARATOR);
        try {
            return new ChangeOwnerDto(EntityIdMapper.getInstance().from(v[0]), PlayerIdMapper.getInstance().from(v[1]));
        } catch (IndexOutOfBoundsException e) {
            throw new InvalidNetworkMessage(e);
        }
    }

    @Override
    public String to(ChangeOwnerDto dto) {
        assert dto != null;
        return EntityIdMapper.getInstance().to(dto.entity)
                + MessageSeparation.OBJECTS_SEPARATOR
                + PlayerIdMapper.getInstance().to(dto.newOwnerId);
    }
}
