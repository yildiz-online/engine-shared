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

    /**
     * Manager responsible to build entities.
     */
    private final EntityConstructionManager<T> manager;

    /**
     * Helper class to create entity in construction instances.
     */
    private final EntityInConstructionFactory f = new EntityInConstructionFactorySimple();

    /**
     * Provider for unused unique entity id.
     */
    private final IdProvider idProvider;

    /**
     * Manager responsible to handle the builders.
     */
    private final BuilderManager builderManager;

    /**
     * Create a new instance filler.
     * @param manager Manager responsible to build entities.
     * @param provider Provider for unused unique entity id.
     * @param builderManager Manager responsible to handle the builders.
     */
    public EntityConstructionManagerFiller(EntityConstructionManager<T> manager, IdProvider provider, BuilderManager builderManager) {
        super();
        this.manager = manager;
        this.idProvider = provider;
        this.builderManager = builderManager;
    }

    @Override
    public void notify(EntityConstructionQueue list) {
        //does nothing.
    }

    @Override
    public void add(EntityConstructionQueue.EntityRepresentationConstruction toBuild, PlayerId p, EntityId builderId) {
        EntityId id = this.idProvider.getFreeId();
        //FIXME handle the optional correctly
        Builder builder = this.builderManager.getBuilderById(builderId).get();
        DefaultEntityInConstruction eic = this.f.build(toBuild.type, id, toBuild.data, p, builder.getBuildPosition(), Point3D.BASE_DIRECTION);
        this.manager.createEntity(eic, builderId, toBuild);
    }
}