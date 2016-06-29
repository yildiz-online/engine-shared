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

import be.yildiz.common.BoundedValue;
import lombok.Getter;

/**
 * @author Grégory Van den Borre
 */
public class MutableHitPoints {

    //FIXME DELETE, this should be in the appropriate module(hull), not shared, and data retrieved trough getter

    @Getter
    private final BoundedValue hp = new BoundedValue();

    public void setMax(int max) {
        this.hp.setMax(max);
    }

    public void setValue(int value) {
        this.hp.setValue(value);
    }

    public void add(int i) {
        this.hp.add(i);
    }

    public boolean isMoreThan(int cost) {
        return this.hp.isMoreThan(cost);
    }

    public boolean isZero() {
        return this.hp.isZero();
    }
}
