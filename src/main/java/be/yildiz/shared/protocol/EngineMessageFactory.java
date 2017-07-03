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
import be.yildiz.common.id.PlayerId;
import be.yildiz.module.network.exceptions.InvalidNetworkMessage;
import be.yildiz.module.network.protocol.MessageWrapper;
import be.yildiz.module.network.protocol.NetworkMessage;
import be.yildiz.module.network.protocol.NetworkMessageFactory;
import be.yildiz.module.network.protocol.mapper.IntegerMapper;
import be.yildiz.module.network.protocol.mapper.PlayerIdMapper;
import be.yildiz.shared.entity.module.ModuleConfiguration;
import be.yildiz.shared.player.Message;
import be.yildiz.shared.protocol.mapper.*;
import be.yildiz.shared.protocol.request.ClientCommand;
import be.yildiz.shared.protocol.response.ServerCommand;

/**
 * @author Grégory Van den Borre
 */
public class EngineMessageFactory extends NetworkMessageFactory{

    private final ActionDtoMapper actionDtoMapper = new ActionDtoMapper();

    private final EntityConstructionDtoMapper entityConstructionDtoMapper = new EntityConstructionDtoMapper();

    private final BuildingConstructionDtoMapper buildingConstructionDtoMapper = new BuildingConstructionDtoMapper();

    private final EntityDtoMapper entityDtoMapper = new EntityDtoMapper();

    private final MessageMapper messageMapper = new MessageMapper();

    public NetworkMessage<ActionDto> ActionRequest(ActionDto a) {
        return new NetworkMessage<>(a, actionDtoMapper, ClientCommand.ACTION.ordinal());
    }

    public NetworkMessage<ActionDto> ActionRequest(MessageWrapper msg) throws InvalidNetworkMessage {
        return new NetworkMessage<>(msg, actionDtoMapper, ClientCommand.ACTION.ordinal());
    }

    public NetworkMessage<Integer> buildCancel(Integer i) {
        return new NetworkMessage<>(i, IntegerMapper.getInstance(), ClientCommand.BUILD_CANCEL.ordinal());
    }

    public NetworkMessage<Integer> buildCancel(MessageWrapper msg) throws InvalidNetworkMessage {
        return new NetworkMessage<>(msg, IntegerMapper.getInstance(), ClientCommand.BUILD_CANCEL.ordinal());
    }

    public NetworkMessage<EntityConstructionDto> message(EntityConstructionDto e) {
        return new NetworkMessage<>(e, entityConstructionDtoMapper, 0);
    }

    public NetworkMessage<BuildingConstructionDto> message(BuildingConstructionDto dto) {
        return new NetworkMessage<>(dto, buildingConstructionDtoMapper, 2);
    }

    public NetworkMessage<EntityDto> entityInfo(EntityDto dto) {
        return new NetworkMessage<>(dto, entityDtoMapper, ServerCommand.ENTITY_INFO.value);
    }

    public NetworkMessage<EntityDto> entityInfo(MessageWrapper msg) throws InvalidNetworkMessage {
        return new NetworkMessage<>(msg, entityDtoMapper, ServerCommand.ENTITY_INFO.value);
    }

    public NetworkMessage<EntityId> destroy(EntityId id) {
        return new NetworkMessage<>(id, EntityIdMapper.getInstance(), 4);
    }

    public NetworkMessage<Message> message(Message m) {
        return new NetworkMessage<>(m, messageMapper, 18);
    }

    public NetworkMessage<ResourceTransferDto> resourceTransferResponse(ResourceTransferDto dto) {
        return new NetworkMessage<>(dto, ResourceTransferDtoMapper.getInstance(), ServerCommand.RESOURCE_TRANSFER.value);
    }

    public NetworkMessage<ResourceTransferDto> resourceTransferResponse(MessageWrapper msg) throws InvalidNetworkMessage {
        return new NetworkMessage<>(msg, ResourceTransferDtoMapper.getInstance(), ServerCommand.RESOURCE_TRANSFER.value);
    }

    public NetworkMessage<PlayerDto> playerInfoResponse(PlayerDto dto) {
        return new NetworkMessage<>(dto, PlayerDtoMapper.getInstance(), ServerCommand.PLAYER_INFO.value);
    }

    public NetworkMessage<PlayerDto> playerInfoResponse(MessageWrapper msg) throws InvalidNetworkMessage {
        return new NetworkMessage<>(msg, PlayerDtoMapper.getInstance(), ServerCommand.PLAYER_INFO.value);
    }

