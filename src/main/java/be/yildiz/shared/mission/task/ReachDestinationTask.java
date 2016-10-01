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

import be.yildiz.common.id.EntityId;
import be.yildiz.common.id.PlayerId;
import be.yildiz.common.vector.Point3D;
import be.yildiz.shared.entity.action.Action;
import be.yildiz.shared.entity.action.ActionListener;

/**
 * @author Grégory Van den Borre
 */
public class ReachDestinationTask extends Task implements ActionListener {

    //FIXME the implementation must create a ghost object to act as a trigger

    /**
     * Destination to reach to complete the task.
     */
    private final Point3D destination;

    /**
     * Define the distance around the destination to validate the task.
     */
    private final float squaredDistance;

    private final PlayerId playerId;

    public ReachDestinationTask(Point3D destination, float distance, PlayerId playerId) {
        super(new TaskId(2, new TaskType(TaskTypeConstant.DESTINATION)));
        this.destination = destination;
        this.squaredDistance = distance * distance;
        this.playerId = playerId;
    }

    @Override
    public void execute(EntityId id, PlayerId owner, Action a) {
        if(!owner.equals(this.playerId)) {
            return;
        }
        if(a.getPosition().squaredDistance(this.destination) <= squaredDistance) {
            this.setCompleted();
        }
    }
}
