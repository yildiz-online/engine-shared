//        This file is part of the Yildiz-Online project, licenced under the MIT License
//        (MIT)
//
//        Copyright (c) 2016 Grégory Van den Borre
//
//        More infos available: http://yildiz.bitbucket.org
//
//        Permission is hereby granted, free of charge, to any person obtaining a copy
//        of this software and associated documentation files (the "Software"), to deal
//        in the Software without restriction, including without limitation the rights
//        to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
//        copies of the Software, and to permit persons to whom the Software is
//        furnished to do so, subject to the following conditions:
//
//        The above copyright notice and this permission notice shall be included in all
//        copies or substantial portions of the Software.
//
//        THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
//        IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
//        FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
//        AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
//        LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
//        OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
//        SOFTWARE.

package be.yildiz.shared.protocol.response;

import be.yildiz.common.id.EntityId;
import be.yildiz.module.network.exceptions.InvalidNetworkMessage;
import be.yildiz.module.network.protocol.MessageWrapper;
import be.yildiz.module.network.protocol.NetworkMessage;
import be.yildiz.module.network.protocol.ServerResponse;
import be.yildiz.shared.resources.GameResources;
import be.yildiz.shared.resources.ResourceValue;
import lombok.Getter;

/**
 * Message to update the resource values client side.
 *
 * @author Grégory Van den Borre
 */
@Getter
public final class ResourceUpdateResponse extends NetworkMessage implements ServerResponse {

    /**
     * Id of the city receiving the resources.
     */
    private final EntityId cityId;

    /**
     * Resources values.
     */
    private final ResourceValue resources;

    /**
     * Time when the value has been set.
     */
    private final long time;

    /**
     * Full constructor.
     *
     * @param message Message from the server to parse.
     * @throws InvalidNetworkMessage If an error occurs while parsing the message.
     */
    public ResourceUpdateResponse(final MessageWrapper message) throws InvalidNetworkMessage {
        super(message);
        this.cityId = this.getEntityId();
        float metal = this.getFloat();
        float energy = this.getFloat();
        float credits = this.getFloat();
        float research = this.getFloat();
        float inhabitants = this.getFloat();
        this.time = this.getLong();
        this.resources = GameResources.fullValue(metal, energy, credits, research, inhabitants);
    }

    /**
     * Full constructor.
     *
     * @param city        Id of the city.
     * @param metal       Metal value.
     * @param energy      Energy value.
     * @param credits     Credits value.
     * @param research    Research value.
     * @param inhabitants Inhabitants value.
     */
    public ResourceUpdateResponse(final EntityId city, final float metal, final float energy, final float credits, final float research, final float inhabitants, final long time) {
        super(NetworkMessage.convertParams(city.value, metal, energy, credits, research, inhabitants, time));
        this.cityId = city;
        this.time = time;
        this.resources = GameResources.fullValue(metal, energy, credits, research, inhabitants);
    }

    @Override
    public int command() {
        return ServerCommand.RESOURCE.value;
    }
}
