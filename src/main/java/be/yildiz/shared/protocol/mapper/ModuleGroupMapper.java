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
import be.yildiz.shared.entity.module.ModuleGroup;

/**
 * @author Grégory Van den Borre
 */
public class ModuleGroupMapper implements ObjectMapper<ModuleGroup> {

    private static final ModuleGroupMapper INSTANCE = new ModuleGroupMapper();

    private ModuleGroupMapper() {
        super();
    }

    public static ModuleGroupMapper getInstance() {
        return INSTANCE;
    }

    @Override
    public ModuleGroup from(String s) throws InvalidNetworkMessage {
        String[] v = s.split(MessageSeparation.VAR_SEPARATOR);
        try {
            return new ModuleGroup.ModuleGroupBuilder()
                    .withHull(ActionIdMapper.getInstance().from(v[0]))
                    .withEnergy(ActionIdMapper.getInstance().from(v[1]))
                    .withMove(ActionIdMapper.getInstance().from(v[2]))
                    .withInteraction(ActionIdMapper.getInstance().from(v[3]))
                    .withDetector(ActionIdMapper.getInstance().from(v[4]))
                    .withAdditional1(ActionIdMapper.getInstance().from(v[5]))
                    .withAdditional2(ActionIdMapper.getInstance().from(v[6]))
                    .withAdditional3(ActionIdMapper.getInstance().from(v[7]))
                    .build();
        } catch (IndexOutOfBoundsException e) {
            throw new InvalidNetworkMessage(e);
        }
    }

    @Override
    public String to(ModuleGroup moduleGroup) {
        return ActionIdMapper.getInstance().to(moduleGroup.getHull())
                + MessageSeparation.VAR_SEPARATOR
                + ActionIdMapper.getInstance().to(moduleGroup.getEnergy())
                + MessageSeparation.VAR_SEPARATOR
                + ActionIdMapper.getInstance().to(moduleGroup.getMove())
                + MessageSeparation.VAR_SEPARATOR
                + ActionIdMapper.getInstance().to(moduleGroup.getInteraction())
                + MessageSeparation.VAR_SEPARATOR
                + ActionIdMapper.getInstance().to(moduleGroup.getDetector())
                + MessageSeparation.VAR_SEPARATOR
                + ActionIdMapper.getInstance().to(moduleGroup.getAdditional1())
                + MessageSeparation.VAR_SEPARATOR
                + ActionIdMapper.getInstance().to(moduleGroup.getAdditional2())
                + MessageSeparation.VAR_SEPARATOR
                + ActionIdMapper.getInstance().to(moduleGroup.getAdditional3());
    }
}
