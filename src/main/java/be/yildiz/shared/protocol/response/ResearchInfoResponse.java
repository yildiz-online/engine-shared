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

import be.yildiz.common.collections.Sets;
import be.yildiz.common.util.StringUtil;
import be.yildiz.module.network.exceptions.InvalidNetworkMessage;
import be.yildiz.module.network.protocol.MessageWrapper;
import be.yildiz.module.network.protocol.NetworkMessage;
import be.yildiz.module.network.protocol.ServerResponse;
import be.yildiz.shared.research.Research;
import lombok.Getter;

import java.util.Set;

/**
 * Provide the list of al research done by the player.
 *
 * @author Grégory Van den Borre
 */
public final class ResearchInfoResponse extends NetworkMessage implements ServerResponse {

    /**
     * List of the researches name.
     */
    @Getter
    private final Set<String> researches;

    /**
     * Full constructor, parse the message to build the object.
     *
     * @param message Message received from the server.
     * @throws InvalidNetworkMessage in case of error while parsing the message.
     */
    public ResearchInfoResponse(final MessageWrapper message) throws InvalidNetworkMessage {
        super(message);
        this.researches = Sets.newSet(this.getStringList());
    }

    /**
     * Full constructor.
     *
     * @param researches List of research names.
     */
    public ResearchInfoResponse(final Set<Research> researches) {
        super(NetworkMessage.convertParams("[" + StringUtil.toString(researches) + "]"));
        this.researches = Sets.newSet();
        researches.forEach(r -> this.researches.add(r.getName()));
    }

    /**
     * @return The ordinal value of ServerCommand RESEARCH_INFO.
     */
    @Override
    public int command() {
        return ServerCommand.RESEARCH_INFO.value;
    }
}
