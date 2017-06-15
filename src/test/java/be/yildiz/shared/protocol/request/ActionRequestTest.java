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
import be.yildiz.module.network.protocol.NetworkMessage;
import be.yildiz.shared.entity.action.Action;
import be.yildiz.shared.entity.fields.Target;
import be.yildiz.shared.protocol.mapper.ActionIdMapper;
import be.yildiz.shared.protocol.mapper.EntityIdMapper;
import be.yildiz.shared.protocol.mapper.Point3DMapper;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

/**
 * @author Grégory Van den Borre
 */
@RunWith(Enclosed.class)
public class ActionRequestTest {

    @BeforeClass
    public static void before() {
        NetworkMessage.registerMapper(EntityId.class, new EntityIdMapper());
        NetworkMessage.registerMapper(ActionId.class, new ActionIdMapper());
        NetworkMessage.registerMapper(Point3D.class, new Point3DMapper());
    }

    public static class Constructor {

        @Test
        public void happyFlow() {
            EntityId id = EntityId.get(5L);
            Point3D dest = Point3D.xyz(10,15,20);
            EntityId target = EntityId.get(6L);
            ActionId actionId = ActionId.get(8);
            Action a = givenAnAction(actionId, id, dest, target);

            ActionRequest request = new ActionRequest(id, a);

            Assert.assertEquals(id, request.entityId);
            Assert.assertEquals(dest, request.destination);
            Assert.assertEquals(target, request.targetId);
            Assert.assertEquals(actionId, request.moduleId);
            Assert.assertEquals(ClientCommand.ACTION.ordinal(), request.command());
            Assert.assertEquals("&13_5_8_10.0,15.0,20.0_6#", request.buildMessage());
        }

        @Test
        public void messageWrapper() throws InvalidNetworkMessage {
            EntityId id = EntityId.get(5L);
            Point3D dest = Point3D.xyz(10,15,20);
            EntityId target = EntityId.get(6L);
            ActionId actionId = ActionId.get(8);

            ActionRequest request = new ActionRequest(new MessageWrapper("13_5_8_10.0,15.0,20.0_6"));
            Assert.assertEquals(id, request.entityId);
            Assert.assertEquals(dest, request.destination);
            Assert.assertEquals(target, request.targetId);
            Assert.assertEquals(actionId, request.moduleId);
            Assert.assertEquals(ClientCommand.ACTION.ordinal(), request.command());
        }

    }

    private static Action givenAnAction(ActionId id, EntityId entity, Point3D dest, EntityId target) {
        return new Action(id, entity, false) {
            @Override
            protected void runImpl(long time) {

            }

            @Override
            public boolean checkPrerequisite() {
                return false;
            }

            @Override
            protected void initImpl() {

            }

            @Override
            public Point3D getDestination() {
                return dest;
            }

            @Override
            public void setDestination(Point3D destination) {

            }

            @Override
            public void setTarget(Target entity) {

            }

            @Override
            public EntityId getTargetId() {
                return target;
            }

            @Override
            protected void stopImpl() {

            }

            @Override
            public void delete() {

            }
        };
    }

}
