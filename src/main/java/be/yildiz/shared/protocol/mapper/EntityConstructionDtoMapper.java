package be.yildiz.shared.protocol.mapper;

import be.yildiz.module.network.exceptions.InvalidNetworkMessage;
import be.yildiz.module.network.protocol.MessageSeparation;
import be.yildiz.module.network.protocol.mapper.IntegerMapper;
import be.yildiz.module.network.protocol.mapper.ObjectMapper;
import be.yildiz.shared.protocol.EntityConstructionDto;

/**
 * @author Grégory Van den Borre
 */
public class EntityConstructionDtoMapper implements ObjectMapper<EntityConstructionDto> {

    @Override
    public EntityConstructionDto from(String s) throws InvalidNetworkMessage {
        assert s != null;
        String[] v = s.split(MessageSeparation.OBJECTS_SEPARATOR);
        try {
            return new EntityConstructionDto(
                    EntityIdMapper.getInstance().from(v[0]),
                    EntityTypeMapper.getInstance().from(v[1]),
                    ModuleGroupMapper.getInstance().from(v[2]),
                    IntegerMapper.getInstance().from(v[3])
            );
        } catch (IndexOutOfBoundsException e) {
            throw new InvalidNetworkMessage(e);
        }
    }

    @Override
    public String to(EntityConstructionDto dto) {
        assert dto != null;
        return EntityIdMapper.getInstance().to(dto.builderId)
                + MessageSeparation.OBJECTS_SEPARATOR
                + EntityTypeMapper.getInstance().to(dto.type)
                + MessageSeparation.OBJECTS_SEPARATOR
                + ModuleGroupMapper.getInstance().to(dto.moduleIds)
                + MessageSeparation.OBJECTS_SEPARATOR
                + IntegerMapper.getInstance().to(dto.requestIndex);
    }
}
