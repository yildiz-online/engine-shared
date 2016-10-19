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

package be.yildiz.shared.player;

import be.yildiz.common.id.EntityId;
import be.yildiz.common.id.PlayerId;
import be.yildiz.common.log.Logger;
import be.yildiz.common.util.ElapsedTimeComputer;
import be.yildiz.shared.building.Building;
import be.yildiz.shared.building.BuildingData;
import be.yildiz.shared.city.City;
import be.yildiz.shared.construction.building.BuildingConstructionListener;
import be.yildiz.shared.construction.entity.EntityConstructionListener;
import be.yildiz.shared.entity.Entity;
import be.yildiz.shared.entity.action.Action;
import be.yildiz.shared.entity.action.ActionListener;

/**
 * Service to monitor a player and log its actions.
 *
 * @author Grégory Van den Borre
 */
public final class PlayerMonitor<T extends Entity, B extends Building, D extends BuildingData, C extends City<B, D>> implements ActionListener, EntityConstructionListener<T>, BuildingConstructionListener<B, D, C> {

    /**
     * Provide a pause between successive calls.
     */
    private final ElapsedTimeComputer etc;

    /**
     * Player currently monitored.
     */
    private final Player player;

    /**
     * The player name.
     */
    private final String playerName;

    /**
     * Full constructor.
     *
     * @param p Player to monitor.
     */
    PlayerMonitor(final Player p) {
        super();
        this.player = p;
        this.playerName = "PLAYER " + this.player;
        this.etc = new ElapsedTimeComputer(10000);
    }

    @Override
    public void entityComplete(final T entity, EntityId builder, int index) {
        if (entity.getOwner().equals(this.player.id)) {
            Logger.info(this.playerName + " entity complete " + entity);
        }
    }

    @Override
    public void buildingComplete(C c, final B b) {
        if (c.getOwner().equals(this.player.id)) {
            Logger.info(this.playerName + " building complete " + b.getType());
        }
    }

    @Override
    public void buildingInConstruction(final C city, final B def, final long timeLeft) {
        if (city.getOwner().equals(this.player.id) && etc.isTimeElapsed()) {
            Logger.info(this.playerName + " building in construction " + def.getType() + ":" + timeLeft);
        }
    }

    @Override
    public void execute(EntityId id, PlayerId owner, Action a) {

    }
}
