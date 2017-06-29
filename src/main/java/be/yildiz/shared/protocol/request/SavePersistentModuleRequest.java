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

import be.yildiz.module.network.exceptions.InvalidNetworkMessage;
import be.yildiz.module.network.protocol.MessageWrapper;
import be.yildiz.module.network.protocol.NetworkMessage;
import be.yildiz.shared.entity.module.ModuleConfiguration;
import be.yildiz.shared.protocol.mapper.ModuleConfigurationMapper;

/**
 * @author Grégory Van den Borre
 */
public final class SavePersistentModuleRequest extends NetworkMessage implements ServerRequest {

    static {
        ModuleConfigurationMapper.getInstance();
    }

    private final ModuleConfiguration moduleConfiguration;

    /**
     * Full Constructor. Create the object and prepare it to be sent.
     * @param config Configuration to persist.
     */
    public SavePersistentModuleRequest(final ModuleConfiguration config) {
        super(NetworkMessage.to(config, ModuleConfiguration.class));
        this.moduleConfiguration = config;
    }

    /**
     * Full Constructor. Create the object from a message received.
     *
     * @param message Message to parse to build the object.
     * @throws InvalidNetworkMessage If the message cannot be correctly parsed.
     */
    public SavePersistentModuleRequest(final MessageWrapper message) throws InvalidNetworkMessage {
        super(message);
        this.moduleConfiguration = this.from(ModuleConfiguration.class);
    }

    @Override
    public int command() {
        return ClientCommand.SAVE_MODULE_CONFIG.ordinal();
    }

    public ModuleConfiguration getModuleConfiguration() {
        return moduleConfiguration;
    }
}
