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

package be.yildiz.shared.entity.module;

import be.yildiz.common.collections.Lists;
import be.yildiz.common.id.ActionId;
import be.yildiz.common.util.StringUtil;
import lombok.Getter;
import lombok.NonNull;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * Hold the 4 main modules(move, interaction, energyGenerator and hull) ids, and all the other modules.
 *
 * @author Grégory Van den Borre
 */
public final class Modules implements Iterable<ActionId> {

    /**
     * Id of the module used for the entity move.
     */
    @Getter
    private final ActionId move;
    /**
     * Id of the module used for then entity interaction with other entities.
     */
    @Getter
    private final ActionId interaction;
    /**
     * Id of the module used for the hull (hit points and energy).
     */
    @Getter
    private final ActionId hull;
    @Getter
    private final ActionId energy;
    /**
     * List of other modules id.
     */
    @Getter
    private final List<ActionId> modules;
    /**
     * Contains all modules of this object.
     */
    @Getter
    private final List<ActionId> all;
    private ActionId id;

    /**
     * Build a modules from 3 given ids and a list of ids.
     *
     * @param move        Id used for move module.
     * @param interaction Id used for interaction module.
     * @param hull        Id used for hull module.
     * @param ids         Other modules if any.
     */
    public Modules(@NonNull ActionId move, @NonNull ActionId interaction, @NonNull ActionId hull, @NonNull ActionId energy, ActionId... ids) {
        super();
        this.move = move;
        this.interaction = interaction;
        this.hull = hull;
        this.energy = energy;
        List<ActionId> m = Lists.newList();
        if (ids != null) {
            Collections.addAll(m, ids);
        }
        this.modules = Collections.unmodifiableList(m);
        this.all = this.buildAll();
    }

    /**
     * Build a modules from a list of ids, the first element is for the move module, the second for the interaction module, the third for the the hull module, others are added in miscellaneous.
     *
     * @param ids List of ids to use, must contains at least 3 elements.
     */
    public Modules(@NonNull List<ActionId> ids) {
        super();
        this.move = ids.get(0);
        this.interaction = ids.get(1);
        this.hull = ids.get(2);
        this.energy = ids.get(3);
        List<ActionId> m = Lists.newList();
        for (int i = 4; i < ids.size(); i++) {
            m.add(ids.get(i));
        }
        this.modules = Collections.unmodifiableList(m);
        this.all = this.buildAll();
    }

    @Override
    public Iterator<ActionId> iterator() {
        // FIXME should alse take into account move hull interact!
        return this.modules.iterator();
    }

    /**
     * @return A list of all modules in this object.
     */
    private List<ActionId> buildAll() {
        List<ActionId> l = Lists.newList();
        l.add(this.move);
        l.add(this.interaction);
        l.add(this.hull);
        l.add(this.energy);
        if (!this.modules.isEmpty()) {
            l.addAll(this.modules);
        }
        return Collections.unmodifiableList(l);
    }

    @Override
    public int hashCode() {
        return this.move.hashCode() + this.interaction.hashCode() + this.hull.hashCode() + this.energy.hashCode();
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Modules)) {
            return false;
        }
        Modules other = (Modules) obj;
        return this.move.equals(other.move) && this.interaction.equals(other.interaction) && this.hull.equals(other.hull) && this.energy.equals(other.energy) && this.modules.size() == other.modules.size()
                && this.modules.containsAll(other.modules);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (ActionId a : this.getAll()) {
            sb.append(a.value + ":");
        }
        return StringUtil.removeLastChar(sb);
    }

    public ActionId getId() {
        return this.id;
    }
}
