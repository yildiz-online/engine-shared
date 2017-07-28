/*
 * This file is part of the Yildiz-Engine project, licenced under the MIT License  (MIT)
 *
 *  Copyright (c) 2017 Grégory Van den Borre
 *
 *  More infos available: https://www.yildiz-games.be
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 *  documentation files (the "Software"), to deal in the Software without restriction, including without
 *  limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 *  of the Software, and to permit persons to whom the Software is furnished to do so,
 *  subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be included in all copies or substantial
 *  portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 *  WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS
 *  OR COPYRIGHT  HOLDERS BE LIABLE FOR ANY CLAIM,
 *  DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE  SOFTWARE.
 *
 */

package be.yildiz.shared.protocol.mapper;

import be.yildiz.common.id.EntityId;
import be.yildiz.module.network.protocol.mapper.BaseMapperTest;
import be.yildiz.shared.building.staff.Staff;
import be.yildiz.shared.data.BuildingPosition;
import be.yildiz.shared.data.EntityType;
import be.yildiz.shared.data.Level;
import be.yildiz.shared.protocol.BuildingConstructionDto;
import org.junit.BeforeClass;

/**
 * @author Grégory Van den Borre
 */
public class BuildingConstructionDtoMapperTest extends BaseMapperTest<BuildingConstructionDto> {

    @BeforeClass
    public static void init() {
        new EntityType(143, "test");
    }

    public BuildingConstructionDtoMapperTest() {
        super(new BuildingConstructionDtoMapper(),
                new BuildingConstructionDto(
                        EntityId.valueOf(3),
                        EntityType.valueOf(143),
                        Level.valueOf(1),
                        BuildingPosition.valueOf(5),
                        Staff.valueOf(2)
                ));
    }
}