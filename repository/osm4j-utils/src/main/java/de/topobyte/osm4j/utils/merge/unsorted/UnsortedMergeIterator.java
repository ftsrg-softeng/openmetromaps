package de.topobyte.osm4j.utils.merge.unsorted;

import de.topobyte.osm4j.core.access.OsmIterator;
import de.topobyte.osm4j.core.model.iface.EntityContainer;
import de.topobyte.osm4j.core.model.iface.EntityType;
import de.topobyte.osm4j.core.model.iface.OsmBounds;
import de.topobyte.osm4j.core.model.iface.OsmEntity;
import de.topobyte.osm4j.core.model.iface.OsmNode;
import de.topobyte.osm4j.core.model.iface.OsmRelation;
import de.topobyte.osm4j.core.model.iface.OsmWay;
import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class UnsortedMergeIterator extends AbstractUnsortedMerge implements OsmIterator {
   private boolean available = false;
   private EntityType mode = null;
   private AbstractUnsortedMerge.Input<OsmNode> nodeInput = null;
   private AbstractUnsortedMerge.Input<OsmWay> wayInput = null;
   private AbstractUnsortedMerge.Input<OsmRelation> relationInput = null;

   public UnsortedMergeIterator(Collection<OsmIterator> inputs) throws IOException {
      super(inputs);
      this.prepare();
   }

   private void prepare() throws IOException {
      for (OsmIterator iterator : this.inputs) {
         if (iterator.hasNext()) {
            this.available = true;
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

      if (!this.nodeItems.isEmpty()) {
         this.mode = EntityType.Node;
         this.nodeInput = this.nodeItems.poll();
      } else if (!this.wayItems.isEmpty()) {
         this.mode = EntityType.Way;
         this.wayInput = this.wayItems.poll();
      } else if (!this.relationItems.isEmpty()) {
         this.mode = EntityType.Relation;
         this.relationInput = this.relationItems.poll();
      }
   }

   public Iterator<EntityContainer> iterator() {
      return this;
   }

   public void remove() {
      throw new UnsupportedOperationException();
   }

   public boolean hasBounds() {
      return this.hasBounds;
   }

   public OsmBounds getBounds() {
      return this.bounds;
   }

   public boolean hasNext() {
      return this.available;
   }

   public EntityContainer next() {
      switch (this.mode) {
         case Node:
            return this.nextNode();
         case Way:
            return this.nextWay();
         case Relation:
            return this.nextRelation();
         default:
            throw new NoSuchElementException();
      }
   }

   private EntityContainer nextNode() {
      OsmNode current = this.nodeInput.firstEntity;
      if (!this.advanceNodeItem(this.nodeInput)) {
         this.ensureMode();
      }

      return new EntityContainer(EntityType.Node, current);
   }

   private EntityContainer nextWay() {
      OsmWay current = this.wayInput.firstEntity;
      if (!this.advanceWayItem(this.wayInput)) {
         this.ensureMode();
      }

      return new EntityContainer(EntityType.Way, current);
   }

   private EntityContainer nextRelation() {
      OsmRelation current = this.relationInput.firstEntity;
      if (!this.advanceRelationItem(this.relationInput)) {
         this.ensureMode();
      }

      return new EntityContainer(EntityType.Relation, current);
   }

   protected boolean advanceNodeItem(AbstractUnsortedMerge.Input<OsmNode> item) {
      if (!item.iterator.hasNext()) {
         return false;
      } else {
         EntityContainer container = (EntityContainer)item.iterator.next();
         OsmEntity entity = container.getEntity();
         if (container.getType() == EntityType.Node) {
            item.firstEntity = (OsmNode)entity;
            return true;
         } else {
            if (container.getType() == EntityType.Way) {
               AbstractUnsortedMerge.Input<OsmWay> newItem = new AbstractUnsortedMerge.Input(item.iterator);
               newItem.firstEntity = (OsmWay)entity;
               this.wayItems.add(newItem);
            } else if (container.getType() == EntityType.Relation) {
               AbstractUnsortedMerge.Input<OsmRelation> newItem = new AbstractUnsortedMerge.Input(item.iterator);
               newItem.firstEntity = (OsmRelation)entity;
               this.relationItems.add(newItem);
            }

            return false;
         }
      }
   }

   protected boolean advanceWayItem(AbstractUnsortedMerge.Input<OsmWay> item) {
      if (!item.iterator.hasNext()) {
         return false;
      } else {
         EntityContainer container = (EntityContainer)item.iterator.next();
         OsmEntity entity = container.getEntity();
         if (container.getType() == EntityType.Way) {
            item.firstEntity = (OsmWay)entity;
            return true;
         } else {
            if (container.getType() == EntityType.Relation) {
               AbstractUnsortedMerge.Input<OsmRelation> newItem = new AbstractUnsortedMerge.Input(item.iterator);
               newItem.firstEntity = (OsmRelation)entity;
               this.relationItems.add(newItem);
            }

            return false;
         }
      }
   }

   protected boolean advanceRelationItem(AbstractUnsortedMerge.Input<OsmRelation> item) {
      if (!item.iterator.hasNext()) {
         return false;
      } else {
         EntityContainer container = (EntityContainer)item.iterator.next();
         OsmEntity entity = container.getEntity();
         if (container.getType() == EntityType.Relation) {
            item.firstEntity = (OsmRelation)entity;
            return true;
         } else {
            return false;
         }
      }
   }

   private void ensureMode() {
      switch (this.mode) {
         case Node:
            if (!this.nodeItems.isEmpty()) {
               this.nodeInput = this.nodeItems.poll();
               return;
            }

            if (!this.wayItems.isEmpty()) {
               this.mode = EntityType.Way;
               this.wayInput = this.wayItems.poll();
            } else if (!this.relationItems.isEmpty()) {
               this.mode = EntityType.Relation;
               this.relationInput = this.relationItems.poll();
            } else {
               this.available = false;
            }
            break;
         case Way:
            if (!this.wayItems.isEmpty()) {
               this.wayInput = this.wayItems.poll();
               return;
            }

            if (!this.relationItems.isEmpty()) {
               this.mode = EntityType.Relation;
               this.relationInput = this.relationItems.poll();
            } else {
               this.available = false;
            }
            break;
         case Relation:
            if (!this.relationItems.isEmpty()) {
               this.relationInput = this.relationItems.poll();
               return;
            }

            this.available = false;
      }
   }
}
