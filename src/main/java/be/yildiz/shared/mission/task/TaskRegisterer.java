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

import be.yildiz.common.collections.Lists;
import be.yildiz.shared.entity.ActionManager;
import be.yildiz.shared.entity.DestructionListener;
import be.yildiz.shared.entity.action.ActionListener;

import java.util.List;

/**
 * @author Grégory Van den Borre
 */
public class TaskRegisterer {

    //TODO the register goes in the factory: factory know true type -> register(ActionListener) register(destructionListener)...
    //The custom listener will be added in the derived class(like default register does)
    //and the custom registerer will be used in the custom factory!
    //keep maps map < taskid, ActionListener>... to remove them properly when required

    private final ActionManager action;

    private final List<ActionListener> actionListenerRegistered = Lists.newList();

    private final List<DestructionListener> destructionListenerRegistered = Lists.newList();

    public TaskRegisterer(ActionManager action) {
        this.action = action;
    }

    public final void registerTask(ActionListener l) {
        this.action.addListener(l);
        this.actionListenerRegistered.add(l);
    }

    public final void registerTask(DestructionListener l) {
        this.action.addDestructionListener(l);
        this.destructionListenerRegistered.add(l);
    }
}
