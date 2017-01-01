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
import org.junit.Assert;
import org.junit.Test;

/**
 * @author Grégory Van den Borre
 */
public final class ResourcesProducerTest {

    @Test
    public void testResourcesProducer() throws InterruptedException {
        final float MAX = 5.0f;
        long time = System.currentTimeMillis();
        ResourceValue resources = new ResourceValue(new float[]{0.0f});
        ResourcesProducer producer = new ResourcesProducer(EntityId.WORLD, time, resources);
        producer.addBonus(new DummyMaxResources(MAX));
        producer.addBonus(new DummyRatio());
        producer.setInitialised();
        Assert.assertEquals(0.0f, producer.getResource(0), 0.1f);
        Thread.sleep(3000);
        Assert.assertEquals(3.0f, producer.getResource(0), 0.1f);
        Thread.sleep(3000);
        Assert.assertEquals(MAX, producer.getResource(0), 0.1f);
        producer.addBonus(new DummyMaxResources(10));
        Assert.assertEquals(5.0f, producer.getResource(0), 0.1f);
        Thread.sleep(1000);
        Assert.assertEquals(6.0f, producer.getResource(0), 0.1f);
    }

    @Test
    public void testAddBonusLimit() throws InterruptedException {
        ResourceValue resources = new ResourceValue(new float[]{0f});
        ResourcesProducer producer = new ResourcesProducer(EntityId.WORLD, 10, resources);
        producer.addBonus(new DummyMaxResources(5));
        producer.setInitialised();
        Assert.assertEquals(5, producer.getMax(0), 0.001f);
        producer.addBonus(new DummyMaxResources(10));
        Assert.assertEquals(10, producer.getMax(0), 0.001f);

    }

    @Test
    public void testResourcesProducerSetNewValues() {
        final float MAX = 5.0f;
        long time = System.currentTimeMillis();
        ResourceValue resources = new ResourceValue(new float[]{0.0f});
        ResourcesProducer producer = new ResourcesProducer(EntityId.WORLD, time, resources);
        producer.setNewValues(time, new ResourceValue(new float[]{10.0f}));
        Assert.assertEquals(10.0f, producer.getResource(0), 0.1f);
        producer.addBonus(new DummyMaxResources(MAX));
        producer.addBonus(new DummyRatio());
        producer.setInitialised();
        Assert.assertEquals(MAX, producer.getResource(0), 0.1f);
    }

    @Test
    public void testAddBonusRatio() {
        ResourceValue resources = new ResourceValue(new float[]{0.0f});
        ResourcesProducer producer = new ResourcesProducer(EntityId.WORLD, System.currentTimeMillis(), resources);
        Assert.assertEquals(0.0f, producer.getRatios(0), 0.001);
        producer.addBonus(new DummyRatio());
        Assert.assertEquals(1.0f, producer.getRatios(0), 0.001);
        producer.addBonus(new DummyRatio());
        Assert.assertEquals(2.0f, producer.getRatios(0), 0.001);
    }

    @Test
    public void testSetRatio() {
    }

    @Test
    public void testGetResources() {
    }

    @Test
    public void testGetRatios() {
    }

    @Test
    public void testGetLastUpdate() {
    }

    @Test
    public void testToString() {
    }

    @Test
    public void testGetResourcesArray() {
    }

    @Test
    public void testSetMax() {
    }

    @Test
    public void testSetRatioValue() {
    }

    @Test
    public void testGetMax() {
    }

    private static final class DummyMaxResources extends BonusResources {

        public DummyMaxResources(float max) {
            super(new float[]{0}, new float[]{max}, 5);
        }
    }

    private static final class DummyRatio extends BonusResources {

        public DummyRatio() {
            super(new float[]{1}, new float[]{0});
        }
    }

}
