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

package be.yildiz.shared.construction.entity;

import be.yildiz.common.id.EntityId;
import be.yildiz.common.id.PlayerId;
import be.yildiz.common.vector.Point3D;
import be.yildiz.shared.entity.DefaultEntityInConstruction;
import be.yildiz.shared.entity.Entity;
import be.yildiz.shared.entity.EntityInConstructionFactory;
import be.yildiz.shared.entity.EntityInConstructionFactorySimple;

/**
 * This class fill the construction manager with requests comming from the queue manager.
 * @author Grégory Van den Borre
 */
public class EntityConstructionManagerFiller<T extends Entity> implements EntityConstructionQueueListener {

    private final EntityConstructionManager<T> manager;

    private final EntityInConstructionFactory f = new EntityInConstructionFactorySimple();
    private final IdProvider idProvider;

    public EntityConstructionManagerFiller(EntityConstructionManager<T> manager, IdProvider provider) {
        super();
        this.manager = manager;
        this.idProvider = provider;
    }

    @Override
    public void notify(EntityConstructionQueue list) {
        //does nothing.
    }

    @Override
    public void add(EntityConstructionQueue.EntityRepresentationConstruction toBuild, PlayerId p, EntityId buider) {
        EntityId id = this.idProvider.getFreeId();
        Builder builder = null;
        DefaultEntityInConstruction eic = this.f.build(toBuild.type, id, toBuild.data, p, builder.getBuildPosition(), Point3D.BASE_DIRECTION);
        this.manager.createEntity(eic, buider, toBuild);
    }
}
