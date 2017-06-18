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

import be.yildiz.common.id.ActionId;
import be.yildiz.common.id.EntityId;
import be.yildiz.common.vector.Point3D;
import be.yildiz.module.network.exceptions.InvalidNetworkMessage;
import be.yildiz.module.network.protocol.MessageWrapper;
import be.yildiz.module.network.protocol.ServerRequest;
import be.yildiz.shared.entity.action.Action;
import be.yildiz.shared.protocol.BaseNetworkMessage;

/**
 * @author Grégory Van den Borre
 */
public final class ActionRequest extends BaseNetworkMessage implements ServerRequest {

    public final EntityId entityId;

    /**
     * Module executing the action.
     */
    public final ActionId moduleId;

    public final Point3D destination;

    public final EntityId targetId;

    /**
     * Full constructor.
     *
     * @param message Message from the server to parse.
     * @throws InvalidNetworkMessage If an error occurs while parsing the message.
     */
    public ActionRequest(final MessageWrapper message) throws InvalidNetworkMessage {
        super(message);
        this.entityId = this.to(EntityId.class);
        this.moduleId = this.to(ActionId.class);
        this.destination = this.to(Point3D.class);
        this.targetId = this.to(EntityId.class);
    }

    /**
     * Full constructor.
     * @param e Entity id.
     * @param a Action to request.
     */
    public ActionRequest(final EntityId e, final Action a) {
        super(check(e, a));
        this.entityId = e;
        this.moduleId = a.id;
        this.destination = a.getDestination();
        this.targetId = a.getTargetId();
    }

    @Override
    public int command() {
        return ClientCommand.ACTION.ordinal();
    }

    private static String[] check(EntityId e, Action a) {
        assert e != null;
        assert a != null;
        return new String[] {
                BaseNetworkMessage.from(e),
                BaseNetworkMessage.from(a.id),
                BaseNetworkMessage.from(a.getDestination()),
                BaseNetworkMessage.from(a.getTargetId())};
    }
}
