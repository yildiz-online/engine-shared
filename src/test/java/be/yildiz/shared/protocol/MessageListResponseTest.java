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

package be.yildiz.shared.protocol;

import be.yildiz.common.collections.Lists;
import be.yildiz.common.id.PlayerId;
import be.yildiz.module.network.exceptions.InvalidNetworkMessage;
import be.yildiz.module.network.protocol.MessageWrapper;
import be.yildiz.shared.player.Message;
import be.yildiz.shared.protocol.response.MessageListResponse;
import org.junit.Assert;
import org.junit.Test;

import java.util.Date;
import java.util.List;

/**
 * @author Grégory Van den Borre
 */
public final class MessageListResponseTest {

    @Test
    public void testConstructor() throws InvalidNetworkMessage {
        Date d1 = new Date(System.currentTimeMillis());
        Date d2 = new Date(System.currentTimeMillis() - 10000);
        Message m1 = new Message(PlayerId.get(2), PlayerId.get(3), "a simple test", d1, true);
        Message m2 = new Message(PlayerId.get(5), PlayerId.get(6), "another, simple test", d2, false);
        List<Message> l1 = Lists.newList();
        l1.add(m1);
        l1.add(m2);
        MessageListResponse r = new MessageListResponse(l1);
        MessageListResponse r2 = new MessageListResponse(new MessageWrapper(r.buildMessage()));
        for (int i = 0; i < 2; i++) {
            Assert.assertEquals(r.getMessageList().get(i).getMessage(), r2.getMessageList().get(i).getMessage());
        }
    }

}
