package de.topobyte.osm4j.utils.merge.unsorted;

import de.topobyte.osm4j.core.access.OsmIterator;
import de.topobyte.osm4j.core.access.OsmOutputStream;
import de.topobyte.osm4j.core.model.iface.EntityContainer;
import de.topobyte.osm4j.core.model.iface.EntityType;
import de.topobyte.osm4j.core.model.iface.OsmEntity;
import de.topobyte.osm4j.core.model.iface.OsmNode;
import de.topobyte.osm4j.core.model.iface.OsmRelation;
import de.topobyte.osm4j.core.model.iface.OsmWay;
import java.io.IOException;
import java.util.Collection;

public class UnsortedMerge extends AbstractUnsortedMerge {
   private OsmOutputStream output;

   public UnsortedMerge(OsmOutputStream output, Collection<OsmIterator> inputs) {
      super(inputs);
      this.output = output;
   }

   public void run() throws IOException {
      this.prepare();
      this.iterate();
      this.output.complete();
   }

   private void prepare() throws IOException {
      if (this.hasBounds) {
         this.output.write(this.bounds);
      }

      for (OsmIterator iterator : this.inputs) {
         if (iterator.hasNext()) {
            EntityContainer container = (EntityContainer)iterator.next();
            OsmEntity entity = container.getEntity();
            switch (container.getType()) {
               case Node:
                  this.nodeItems.add(this.createItem((OsmNode)entity, iterator));
                  break;
               case Way:
                  this.wayItems.add(this.createItem((OsmWay)entity, iterator));
                  break;
               case Relation:
                  this.relationItems.add(this.createItem((OsmRelation)entity, iterator));
            }
         }
      }
   }

   private void iterate() throws IOException {
      while (!this.nodeItems.isEmpty()) {
         AbstractUnsortedMerge.Input<OsmNode> item = this.nodeItems.poll();
         this.output.write(item.firstEntity);
         item.firstEntity = null;
         OsmIterator iterator = item.iterator;

         while (iterator.hasNext()) {
            EntityContainer container = (EntityContainer)iterator.next();
            OsmEntity entity = container.getEntity();
            if (container.getType() == EntityType.Node) {
               this.output.write((OsmNode)entity);
            } else if (container.getType() == EntityType.Way) {
               this.wayItems.add(this.createItem((OsmWay)entity, iterator));
            } else if (container.getType() == EntityType.Relation) {
               this.relationItems.add(this.createItem((OsmRelation)entity, iterator));
            }
         }
      }

      while (!this.wayItems.isEmpty()) {
         AbstractUnsortedMerge.Input<OsmWay> item = this.wayItems.poll();
         this.output.write(item.firstEntity);
         item.firstEntity = null;
         OsmIterator iterator = item.iterator;

         while (iterator.hasNext()) {
            EntityContainer container = (EntityContainer)iterator.next();
            OsmEntity entity = container.getEntity();
            if (container.getType() == EntityType.Way) {
               this.output.write((OsmWay)entity);
            } else {
               if (container.getType() != EntityType.Relation) {
                  break;
               }

               this.relationItems.add(this.createItem((OsmRelation)entity, iterator));
            }
         }
      }

      while (!this.relationItems.isEmpty()) {
         AbstractUnsortedMerge.Input<OsmRelation> item = this.relationItems.poll();
         this.output.write(item.firstEntity);
         item.firstEntity = null;
         OsmIterator iterator = item.iterator;

         while (iterator.hasNext()) {
            EntityContainer container = (EntityContainer)iterator.next();
            OsmEntity entity = container.getEntity();
            if (container.getType() != EntityType.Relation) {
               break;
            }

            this.output.write((OsmRelation)entity);
         }
      }
   }
}
