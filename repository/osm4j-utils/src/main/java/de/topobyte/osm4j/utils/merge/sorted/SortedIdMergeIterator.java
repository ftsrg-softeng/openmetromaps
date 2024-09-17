package de.topobyte.osm4j.utils.merge.sorted;

import de.topobyte.osm4j.core.access.OsmIdIterator;
import de.topobyte.osm4j.core.model.iface.EntityType;
import de.topobyte.osm4j.core.model.iface.IdContainer;
import de.topobyte.osm4j.core.model.iface.OsmBounds;
import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class SortedIdMergeIterator extends AbstractSortedIdMerge implements OsmIdIterator {
   private boolean available = false;
   private EntityType mode = null;
   private long lastId = -1L;

   public SortedIdMergeIterator(Collection<OsmIdIterator> inputs) throws IOException {
      super(inputs);
      this.prepare();
   }

   private void prepare() throws IOException {
      for (OsmIdIterator iterator : this.inputs) {
         if (iterator.hasNext()) {
            this.available = true;
            IdContainer container = (IdContainer)iterator.next();
            long id = container.getId();
            switch (container.getType()) {
               case Node:
                  this.nodeItems.add(this.createItem(id, iterator));
                  break;
               case Way:
                  this.wayItems.add(this.createItem(id, iterator));
                  break;
               case Relation:
                  this.relationItems.add(this.createItem(id, iterator));
            }
         }
      }

      if (!this.nodeItems.isEmpty()) {
         this.mode = EntityType.Node;
      } else if (!this.wayItems.isEmpty()) {
         this.mode = EntityType.Way;
      } else if (!this.relationItems.isEmpty()) {
         this.mode = EntityType.Relation;
      }
   }

   public Iterator<IdContainer> iterator() {
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

   public IdContainer next() {
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

   private IdContainer nextNode() {
      AbstractSortedIdMerge.Input item = this.nodeItems.poll();
      this.lastId = item.currentId;
      this.advanceNodeItem(item, true);
      this.skipDuplicateNodes();
      this.ensureMode();
      return new IdContainer(EntityType.Node, this.lastId);
   }

   private IdContainer nextWay() {
      AbstractSortedIdMerge.Input item = this.wayItems.poll();
      this.lastId = item.currentId;
      this.advanceWayItem(item, true);
      this.skipDuplicateWays();
      this.ensureMode();
      return new IdContainer(EntityType.Way, this.lastId);
   }

   private IdContainer nextRelation() {
      AbstractSortedIdMerge.Input item = this.relationItems.poll();
      this.lastId = item.currentId;
      this.advanceRelationItem(item, true);
      this.skipDuplicateRelations();
      this.ensureMode();
      return new IdContainer(EntityType.Relation, this.lastId);
   }

   private void skipDuplicateNodes() {
      while (!this.nodeItems.isEmpty()) {
         AbstractSortedIdMerge.Input item = this.nodeItems.peek();
         if (item.currentId == this.lastId) {
            this.nodeItems.poll();
            this.advanceNodeItem(item, true);
            continue;
         }
         break;
      }
   }

   private void skipDuplicateWays() {
      while (!this.wayItems.isEmpty()) {
         AbstractSortedIdMerge.Input item = this.wayItems.peek();
         if (item.currentId == this.lastId) {
            this.wayItems.poll();
            this.advanceWayItem(item, true);
            continue;
         }
         break;
      }
   }

   private void skipDuplicateRelations() {
      while (!this.relationItems.isEmpty()) {
         AbstractSortedIdMerge.Input item = this.relationItems.peek();
         if (item.currentId == this.lastId) {
            this.relationItems.poll();
            this.advanceRelationItem(item, true);
            continue;
         }
         break;
      }
   }

   private void ensureMode() {
      switch (this.mode) {
         case Node:
            if (!this.nodeItems.isEmpty()) {
               return;
            }

            if (!this.wayItems.isEmpty()) {
               this.mode = EntityType.Way;
               this.lastId = -1L;
            } else if (!this.relationItems.isEmpty()) {
               this.mode = EntityType.Relation;
               this.lastId = -1L;
            } else {
               this.available = false;
            }
            break;
         case Way:
            if (!this.wayItems.isEmpty()) {
               return;
            }

            if (!this.relationItems.isEmpty()) {
               this.mode = EntityType.Relation;
               this.lastId = -1L;
            } else {
               this.available = false;
            }
            break;
         case Relation:
            if (!this.relationItems.isEmpty()) {
               return;
            }

            this.available = false;
      }
   }
}