    public NetworkMessage<ResourceValueDto> resourceUpdateResponse(ResourceValueDto dto) {
        return new NetworkMessage<>(dto, ResourceValueDtoMapper.getInstance(), ServerCommand.RESOURCE.value);
    }

    public NetworkMessage<ResourceValueDto> resourceUpdateResponse(MessageWrapper msg) throws InvalidNetworkMessage {
        return new NetworkMessage<>(msg, ResourceValueDtoMapper.getInstance(), ServerCommand.RESOURCE.value);
    }

    public NetworkMessage<PlayerId> adminMonitorPlayer(PlayerId dto) {
        return new NetworkMessage<>(dto, PlayerIdMapper.getInstance(), ClientCommand.ADMIN_MONITOR_PLAYER.ordinal());
    }

    public NetworkMessage<PlayerId> adminMonitorPlayer(MessageWrapper msg) throws InvalidNetworkMessage {
        return new NetworkMessage<>(msg, PlayerIdMapper.getInstance(), ClientCommand.ADMIN_MONITOR_PLAYER.ordinal());
    }

    public NetworkMessage<EntityPositionDto> updatePositionResponse(EntityPositionDto dto) {
        return new NetworkMessage<>(dto, EntityPositionDtoMapper.getInstance(), ServerCommand.POSITION.value);
    }

    public NetworkMessage<EntityPositionDto> updatePositionResponse(MessageWrapper msg) throws InvalidNetworkMessage {
        return new NetworkMessage<>(msg, EntityPositionDtoMapper.getInstance(), ServerCommand.POSITION.value);
    }

    public NetworkMessage<EntityId> removeVisibilityResponse(EntityId dto) {
        return new NetworkMessage<>(dto, EntityIdMapper.getInstance(), ServerCommand.UNIT_NO_LONGER_VISIBLE.value);
    }

    public NetworkMessage<EntityId> removeVisibilityResponse(MessageWrapper msg) throws InvalidNetworkMessage {
        return new NetworkMessage<>(msg, EntityIdMapper.getInstance(), ServerCommand.UNIT_NO_LONGER_VISIBLE.value);
    }

    public NetworkMessage<EntityId> stopAttackResponse(EntityId dto) {
        return new NetworkMessage<>(dto, EntityIdMapper.getInstance(), ServerCommand.STOP_ATTACK.value);
    }

    public NetworkMessage<EntityId> stopAttackResponse(MessageWrapper msg) throws InvalidNetworkMessage {
        return new NetworkMessage<>(msg, EntityIdMapper.getInstance(), ServerCommand.STOP_ATTACK.value);
    }

    public NetworkMessage<StaffAllocationDto> staffAllocationRequest(StaffAllocationDto dto) {
        return new NetworkMessage<>(dto, StaffAllocationDtoMapper.getInstance(), ClientCommand.STAFF.ordinal());
    }

    public NetworkMessage<StaffAllocationDto> staffAllocationRequest(MessageWrapper msg) throws InvalidNetworkMessage {
        return new NetworkMessage<>(msg, StaffAllocationDtoMapper.getInstance(), ClientCommand.STAFF.ordinal());
    }

    public NetworkMessage<EntityHitDto> entityHitResponse(EntityHitDto dto) {
        return new NetworkMessage<>(dto, EntityHitDtoMapper.getInstance(), ServerCommand.HIT.value);
    }

    public NetworkMessage<EntityHitDto> entityHitResponse(MessageWrapper msg) throws InvalidNetworkMessage {
        return new NetworkMessage<>(msg, EntityHitDtoMapper.getInstance(), ServerCommand.HIT.value);
    }

    public NetworkMessage<ResearchDto> researchRequest(ResearchDto dto) {
        return new NetworkMessage<>(dto, ResearchDtoMapper.getInstance(), ClientCommand.RESEARCH.ordinal());
    }

    public NetworkMessage<ResearchDto> researchRequest(MessageWrapper msg) throws InvalidNetworkMessage {
        return new NetworkMessage<>(msg, ResearchDtoMapper.getInstance(), ClientCommand.RESEARCH.ordinal());
    }

    public NetworkMessage<ModuleConfiguration> savePersistentModuleRequest(ModuleConfiguration dto) {
        return new NetworkMessage<>(dto, ModuleConfigurationMapper.getInstance(), ClientCommand.SAVE_MODULE_CONFIG.ordinal());
    }

    public NetworkMessage<ModuleConfiguration> savePersistentModuleRequest(MessageWrapper msg) throws InvalidNetworkMessage {
        return new NetworkMessage<>(msg, ModuleConfigurationMapper.getInstance(), ClientCommand.SAVE_MODULE_CONFIG.ordinal());
    }
}
