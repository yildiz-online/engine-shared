package be.yildiz.shared.protocol.mapper;

import be.yildiz.shared.protocol.EntityConstructionDto;
import be.yildizgames.common.mapping.*;

/**
 * @author Grégory Van den Borre
 */
public class EntityConstructionDtoMapper implements ObjectMapper<EntityConstructionDto> {

    @Override
    public EntityConstructionDto from(String s) throws MappingException {
        assert s != null;
        String[] v = s.split(Separator.OBJECTS_SEPARATOR);
        try {
            return new EntityConstructionDto(
                    EntityIdMapper.getInstance().from(v[0]),
                    EntityTypeMapper.getInstance().from(v[1]),
                    ModuleGroupMapper.getInstance().from(v[2]),
                    IntegerMapper.getInstance().from(v[3])
            );
        } catch (IndexOutOfBoundsException e) {
            throw new MappingException(e);
        }
    }

    @Override
    public String to(EntityConstructionDto dto) {
        assert dto != null;
        return EntityIdMapper.getInstance().to(dto.builderId)
                + Separator.OBJECTS_SEPARATOR
                + EntityTypeMapper.getInstance().to(dto.type)
                + Separator.OBJECTS_SEPARATOR
                + ModuleGroupMapper.getInstance().to(dto.moduleIds)
                + Separator.OBJECTS_SEPARATOR
                + IntegerMapper.getInstance().to(dto.requestIndex);
    }
}
