package de.topobyte.osm4j.utils.merge.sorted;

import de.topobyte.osm4j.core.access.OsmIterator;
import de.topobyte.osm4j.core.dataset.sort.IdComparator;
import de.topobyte.osm4j.core.model.iface.EntityContainer;
import de.topobyte.osm4j.core.model.iface.EntityType;
import de.topobyte.osm4j.core.model.iface.OsmBounds;
import de.topobyte.osm4j.core.model.iface.OsmEntity;
import de.topobyte.osm4j.core.model.iface.OsmNode;
import de.topobyte.osm4j.core.model.iface.OsmRelation;
import de.topobyte.osm4j.core.model.iface.OsmWay;
import java.io.IOException;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class SortedMergeIterator extends AbstractSortedMerge implements OsmIterator {
   private boolean available = false;
   private EntityType mode = null;
   private long lastId = -1L;

   public SortedMergeIterator(Collection<OsmIterator> inputs) throws IOException {
      this(inputs, new IdComparator());
   }

   public SortedMergeIterator(Collection<OsmIterator> inputs, Comparator<OsmEntity> comparator) throws IOException {
      this(inputs, comparator, comparator, comparator);
   }

   public SortedMergeIterator(
      Collection<OsmIterator> inputs,
      Comparator<? super OsmNode> comparatorNodes,
      Comparator<? super OsmWay> comparatorWays,
      Comparator<? super OsmRelation> comparatorRelations
   ) throws IOException {
      super(inputs, comparatorNodes, comparatorWays, comparatorRelations);
      this.prepare();
   }

   private void prepare() throws IOException {
      for (OsmIterator iterator : this.inputs) {
         if (iterator.hasNext()) {
            this.available = true;
            EntityContainer container = (EntityContainer)iterator.next();
            switch (container.getType()) {
               case Node:
                  this.nodeItems.add(this.createItem((OsmNode)container.getEntity(), iterator));
                  break;
               case Way:
                  this.wayItems.add(this.createItem((OsmWay)container.getEntity(), iterator));
                  break;
               case Relation:
                  this.relationItems.add(this.createItem((OsmRelation)container.getEntity(), iterator));
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
      AbstractSortedMerge.Input<OsmNode> item = this.nodeItems.poll();
      OsmEntity current = item.currentEntity;
      this.lastId = current.getId();
      this.advanceNodeItem(item, true);
      this.skipDuplicateNodes();
      this.ensureMode();
      return new EntityContainer(EntityType.Node, current);
   }

   private EntityContainer nextWay() {
      AbstractSortedMerge.Input<OsmWay> item = this.wayItems.poll();
      OsmEntity current = item.currentEntity;
      this.lastId = current.getId();
      this.advanceWayItem(item, true);
      this.skipDuplicateWays();
      this.ensureMode();
      return new EntityContainer(EntityType.Way, current);
   }

   private EntityContainer nextRelation() {
      AbstractSortedMerge.Input<OsmRelation> item = this.relationItems.poll();
      OsmEntity current = item.currentEntity;
      this.lastId = current.getId();
      this.advanceRelationItem(item, true);
      this.skipDuplicateRelations();
      this.ensureMode();
      return new EntityContainer(EntityType.Relation, current);
   }

   private void skipDuplicateNodes() {
      while (!this.nodeItems.isEmpty()) {
         AbstractSortedMerge.Input<OsmNode> item = this.nodeItems.peek();
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
         AbstractSortedMerge.Input<OsmWay> item = this.wayItems.peek();
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
         AbstractSortedMerge.Input<OsmRelation> item = this.relationItems.peek();
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
