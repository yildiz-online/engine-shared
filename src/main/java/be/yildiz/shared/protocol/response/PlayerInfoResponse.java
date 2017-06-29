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

import be.yildiz.module.network.exceptions.InvalidNetworkMessage;
import be.yildiz.module.network.protocol.MessageWrapper;
import be.yildiz.module.network.protocol.NetworkMessage;
import be.yildiz.shared.protocol.mapper.PlayerDtoMapper;

/**
 * Response from the server with a player data.
 *
 * @author Grégory Van den Borre
 */
public final class PlayerInfoResponse extends NetworkMessage implements ServerResponse {

    static {
        PlayerDtoMapper.getInstance();
    }

    /**
     * Player data.
     */
    private final PlayerDto dto;

    /**
     * Full constructor.
     *
     * @param dto     Player data.
     */
    public PlayerInfoResponse(final PlayerDto dto) {
        super(NetworkMessage.to(dto, PlayerDto.class));
        this.dto = dto;
    }

    /**
     * Full constructor.
     *
     * @param message Message from the server to parse.
     * @throws InvalidNetworkMessage If an error occurs while parsing the message.
     */
    public PlayerInfoResponse(final MessageWrapper message) throws InvalidNetworkMessage {
        super(message);
        this.dto = this.from(PlayerDto.class);
    }

    @Override
    public int command() {
        return ServerCommand.PLAYER_INFO.value;
    }

    public PlayerDto getPlayerDto() {
        return dto;
    }
}
