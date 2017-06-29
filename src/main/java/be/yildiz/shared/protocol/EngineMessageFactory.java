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
import be.yildiz.module.network.protocol.NetworkMessage;
import be.yildiz.module.network.protocol.mapper.IntegerMapper;
import be.yildiz.shared.player.Message;
import be.yildiz.shared.protocol.mapper.*;

/**
 * @author Grégory Van den Borre
 */
public class EngineMessageFactory {

    private final ActionDtoMapper actionDtoMapper = new ActionDtoMapper();

    private final EntityConstructionDtoMapper entityConstructionDtoMapper = new EntityConstructionDtoMapper();

    private final BuildingConstructionDtoMapper buildingConstructionDtoMapper = new BuildingConstructionDtoMapper();

    private final EntityDtoMapper entityDtoMapper = new EntityDtoMapper();

    private final MessageMapper messageMapper = new MessageMapper();

    public NetworkMessage<ActionDto> message(ActionDto a) {
        return new NetworkMessage<>(a, actionDtoMapper, 13);
    }

    public NetworkMessage<Integer> buildCancel(Integer i) {
        return new NetworkMessage<>(i, IntegerMapper.getInstance(), 12);
    }

    public NetworkMessage<EntityConstructionDto> message(EntityConstructionDto e) {
        return new NetworkMessage<>(e, entityConstructionDtoMapper, 0);
    }

    public NetworkMessage<BuildingConstructionDto> message(BuildingConstructionDto dto) {
        return new NetworkMessage<>(dto, buildingConstructionDtoMapper, 2);
    }

    public NetworkMessage<EntityDto> message(EntityDto dto) {
        return new NetworkMessage<>(dto, entityDtoMapper, 3);
    }

    public NetworkMessage<EntityId> destroy(EntityId id) {
        return new NetworkMessage<>(id, EntityIdMapper.getInstance(), 4);
    }

    public NetworkMessage<Message> message(Message m) {
        return new NetworkMessage<>(m, messageMapper, 18);
    }

}
