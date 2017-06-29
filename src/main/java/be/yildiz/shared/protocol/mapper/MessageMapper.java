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

package be.yildiz.shared.protocol.mapper;

import be.yildiz.module.network.exceptions.InvalidNetworkMessage;
import be.yildiz.module.network.protocol.MessageSeparation;
import be.yildiz.module.network.protocol.mapper.BooleanMapper;
import be.yildiz.module.network.protocol.mapper.LongMapper;
import be.yildiz.module.network.protocol.mapper.ObjectMapper;
import be.yildiz.module.network.protocol.mapper.PlayerIdMapper;
import be.yildiz.shared.player.Message;

/**
 * @author Grégory Van den Borre
 */
public class MessageMapper implements ObjectMapper<Message> {

    private static final String REP_AMP = "A5A7";

    private static final String REP_HSH = "H5H6";

    private static final String REP_UND = "U5K9";

    private static final String REP_AT = "T5G7";

    @Override
    public Message from(String s) throws InvalidNetworkMessage {
        String[] v = s.split(MessageSeparation.VAR_SEPARATOR);
        try {
            return new Message(PlayerIdMapper.getInstance().from(v[0]),
                    PlayerIdMapper.getInstance().from(v[1]),
                    v[2].replaceAll(REP_AMP, "&")
                        .replaceAll(REP_AT, "@")
                        .replaceAll(REP_HSH, "#")
                        .replaceAll(REP_UND, "_"),
                    LongMapper.getInstance().from(v[3]),
                    BooleanMapper.getInstance().from(v[4]));
        } catch (IndexOutOfBoundsException e) {
            throw new InvalidNetworkMessage(e);
        }
    }

    @Override
    public String to(Message message) {
        return PlayerIdMapper.getInstance().to(message.getSender())
                + MessageSeparation.VAR_SEPARATOR
                + PlayerIdMapper.getInstance().to(message.getReceiver())
                + MessageSeparation.VAR_SEPARATOR
                + message.getMessage()
                        .replaceAll("&", REP_AMP)
                        .replaceAll("#", REP_HSH)
                        .replaceAll("_", REP_UND)
                        .replaceAll("@", REP_AT)
                + MessageSeparation.VAR_SEPARATOR
                + LongMapper.getInstance().to(message.getDate())
                + MessageSeparation.VAR_SEPARATOR
                + BooleanMapper.getInstance().to(message.isRead());
    }
}
