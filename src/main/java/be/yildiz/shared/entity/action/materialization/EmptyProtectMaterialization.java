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

package be.yildiz.shared.entity.action.materialization;

import be.yildiz.common.gameobject.GameMaterialization;
import be.yildiz.common.gameobject.Movable;
import be.yildiz.common.id.EntityId;
import be.yildiz.common.vector.Point3D;

/**
 * @author Grégory Van den Borre
 */
public class EmptyProtectMaterialization implements ProtectMaterialization{

    private final EntityId id;

    public EmptyProtectMaterialization(EntityId id) {
        super();
        this.id = id;
    }
    @Override
    public void destroy() {

    }

    @Override
    public GameMaterialization getObject() {
        return new GameMaterialization() {
            @Override
            public EntityId getId() {
                return id;
            }

            @Override
            public void rotate(float x, float y, float z, float w) {

            }

            @Override
            public Point3D getScaleSize() {
                return Point3D.valueOf(1);
            }

            @Override
            public void scale(float x, float y, float z) {

            }

            @Override
            public void delete() {

            }

            @Override
            public void sleep(boolean b) {

            }

            @Override
            public void attachTo(Movable other) {

            }

            @Override
            public void detach(Movable other) {

            }

            @Override
            public void addChild(Movable other) {

            }

            @Override
            public void attachToOptional(Movable other) {

            }

            @Override
            public Point3D getPosition() {
                return Point3D.ZERO;
            }

            @Override
            public void setPosition(Point3D newPosition) {

            }

            @Override
            public Point3D getAbsolutePosition() {
                return Point3D.ZERO;
            }

            @Override
            public void setAbsolutePosition(Point3D pos) {

            }

            @Override
            public Point3D getDirection() {
                return Point3D.BASE_DIRECTION;
            }

            @Override
            public void setDirection(Point3D newDirection) {

            }

            @Override
            public Point3D getAbsoluteDirection() {
                return Point3D.BASE_DIRECTION;
            }
        };
    }
}
