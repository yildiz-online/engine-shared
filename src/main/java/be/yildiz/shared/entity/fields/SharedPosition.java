//        This file is part of the Yildiz-Online project, licenced under the MIT License
//        (MIT)
//
//        Copyright (c) 2016 Grégory Van den Borre
//
//        More infos available: http://yildiz.bitbucket.org
//
//        Permission is hereby granted, free of charge, to any person obtaining a copy
//        of this software and associated documentation files (the "Software"), to deal
//        in the Software without restriction, including without limitation the rights
//        to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
//        copies of the Software, and to permit persons to whom the Software is
//        furnished to do so, subject to the following conditions:
//
//        The above copyright notice and this permission notice shall be included in all
//        copies or substantial portions of the Software.
//
//        THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
//        IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
//        FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
//        AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
//        LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
//        OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
//        SOFTWARE.

package be.yildiz.shared.entity.fields;

import be.yildiz.common.vector.Point3D;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Grégory Van den Borre
 */
@Getter
@Setter
public class SharedPosition implements PositionData {

    private Point3D position = Point3D.ZERO;

    private Point3D direction = Point3D.INVERT_Z;

    public void lookAt(final Point3D destination) {
        this.setDirection(destination.subtract(this.position));
    }

    public void translate(final float distance) {
        this.position = this.position.add(Point3D.normalize(this.direction).multiply(distance));
    }

    @Override
    public float squaredDistance(Point3D other) {
        return Point3D.squaredDistance(this.position, other);
    }
}
