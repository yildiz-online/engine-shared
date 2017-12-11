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

package be.yildiz.shared.ia.goal;

import be.yildizgames.common.collection.Lists;
import be.yildizgames.engine.feature.entity.Entity;

import java.util.List;

/**
 * Implementation of the goal generator based on entities modules.
 *
 * @author Grégory Van den Borre
 */
public final class ModuleGoalGenerator implements GoalGenerator {

    /**
     * Generate a list of goals for the entity based upon its modules.
     *
     * @param e Entity to generate the goals for.
     * @return A list of goals based on the actions the entity is able to execute.
     */
    @Override
    public List<Goal> generate(final Entity e) {
        List<Goal> goals = Lists.newList();
        //FIXME generate goal from entity?
        //goals.add(new Goal(e.getMoveAction()));
        goals.add(new Goal(e.getAttackAction()));
        goals.add(new Goal(e.getProtectAction()));
        goals.add(new Goal(e.getGenerateEnergyAction()));
        return goals;
    }

}
