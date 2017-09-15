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

package be.yildiz.shared.player;

import be.yildiz.common.id.EntityId;
import be.yildiz.shared.resources.ResourceValue;
import be.yildiz.shared.resources.ResourcesProducer;
import be.yildiz.shared.resources.bonus.BonusResources;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Grégory Van den Borre
 */
final class ResourcesProducerTest {

    @Test
    void testResourcesProducer() throws InterruptedException {
        final float MAX = 5.0f;
        long time = System.currentTimeMillis();
        ResourceValue resources = new ResourceValue(new float[]{0.0f});
        ResourcesProducer producer = new ResourcesProducer(EntityId.WORLD, time, resources);
        producer.addBonus(new DummyMaxResources(MAX));
        producer.addBonus(new DummyRatio());
        producer.setInitialised();
        assertEquals(0.0f, producer.getResource(0), 0.1f);
        Thread.sleep(3000);
        assertEquals(3.0f, producer.getResource(0), 0.1f);
        Thread.sleep(3000);
        assertEquals(MAX, producer.getResource(0), 0.1f);
        producer.addBonus(new DummyMaxResources(10));
        assertEquals(5.0f, producer.getResource(0), 0.1f);
        Thread.sleep(1000);
        assertEquals(6.0f, producer.getResource(0), 0.1f);
    }

    @Test
    void testAddBonusLimit() throws InterruptedException {
        ResourceValue resources = new ResourceValue(new float[]{0f});
        ResourcesProducer producer = new ResourcesProducer(EntityId.WORLD, 10, resources);
        producer.addBonus(new DummyMaxResources(5));
        producer.setInitialised();
        assertEquals(5, producer.getMax(0), 0.001f);
        producer.addBonus(new DummyMaxResources(10));
        assertEquals(10, producer.getMax(0), 0.001f);

    }

    @Test
    void testResourcesProducerSetNewValues() {
        final float MAX = 5.0f;
        long time = System.currentTimeMillis();
        ResourceValue resources = new ResourceValue(new float[]{0.0f});
        ResourcesProducer producer = new ResourcesProducer(EntityId.WORLD, time, resources);
        producer.setNewValues(time, new ResourceValue(new float[]{10.0f}));
        assertEquals(10.0f, producer.getResource(0), 0.1f);
        producer.addBonus(new DummyMaxResources(MAX));
        producer.addBonus(new DummyRatio());
        producer.setInitialised();
        assertEquals(MAX, producer.getResource(0), 0.1f);
    }

    @Test
    void testAddBonusRatio() {
        ResourceValue resources = new ResourceValue(new float[]{0.0f});
        ResourcesProducer producer = new ResourcesProducer(EntityId.WORLD, System.currentTimeMillis(), resources);
        assertEquals(0.0f, producer.getRatios(0), 0.001);
        producer.addBonus(new DummyRatio());
        assertEquals(1.0f, producer.getRatios(0), 0.001);
        producer.addBonus(new DummyRatio());
        assertEquals(2.0f, producer.getRatios(0), 0.001);
    }

    @Test
    void testSetRatio() {
    }

    @Test
    void testGetResources() {
    }

    @Test
    void testGetRatios() {
    }

    @Test
    void testGetLastUpdate() {
    }

    @Test
    void testToString() {
    }

    @Test
    void testGetResourcesArray() {
    }

    @Test
    void testSetMax() {
    }

    @Test
    void testSetRatioValue() {
    }

    @Test
    void testGetMax() {
    }

    private static final class DummyMaxResources extends BonusResources {

        DummyMaxResources(float max) {
            super(new float[]{0}, new float[]{max}, 5);
        }
    }

    private static final class DummyRatio extends BonusResources {

        DummyRatio() {
            super(new float[]{1}, new float[]{0});
        }
    }

}
