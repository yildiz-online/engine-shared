package be.yildiz.shared.protocol.response;

import be.yildiz.common.id.EntityId;
import be.yildiz.common.vector.Point3D;

/**
 * @author Grégory Van den Borre
 */
public class EntityPositionDto {

    private final EntityId id;

    /**
     * Unit position.
     */
    private final Point3D position;

    /**
     * Unit direction.
     */
    private final Point3D orientation;

    public EntityPositionDto(EntityId id, Point3D position, Point3D orientation) {
        this.id = id;
        this.position = position;
        this.orientation = orientation;
    }
}
