package be.yildiz.shared.protocol;

import be.yildiz.common.id.ActionId;
import be.yildiz.common.id.EntityId;
import be.yildiz.common.vector.Point3D;
import be.yildiz.shared.entity.action.Action;

/**
 * @author Grégory Van den Borre
 */
public class ActionDto {

    public final ActionId id;

    public final EntityId entity;

    public final Point3D destination;

    public final EntityId target;

    public ActionDto(ActionId id, EntityId entity, Point3D destination, EntityId target) {
        super();
        this.id = id;
        this.entity = entity;
        this.destination = destination;
        this.target = target;
    }

    public ActionDto(Action a) {
        this(a.id, a.getEntity(), a.getDestination(), a.getTargetId());
    }
}
