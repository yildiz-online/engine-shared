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

import be.yildiz.common.id.PlayerId;
import be.yildiz.module.network.exceptions.InvalidNetworkMessage;
import be.yildiz.module.network.protocol.MessageWrapper;
import be.yildiz.module.network.protocol.NetworkMessage;
import be.yildiz.module.network.protocol.ServerResponse;
import be.yildiz.shared.player.PlayerStatus;
import lombok.Getter;

/**
 * Response from the server with a player data.
 *
 * @author Grégory Van den Borre
 */
public final class PlayerInfoResponse extends NetworkMessage implements ServerResponse {

    /**
     * Player unique Id.
     */
    @Getter
    private final PlayerId player;

    /**
     * Player name.
     */
    @Getter
    private final String login;

    /**
     * Player status for the current player.
     */
    @Getter
    private final PlayerStatus status;

    /**
     * Full constructor.
     *
     * @param playerId     Player Id.
     * @param playerName   Player's name.
     * @param playerStatus Player's status.
     */
    public PlayerInfoResponse(final PlayerId playerId, final String playerName, final PlayerStatus playerStatus) {
        super(NetworkMessage.convertParams(playerId, playerName, Integer.valueOf(playerStatus.ordinal())));
        this.player = playerId;
        this.login = playerName;
        this.status = playerStatus;
    }

    /**
     * Full constructor.
     *
     * @param message Message from the server to parse.
     * @throws InvalidNetworkMessage If an error occurs while parsing the message.
     */
    public PlayerInfoResponse(final MessageWrapper message) throws InvalidNetworkMessage {
        super(message);
        this.player = this.getPlayerId();
        this.login = this.getString();
        this.status = PlayerStatus.getFromOrdinal(this.getInt());
    }

    @Override
    public int command() {
        return ServerCommand.PLAYER_INFO.value;
    }
}
