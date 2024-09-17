package de.topobyte.osm4j.utils.merge.sorted;

import de.topobyte.osm4j.core.access.OsmIterator;
import de.topobyte.osm4j.core.access.OsmOutputStream;
import de.topobyte.osm4j.core.dataset.sort.IdComparator;
import de.topobyte.osm4j.core.model.iface.EntityContainer;
import de.topobyte.osm4j.core.model.iface.OsmEntity;
import de.topobyte.osm4j.core.model.iface.OsmNode;
import de.topobyte.osm4j.core.model.iface.OsmRelation;
import de.topobyte.osm4j.core.model.iface.OsmWay;
import java.io.IOException;
import java.util.Collection;
import java.util.Comparator;

public class SortedMerge extends AbstractSortedMerge {
   private OsmOutputStream output;
   private long lastId = -1L;

   public SortedMerge(OsmOutputStream output, Collection<OsmIterator> inputs) {
      this(output, inputs, new IdComparator());
   }

   public SortedMerge(OsmOutputStream output, Collection<OsmIterator> inputs, Comparator<OsmEntity> comparator) {
      this(output, inputs, comparator, comparator, comparator);
   }

   public SortedMerge(
      OsmOutputStream output,
      Collection<OsmIterator> inputs,
      Comparator<? super OsmNode> comparatorNodes,
      Comparator<? super OsmWay> comparatorWays,
      Comparator<? super OsmRelation> comparatorRelations
   ) {
      super(inputs, comparatorNodes, comparatorWays, comparatorRelations);
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
      while (this.nodeItems.size() > 1) {
         AbstractSortedMerge.Input<OsmNode> item = this.nodeItems.poll();
         this.writeNode(item);
         this.advanceNodeItem(item, true);
      }

      if (this.nodeItems.size() == 1) {
         AbstractSortedMerge.Input<OsmNode> item = this.nodeItems.poll();

         do {
            this.writeNode(item);
         } while (this.advanceNodeItem(item, false));
      }

      this.lastId = -1L;

      while (this.wayItems.size() > 1) {
         AbstractSortedMerge.Input<OsmWay> item = this.wayItems.poll();
         this.writeWay(item);
         this.advanceWayItem(item, true);
      }

      if (this.wayItems.size() == 1) {
         AbstractSortedMerge.Input<OsmWay> item = this.wayItems.poll();

         do {
            this.writeWay(item);
         } while (this.advanceWayItem(item, false));
      }

      this.lastId = -1L;

      while (this.relationItems.size() > 1) {
         AbstractSortedMerge.Input<OsmRelation> item = this.relationItems.poll();
         this.writeRelation(item);
         this.advanceRelationItem(item, true);
      }

      if (this.relationItems.size() == 1) {
         AbstractSortedMerge.Input<OsmRelation> item = this.relationItems.poll();

         do {
            this.writeRelation(item);
         } while (this.advanceRelationItem(item, false));
      }
   }

   private void writeNode(AbstractSortedMerge.Input<OsmNode> item) throws IOException {
      if (item.currentId != this.lastId) {
         this.output.write(item.currentEntity);
         this.lastId = item.currentId;
      }
   }

   private void writeWay(AbstractSortedMerge.Input<OsmWay> item) throws IOException {
      if (item.currentId != this.lastId) {
         this.output.write(item.currentEntity);
         this.lastId = item.currentId;
      }
   }

   private void writeRelation(AbstractSortedMerge.Input<OsmRelation> item) throws IOException {
      if (item.currentId != this.lastId) {
         this.output.write(item.currentEntity);
         this.lastId = item.currentId;
      }
   }
}
