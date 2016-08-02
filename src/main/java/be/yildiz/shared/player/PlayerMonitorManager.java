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

import be.yildiz.common.collections.Maps;
import be.yildiz.shared.building.Building;
import be.yildiz.shared.building.BuildingData;
import be.yildiz.shared.city.City;
import be.yildiz.shared.entity.ActionManager;
import be.yildiz.shared.entity.Entity;
import be.yildiz.shared.entity.EntityData;

import java.util.Map;

/**
 * @param <T>
 * @param <B>
 * @param <D>
 * @param <E>
 * @author Grégory Van den Borre
 */
public final class PlayerMonitorManager<T extends Entity, B extends Building, D extends BuildingData, E extends EntityData, C extends City<B, D>> {

    private final ActionManager<T, E> actionManager;

    /**
     * Keep a list of all monitored players.
     */
    private final Map<Player, PlayerMonitor<T, B, D, C>> list = Maps.newMap();

    public PlayerMonitorManager(ActionManager<T, E> actionManager) {
        this.actionManager = actionManager;
    }

    public PlayerMonitor<T, B, D, C> get(final Player player) {
        return this.list.get(player);
    }

    public void addPlayerMonitor(final Player p) {
        PlayerMonitor<T, B, D, C> pm = new PlayerMonitor<>(p);
        this.list.put(p, pm);
        this.actionManager.addListener(pm);
    }

    public void removePlayerMonitor(final Player player) {
        if (this.list.containsKey(player)) {
            PlayerMonitor<T, B, D, C> pm = this.list.get(player);
            this.actionManager.removeListener(pm);
        }

    }
}
