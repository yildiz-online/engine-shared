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

import be.yildiz.shared.entity.module.ModuleConfiguration;
import be.yildizgames.common.mapping.MappingException;
import be.yildizgames.common.mapping.ObjectMapper;
import be.yildizgames.common.mapping.PlayerIdMapper;
import be.yildizgames.common.mapping.Separator;

/**
 * @author Grégory Van den Borre
 */
public class ModuleConfigurationMapper implements ObjectMapper<ModuleConfiguration> {

    private static final ModuleConfigurationMapper INSTANCE = new ModuleConfigurationMapper();

    private ModuleConfigurationMapper() {
        super();
    }

    public static ModuleConfigurationMapper getInstance() {
        return INSTANCE;
    }

    @Override
    public ModuleConfiguration from(String s) throws MappingException {
        assert s != null;
        String[] v = s.split(Separator.OBJECTS_SEPARATOR);
        try {
            return new ModuleConfiguration(
                    v[0],
                    PlayerIdMapper.getInstance().from(v[1]),
                    EntityTypeMapper.getInstance().from(v[2]),
                    ModuleGroupMapper.getInstance().from(v[3])
            );
        } catch (IndexOutOfBoundsException e) {
            throw new MappingException(e);
        }
    }

    @Override
    public String to(ModuleConfiguration m) {
        assert m != null;
        return m.getName()
                + Separator.OBJECTS_SEPARATOR
                + PlayerIdMapper.getInstance().to(m.getPlayer())
                + Separator.OBJECTS_SEPARATOR
                + EntityTypeMapper.getInstance().to(m.getType())
                + Separator.OBJECTS_SEPARATOR
                + ModuleGroupMapper.getInstance().to(m.getModules());
    }
}
