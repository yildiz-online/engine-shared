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

package be.yildiz.shared.protocol.response;

import be.yildiz.common.id.EntityId;
import be.yildiz.common.id.PlayerId;
import be.yildiz.common.vector.Point3D;
import be.yildiz.module.network.exceptions.InvalidNetworkMessage;
import be.yildiz.module.network.protocol.MessageWrapper;
import be.yildiz.module.network.protocol.NetworkMessage;
import be.yildiz.module.network.protocol.ServerResponse;
import be.yildiz.shared.data.EntityType;
import be.yildiz.shared.entity.Entity;
import be.yildiz.shared.entity.module.ModuleGroup;
import lombok.EqualsAndHashCode;

/**
 * Message sent from the server to the client when an Entity is created.
 *
 * @author Grégory Van den Borre
 */
@EqualsAndHashCode(callSuper = false)
public final class EntityInfoResponse extends NetworkMessage implements ServerResponse {

    /**
     * Entity associated Id.
     */
    private final EntityId entity;

    /**
     * Entity name.
     */
    private final String name;

    /**
     * Entity type.
     */
    private final EntityType type;

    /**
     * Id of the Player owning this Entity.
     */
    private final PlayerId owner;

    /**
     * Entity position.
     */
    private final Point3D position;

    /**
     * Entity direction.
     */
    private final Point3D orientation;

    /**
     * Entity hit points.
     */
    private final int hitPoint;

    /**
     * Entity energy points.
     */
    private final int energy;

    private final ModuleGroup modules;

    private final EntityId builderId;

    private final int index;

    /**
     * Full constructor, parse the message to build the object.
     *
     * @param message Message received from the server.
     * @throws InvalidNetworkMessage In case of error while parsing the message.
     */
    public EntityInfoResponse(final MessageWrapper message) throws InvalidNetworkMessage {
        super(message);
        this.entity = this.getEntityId();
        this.name = this.getString();
        this.type = EntityType.get(this.getInt());
        this.owner = this.getPlayerId();
        this.position = this.getPoint3D();
        this.orientation = this.getPoint3D();
        this.hitPoint = this.getInt();
        this.energy = this.getInt();
        this.builderId = this.getEntityId();
        this.modules = new ModuleGroup.ModuleGroupBuilder().fromList(this.getActionIdList()).build();
        this.index = this.getInt();
    }

    public EntityInfoResponse(final Entity e) {
        this(e, EntityId.WORLD, 0);
    }

    /**
     * Full constructor, builder is assumed to be World.
     *
     * @param e Entity to send on the network.
     * @param builderId Id of the builder building this entity.
     * @param index Construction unique index.
     */
    public EntityInfoResponse(final Entity e, final EntityId builderId, final int index) {
        super(NetworkMessage.convertParams(e.getId(), e.getName(), Integer.valueOf(e.getType().type), e.getOwner(), e.getPosition(), e.getDirection(), e.getHitPoints(), e
                .getEnergyPoints(), builderId.value, e.getModules().getAll(), index));
        this.entity = e.getId();
        this.name = e.getName();
        this.type = e.getType();
        this.owner = e.getOwner();
        this.position = e.getPosition();
        this.orientation = e.getDirection();
        this.hitPoint = e.getHitPoints();
        this.energy = e.getEnergyPoints();
        this.builderId = builderId;
        this.modules = e.getModules();
        this.index = index;
    }

    /**
     * @return The ordinal value of ServerCommand BUILD.
     */
    @Override
    public int command() {
        return ServerCommand.BUILD.value;
    }

    public EntityId getEntity() {
        return entity;
    }

    public String getName() {
        return name;
    }

    public EntityType getType() {
        return type;
    }

    public PlayerId getOwner() {
        return owner;
    }

    public Point3D getPosition() {
        return position;
    }

    public Point3D getOrientation() {
        return orientation;
    }

    public int getHitPoint() {
        return hitPoint;
    }

    public int getEnergy() {
        return energy;
    }

    public ModuleGroup getModules() {
        return modules;
    }

    public EntityId getBuilderId() {
        return builderId;
    }

    public int getIndex() {
        return index;
    }
}
