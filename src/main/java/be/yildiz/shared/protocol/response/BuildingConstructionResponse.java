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
import be.yildiz.module.network.exceptions.InvalidNetworkMessage;
import be.yildiz.module.network.protocol.MessageWrapper;
import be.yildiz.module.network.protocol.NetworkMessage;
import be.yildiz.module.network.protocol.ServerResponse;
import be.yildiz.shared.building.Building;
import be.yildiz.shared.data.BuildingPosition;
import be.yildiz.shared.data.Level;

/**
 * Response from the server to build or upgrade a building in a base.
 *
 * @author Grégory Van den Borre
 */
public final class BuildingConstructionResponse extends NetworkMessage implements ServerResponse {

    /**
     * Id of the base containing the building.
     */
    private final EntityId cityId;

    /**
     * Type of the building.
     */
    private final int type;

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
     * Time when the building will be completed.
     */
    private final long time;

    /**
     * Full constructor, build the object from String representation.
     *
     * @param message Message received from the server to parse.
     * @throws InvalidNetworkMessage If an error occurs while parsing the message.
     */
    public BuildingConstructionResponse(final MessageWrapper message) throws InvalidNetworkMessage {
        super(message);
        this.cityId = this.getEntityId();
        this.type = this.getInt();
        this.level = new Level(this.getInt());
        this.position = new BuildingPosition(this.getInt());
        this.staff = this.getInt();
        this.time = this.getLong();
    }

    /**
     * Full constructor.
     *
     * @param b    Building in construction.
     * @param time Time when the building will be completed.
     */
    public BuildingConstructionResponse(final Building b, final long time) {
        super(NetworkMessage.convertParams(b.getCity(), Integer.valueOf(b.getType().type), Integer.valueOf(b.getLevel().value), Integer.valueOf(b.getBuildingPosition().value),
                Integer.valueOf(b.getStaff()), Long.valueOf(time)));
        this.cityId = b.getCity();
        this.type = b.getType().type;
        this.level = b.getLevel();
        this.position = b.getBuildingPosition();
        this.staff = b.getStaff();
        this.time = time;
    }

    /**
     * @return Value of ServerCommand BUILDING_INFO.
     */
    @Override
    public int command() {
        return ServerCommand.BUILDING_INFO.value;
    }

    public EntityId getCityId() {
        return cityId;
    }

    public int getType() {
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

    public long getTime() {
        return time;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        BuildingConstructionResponse that = (BuildingConstructionResponse) o;

        if (type != that.type) {
            return false;
        }
        if (staff != that.staff) {
            return false;
        }
        if (time != that.time) {
            return false;
        }
        if (!cityId.equals(that.cityId)) {
            return false;
        }
        if (!level.equals(that.level)) {
            return false;
        }
        return position.equals(that.position);
    }

    @Override
    public int hashCode() {
        int result = cityId.hashCode();
        result = 31 * result + type;
        result = 31 * result + level.hashCode();
        result = 31 * result + position.hashCode();
        result = 31 * result + staff;
        result = 31 * result + (int) (time ^ (time >>> 32));
        return result;
    }
}
