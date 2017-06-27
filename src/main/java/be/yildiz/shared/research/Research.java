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

package be.yildiz.shared.research;

import be.yildiz.common.util.BaseRegisterable;
import be.yildiz.common.util.Registerer;
import be.yildiz.shared.entity.bonus.EntityBonus;
import be.yildiz.shared.resources.GameResources;
import be.yildiz.shared.resources.ResourceValue;

import java.util.Optional;

/**
 * Contains the data for a research.
 *
 * @author Grégory Van den Borre
 */
public final class Research extends BaseRegisterable {

    /**
     * Contains all created Research, to check name is unique and to retrieve a Research from its name.
     */
    private static final Registerer<Research> REGISTERER = Registerer.newRegisterer();

    /**
     * Bonus obtained the this research is done.
     */
    private final EntityBonus bonus;

    /**
     * Research price.
     */
    private final ResourceValue price;

    /**
     * Research needed to be done before making this one, optional. none.
     */
    private final Optional<Research> prerequisite;


    private Research(final String name, final float researchPrice, final EntityBonus bonus, final Optional<Research> prerequisite) {
        super(name);
        this.prerequisite = prerequisite;
        this.price = GameResources.research(researchPrice);
        this.bonus = bonus;
        Research.REGISTERER.register(this);
    }

    /**
     * @param name  Research unique name.
     * @param price Research price.
     * @param bonus Bonus received when this research is bought.
     * @return The created research.
     */
    //@effects Create a new Research with no prerequisite.
    public static Research createAndRegister(final String name, final float price, final EntityBonus bonus) {
        return new Research(name, price, bonus, Optional.empty());
    }

    /**
     * @param name          Research unique name.
     * @param price         Research price.
     * @param bonus         Bonus received when this research is bought.
     * @param prerequisite  Research needed before buying this one.
     * @return The created research.
     */
    //@effects Create a new Research with a prerequisite.
    public static Research createAndRegister(final String name, final float price, final EntityBonus bonus, final Research prerequisite) {
        return new Research(name, price, bonus, Optional.of(prerequisite));
    }

    /**
     * Retrieve a research with its unique name.
     *
     * @param name Research unique name.
     * @return The research matching to the given name.
     */
    public static Research get(final String name) {
        return Research.REGISTERER.get(name);
    }

    public EntityBonus getBonus() {
        return bonus;
    }

    public ResourceValue getPrice() {
        return price;
    }

    public Optional<Research> getPrerequisite() {
        return prerequisite;
    }
}
