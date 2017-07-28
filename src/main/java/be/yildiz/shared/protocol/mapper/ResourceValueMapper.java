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
import be.yildiz.module.network.protocol.mapper.ObjectMapper;
import be.yildiz.shared.resources.ResourceValue;

/**
 * @author Grégory Van den Borre
 */
public class ResourceValueMapper implements ObjectMapper<ResourceValue> {

    private static final ResourceValueMapper INSTANCE = new ResourceValueMapper();

    private ResourceValueMapper() {
        super();
    }

    public static ResourceValueMapper getInstance() {
        return INSTANCE;
    }

    @Override
    public ResourceValue from(String s) throws InvalidNetworkMessage {
        assert s != null;
        try {
            String[] v = s.split(MessageSeparation.VAR_SEPARATOR);
            float[] f = new float[v.length];
            for (int i = 0; i < v.length; i++) {
                f[i] = Float.valueOf(v[i]);
            }
            return new ResourceValue(f);
        } catch (IndexOutOfBoundsException | NumberFormatException e) {
            throw new InvalidNetworkMessage(e);
        }
    }

    @Override
    public String to(ResourceValue value) {
        assert value != null;
        StringBuilder sb = new StringBuilder();
        for(float f : value.getArray()) {
            sb.append(f);
            sb.append(MessageSeparation.VAR_SEPARATOR);
        }
        String result = sb.toString();

        return result.substring(0, result.length() - 1);
    }
}
