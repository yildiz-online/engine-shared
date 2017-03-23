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

package be.yildiz.shared.protocol.request;

import be.yildiz.common.id.EntityId;
import be.yildiz.module.network.exceptions.InvalidNetworkMessage;
import be.yildiz.module.network.protocol.MessageWrapper;
import be.yildiz.module.network.protocol.NetworkMessage;
import be.yildiz.module.network.protocol.ServerRequest;
import be.yildiz.shared.data.BuildingPosition;
import be.yildiz.shared.data.EntityType;
import be.yildiz.shared.data.Level;

/**
 * A request from the client to the server to build or upgrade a building.
 *
 * @author Grégory Van den Borre
 */
public final class BuildingConstructionRequest extends NetworkMessage implements ServerRequest {

    /**
     * Id of the base containing the building.
     */
    private final EntityId cityId;

    /**
     * Type of the building.
     */
    private final EntityType type;

    /**
     * Level of the building.
     */
    private final Level level;

    /**
     * Position of the building in the base.
     */
    private final BuildingPosition position;

    /**
     * Staff allocated to the building.
     */
    private final int staff;

    /**
     * Full Constructor. Create the object from a message received.
     *
     * @param message Message to parse to build the object.
     * @throws InvalidNetworkMessage If the message cannot be correctly parsed.
     */
    public BuildingConstructionRequest(final MessageWrapper message) throws InvalidNetworkMessage {
        super(message);
        this.cityId = this.getEntityId();
        this.position = new BuildingPosition(this.getInt());
        this.type = EntityType.get(this.getInt());
        this.level = new Level(this.getInt());
        this.staff = this.getInt();
    }

    /**
     * Full constructor.
     *
     * @param baseId         Id of the base containing the building.
     * @param positionInCity Position of the building in the base.
     * @param buildingType   Type of the building.
     * @param buildingLevel  Level of the building.
     * @param staff          Number of allocated staff.
     */
    public BuildingConstructionRequest(final EntityId baseId, final BuildingPosition positionInCity, final EntityType buildingType, final Level buildingLevel, final int staff) {
        super(NetworkMessage.convertParams(baseId, Integer.valueOf(positionInCity.value), Integer.valueOf(buildingType.type), Integer.valueOf(buildingLevel.value), Integer.valueOf(staff)));
        this.cityId = baseId;
        this.position = positionInCity;
        this.type = buildingType;
        this.level = buildingLevel;
        this.staff = staff;
    }

    /**
     * @return Value of ClientCommand CREATE_BUILDING.
     */
    @Override
    public int command() {
        return ClientCommand.CREATE_BUILDING.ordinal();
    }

    public EntityId getCityId() {
        return cityId;
    }

    public EntityType getType() {
        return type;
    }

    public Level getLevel() {
        return level;
    }

    public BuildingPosition getPosition() {
        return position;
    }

    public int getStaff() {
        return staff;
    }
}
