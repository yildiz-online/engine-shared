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

package be.yildiz.shared.resources;

import be.yildiz.common.collections.Sets;
import be.yildiz.common.id.EntityId;
import be.yildiz.common.log.Logger;
import be.yildiz.shared.resources.bonus.BonusListener;
import be.yildiz.shared.resources.bonus.BonusResources;
import lombok.Getter;

import java.util.Date;
import java.util.Set;

/**
 * Contains the resources values and the ratio to compute them.
 *
 * @author Grégory Van den Borre
 */
public final class ResourcesProducer {

    /**
     * Contains the ratio values, the array must math the resource array.
     */
    private final ResourceRatio ratio;

    /**
     * Contains the last computed resource values.
     */
    private final ResourceValue resources;
    /**
     * Limit to stop the production, it will not exceed this values.
     */
    private final ResourceLimit limit;
    /**
     * List of associated bonus.
     */
    private final Set<BonusListener> bonusListenerList = Sets.newSet();
    private final Set<BonusResources> bonus = Sets.newSet();
    @Getter
    private final EntityId city;
    /**
     * Time when the resources were computed for the last time.
     */
    @Getter
    private long lastUpdate;
    /**
     * When the producer is built, it has no bonus, if resource value is recomputed, it would always compute it as 0(limit is 0 an ratio is 0).
     */
    private boolean initialized = false;

    /**
     * Full constructor.
     *
     * @param player    Associated player.
     * @param time      Time when the resources were updated for the last time.
     * @param resources Contains the last computed resource values.
     * @pre player != null
     * @pre time > 0
     * @pre resources != null
     * @post this.lastUpdate = time
     * @post this.resources == resources
     * @post this.initialized == false
     */
    public ResourcesProducer(final EntityId cityId, final long time, final ResourceValue resources) {
        super();
        this.city = cityId;
        this.lastUpdate = time;
        int size = resources.getArray().length;
        this.ratio = new ResourceRatio(new float[size]);
        this.resources = resources;
        this.limit = new ResourceLimit(new float[size]);
        assert this.invariant();
    }

    /**
     * Must only be called once all bonus have been applied to avoid being limited to 0.
     *
     * @post this.initialized == true
     */
    public void setInitialised() {
        this.initialized = true;
        assert this.invariant();
    }

    /**
     * Add a bonus to the production limit or ratio to increase it. As resources are not recomputed, if it is needed, call getResource to update values.
     *
     * @param bonus To add to this production limit or ratio.
     * @requires bonus != null
     * @ensures this.bonus.size() == (@pre this.bonus.size()+1)
     */
    public void addBonus(final BonusResources bonus) {
        this.getResources();
        this.limit.addBonus(bonus);
        this.ratio.addBonus(bonus);
        this.bonus.remove(bonus);
        this.bonus.add(bonus);
        this.bonusListenerList.forEach(l -> l.bonusAdded(bonus));
        assert this.invariant();
    }

    /**
     * Remove a bonus to the production generation speed, a limit bonus cannot be removed.
     *
     * @param bonus To remove from this production generation speed.
     */
    public void removeBonus(final BonusResources bonus) {
        this.getResources();
        this.ratio.removeBonus(bonus);
        this.bonus.remove(bonus);
        this.bonusListenerList.forEach(l -> l.bonusRemoved(bonus));
        assert this.invariant();
    }

    /**
     * Update the producer values.
     *
     * @param time              Time since last computing.
     * @param resourceFullValue Value at the last time computed.
     */
    public void setNewValues(final long time, final ResourceValue resourceValue) {
        this.lastUpdate = time;
        this.resources.setValues(resourceValue);
        assert this.invariant();
    }

    /**
     * Compute and return the resource values, computation is time elapsed * ratio.
     *
     * @return The current resource values.
     */
    public ResourceValue getResources() {
        this.updateResources();
        return this.resources;
    }

    /**
     * Give a ratio.
     *
     * @param position Resource position.
     * @return The ratio value for the given resource.
     */
    public float getRatios(final int position) {
        return this.ratio.getValues(position);
    }

