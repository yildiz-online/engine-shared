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

package be.yildiz.shared.mission.task;

import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

/**
 * @author Grégory Van den Borre
 */
@RunWith(Enclosed.class)
public class TaskTypeTest {

    /**
     * Different values can have same hc, so not tested.
     */
    public static class Hashcode {

        @Test
        public void sameValues() {
            int i1 = new TaskType("ok").hashCode();
            int i2 = new TaskType("ok").hashCode();
            Assert.assertEquals(i1, i2);
        }
    }

    public static class Equals {

        @Test
        public void sameValues() {
            Assert.assertEquals(new TaskType("ok"), new TaskType("ok"));
        }

        @Test
        public void differentValue() {
            Assert.assertNotEquals(new TaskType("ok"), new TaskType("nok"));
        }

        @Test
        public void nullValue() {
            Assert.assertNotEquals(new TaskType("ok"), null);
        }

        @Test
        public void differentClass() {
            Assert.assertNotEquals(new TaskType("ok"), "ok");
        }

    }
}
