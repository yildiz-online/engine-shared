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
        assert id != null;
        assert entity != null;
        assert destination != null;
        assert target != null;
        this.id = id;
        this.entity = entity;
        this.destination = destination;
        this.target = target;
    }

    public ActionDto(Action a) {
        this(a.id, a.getEntity(), a.getDestination(), a.getTargetId());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ActionDto actionDto = (ActionDto) o;

        return id.equals(actionDto.id) && entity.equals(actionDto.entity) && destination.equals(actionDto.destination) && target.equals(actionDto.target);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + entity.hashCode();
        result = 31 * result + destination.hashCode();
        result = 31 * result + target.hashCode();
        return result;
    }
}