    /**
     * Return an up to date(recomputed now) resource value.
     *
     * @param position Resource position.
     * @return The current resource amount at the given position.
     */
    public float getResource(final int position) {
        ResourceValue value = this.getResources();
        return value.getValue(position);
    }

    /**
     * Get the max value.
     *
     * @param position Resource position.
     * @return The max value for the given resource.
     */
    public float getMax(final int position) {
        return this.limit.getLimits(position);
    }

    /**
     * Steal resource.
     *
     * @param toRemove Amount of resource to steal.
     * @return The amount of resource stolen.
     */
    public ResourceValue steal(final ResourceValue toRemove) {
        this.updateResources();
        float[] resourcesToRemove = toRemove.getArray();
        float[] resourcesValues = this.resources.getArray();
        float[] stolenValues = new float[resourcesValues.length];
        for (int i = 0; i < resourcesToRemove.length; i++) {
            if (resourcesValues[i] >= resourcesToRemove[i]) {
                stolenValues[i] = resourcesToRemove[i];
                resourcesValues[i] -= resourcesToRemove[i];
            } else {
                stolenValues[i] = resourcesValues[i];
                resourcesValues[i] = 0.0f;
            }
        }
        return new ResourceValue(stolenValues);
    }

    /**
     * Add resource to this one.
     *
     * @param toAdd Amount of resource to add.
     */
    public void add(final ResourceValue toAdd) {
        this.updateResources();
        this.resources.add(toAdd, this.limit);
        assert this.invariant();
    }

    /**
     * Add a new bonus listener, if already in the list, it will not be added.
     *
     * @param bl Listener to add.
     * @requires bl != null
     * @ensure if(list!contains(bl)) list.size = pre.list.size + 1
     */
    public void addBonusListener(final BonusListener bl) {
        this.bonusListenerList.add(bl);
        for (BonusResources m : this.bonus) {
            for (BonusListener l : this.bonusListenerList) {
                l.bonusAdded(m);
            }
        }
    }

    /**
     * Recompute the resources.
     */
    private void updateResources() {
        if (this.initialized) {
            final long current = System.currentTimeMillis();
            final long delta = current - this.lastUpdate;
            this.lastUpdate = current;
            this.resources.add(this.ratio, delta, this.limit);
        }
        assert this.invariant();
    }

    /**
     * Buying logic, check if resources are enough to pay the parameter price, it is the case, price is removed from the resources amount and the method return <code>true</code>, else, nothing is done
     * and the method returns <code>false</code>.
     *
     * @param price Amount of resources to remove.
     * @return <code>true</code> if the city had enough resources to pay the price and the transaction has been done, <code>false</code> otherwise.
     */
    public boolean buy(final ResourceValue price) {
        return this.resources.buy(price);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder("Resources producer:");
        builder.append(this.resources);
        builder.append("last update:");
        builder.append(new Date(this.lastUpdate));
        builder.append(this.ratio);
        builder.append(this.limit);
        return builder.toString();
    }

    /**
     * Invariant for this class.
     *
     * @return <code>true</code> if invariant is maintained, <code>false</code> otherwise.
     */
    private boolean invariant() {
        if (this.lastUpdate <= 0) {
            Logger.warning("INVARIANT FAIL: lastUpdate value: " + this.lastUpdate);
            return false;
        }
        if (this.limit == null) {
            Logger.warning("INVARIANT FAIL: limit is null");
            return false;
        }
        if (this.ratio == null) {
            Logger.warning("INVARIANT FAIL: ratio is null");
            return false;
        }
        if (this.resources == null) {
            Logger.warning("INVARIANT FAIL: resouces is null");
            return false;
        }
        return true;
    }

    /**
     * Check if the resources are enough to buy.
     *
     * @param price Price to buy.
     * @return <code>true</code> if resources are bigger than the price.
     */
    public boolean canBuy(ResourceValue price) {
        return this.resources.canBuy(price);
    }

    /**
     * @return <code>true</code> if this producer have negative values in its ratio.
     */
    public boolean hasNegativeRatio() {
        return this.ratio.hasNegative();
    }
}
