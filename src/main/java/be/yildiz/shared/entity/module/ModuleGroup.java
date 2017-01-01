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

package be.yildiz.shared.entity.module;

import be.yildiz.common.id.ActionId;
import lombok.Getter;
import lombok.NonNull;

import java.util.Arrays;
import java.util.List;

/**
 * Hold the 8 main modules(move, interaction, detector energyGenerator, hull and 3 other optional modules).
 *
 * @author Grégory Van den Borre
 */
@Getter
public final class ModuleGroup {

    /**
     * Id of the module used for the entity move.
     */
    private final ActionId move;

    /**
     * Id of the module used for then entity interaction with other entities.
     */
    private final ActionId interaction;

    /**
     * Id of the module used for the hull (hit points).
     */
    private final ActionId hull;

    /**
     * Id of the module used for the energy generation.
     */
    private final ActionId energy;

    private final ActionId detector;

    private final ActionId additional1;

    private final ActionId additional2;

    private final ActionId additional3;


    /**
     * Build a modules from 8 given ids.
     *
     * @param hull        Id used for hull module.
     * @param energy      Id used for energy generation module.
     * @param move        Id used for move module.
     * @param interaction Id used for interaction module.
     * @throws NullPointerException if any parameter is null.
     */
    private ModuleGroup(@NonNull ActionId hull, @NonNull ActionId energy, @NonNull ActionId detector, @NonNull ActionId move, @NonNull ActionId interaction, @NonNull ActionId additional1, @NonNull ActionId additional2, @NonNull ActionId additional3) {
        super();
        this.move = move;
        this.interaction = interaction;
        this.hull = hull;
        this.energy = energy;
        this.detector = detector;
        this.additional1 = additional1;
        this.additional2 = additional2;
        this.additional3 = additional3;
    }

    @Override
    public int hashCode() {
        return this.move.hashCode() + this.interaction.hashCode() + this.hull.hashCode() + this.energy.hashCode() + this.detector.hashCode() + this.additional1.hashCode() + this.additional2.hashCode() + this.additional3.hashCode();}

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ModuleGroup)) {
            return false;
        }
        ModuleGroup that = (ModuleGroup) obj;
        if(!this.move.equals(that.move)) {
            return false;
        }
        if(!this.interaction.equals(that.interaction)){
            return false;
        }
        if(!this.hull.equals(that.hull)) {
            return false;
        }
        if(!this.energy.equals(that.energy)) {
            return false;
        }
        if(!this.detector.equals(that.detector)) {
            return false;
        }
        if(!this.additional1.equals(that.additional1)) {
            return false;
        }
        if(!this.additional2.equals(that.additional2)) {
            return false;
        }
        if(!this.additional3.equals(that.additional3)) {
            return false;
        }
        return true;
    }

    public List<ActionId> getAll() {
        return Arrays.asList(this.hull, this.energy, this.detector, this.move, this.interaction, this.additional1, this.additional2, this.additional3);
    }

    public static class ModuleGroupBuilder {

        /**
         * Id of the module used for the entity move.
         */
        private ActionId move;

        /**
         * Id of the module used for then entity interaction with other entities.
         */
        private ActionId interaction;

        /**
         * Id of the module used for the hull (hit points).
         */
        private ActionId hull;

        /**
         * Id of the module used for the energy generation.
         */
        private ActionId energy;

        private ActionId detector;

        private ActionId additional1;

        private ActionId additional2;

        private ActionId additional3;

        public ModuleGroupBuilder withHull(ActionId hull) {
            this.hull = hull;
            return this;
        }

        public ModuleGroupBuilder withEnergy(ActionId energy) {
            this.energy = energy;
            return this;
        }

        public ModuleGroupBuilder withDetector(ActionId detector) {
            this.detector = detector;
            return this;
        }

        public ModuleGroupBuilder withMove(ActionId move) {
            this.move = move;
            return this;
        }

        public ModuleGroupBuilder withInteraction(ActionId interaction) {
            this.interaction = interaction;
            return this;
        }

        public ModuleGroupBuilder withAdditional1(ActionId additional1) {
            this.additional1 = additional1;
            return this;
        }

        public ModuleGroupBuilder withAdditional2(ActionId additional2) {
            this.additional2 = additional2;
            return this;
        }

        public ModuleGroupBuilder withAdditional3(ActionId additional3) {
            this.additional3 = additional3;
            return this;
        }

        public ModuleGroupBuilder fromList(List<ActionId> actionIds) {
            if(actionIds.size() != 8) {
                throw new IllegalArgumentException("Size must be 8 actual is " + actionIds.size());
            }
            if(actionIds.contains(null)) {
                throw new NullPointerException("The list should not contain null values");
            }
            this.hull = actionIds.get(0);
            this.energy = actionIds.get(1);
            this.detector = actionIds.get(2);
            this.move = actionIds.get(3);
            this.interaction = actionIds.get(4);
            this.additional1 = actionIds.get(5);
            this.additional2 = actionIds.get(6);
            this.additional3 = actionIds.get(7);
            return this;
        }

        public ModuleGroupBuilder withNoAdditional() {
            this.additional1 = ActionId.get(255);
            this.additional2 = ActionId.get(255);
            this.additional3 = ActionId.get(255);
            return this;
        }

        public ModuleGroup build() {
            return new ModuleGroup(this.hull, this.energy, this.detector, this.move, this.interaction, this.additional1, this.additional2, this.additional3);
        }
    }
}
