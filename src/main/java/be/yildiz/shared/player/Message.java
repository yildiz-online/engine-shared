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

package be.yildiz.shared.player;

import be.yildiz.common.id.PlayerId;

import java.util.Date;

/**
 * Message sent from a player to another.
 *
 * @author Grégory Van den Borre
 */
public final class Message {

    /**
     * To replace comma in the message.
     */
    private static final String COMMA = "%C%";

    /**
     * Separator for the message components.
     */
    private static final String SEPARATOR = "%§%";

    /**
     * Id of the player sending the message.
     */
    private final PlayerId sender;

    /**
     * Id of the player receiving the message.
     */
    private final PlayerId receiver;

    /**
     * Message content.
     */
    private final String content;

    /**
     * Message date.
     */
    private final Date date;

    /**
     * <code>true</code> if the message has been read, <code>false</code> otherwise.
     */
    private boolean read;

    /**
     * Create a new message.
     *
     * @param sender   Message sender.
     * @param receiver Message receiver.
     * @param message  Message.
     * @param date     Message send date.
     * @param read     Message read status.
     */
    public Message(final PlayerId sender, final PlayerId receiver, final String message, final Date date, final boolean read) {
        super();
        assert sender != null;
        assert receiver != null;
        assert message != null;
        assert date != null;
        this.sender = sender;
        this.receiver = receiver;
        this.content = message.replaceAll(SEPARATOR, "").replaceAll(",", COMMA);
        this.date = date;
        this.read = read;
    }

    /**
     * Create a message from a String to parse.
     *
     * @param toParse String to parse to create the message.
     */
    public Message(final String toParse) {
        super();
        String[] s = toParse.split(SEPARATOR);
        this.sender = PlayerId.valueOf(Integer.parseInt(s[0].trim()));
        this.receiver = PlayerId.valueOf(Integer.parseInt(s[1].trim()));
        this.content = s[2].trim().replace(SEPARATOR, "").replace(",", COMMA);
        this.date = new Date(Long.parseLong(s[3].trim()));
        this.read = Boolean.parseBoolean(s[4].trim());
    }

    /**
     * @return The message content.
     */
    public String getMessage() {
        return this.content.replaceAll(COMMA, ",");
    }

    public PlayerId getSender() {
        return sender;
    }

    public PlayerId getReceiver() {
        return receiver;
    }

    public Date getDate() {
        return date;
    }

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((this.date == null) ? 0 : this.date.hashCode());
        result = prime * result + ((this.content == null) ? 0 : this.content.hashCode());
        result = prime * result + (this.read ? 1231 : 1237);
        result = prime * result + ((this.receiver == null) ? 0 : this.receiver.hashCode());
        result = prime * result + ((this.sender == null) ? 0 : this.sender.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Message)) {
            return false;
        }
        Message other = (Message) obj;
        if (!this.date.equals(other.date)) {
            return false;
        }
        if (!this.content.equals(other.content)) {
            return false;
        }
        if (this.read != other.read) {
            return false;
        }
        if (!this.receiver.equals(other.receiver)) {
            return false;
        }
        return this.sender.equals(other.sender);
    }

    /**
     * @return A formated message to be parsed.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.sender);
        sb.append(SEPARATOR);
        sb.append(this.receiver);
        sb.append(SEPARATOR);
        sb.append(this.content);
        sb.append(SEPARATOR);
        sb.append(this.date.getTime());
        sb.append(SEPARATOR);
        sb.append(this.read);
        return sb.toString();
    }
}
