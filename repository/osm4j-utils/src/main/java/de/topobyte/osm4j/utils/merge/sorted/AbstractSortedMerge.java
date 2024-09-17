package de.topobyte.osm4j.utils.merge.sorted;

import de.topobyte.osm4j.core.access.OsmIterator;
import de.topobyte.osm4j.core.model.iface.EntityContainer;
import de.topobyte.osm4j.core.model.iface.EntityType;
import de.topobyte.osm4j.core.model.iface.OsmEntity;
import de.topobyte.osm4j.core.model.iface.OsmNode;
import de.topobyte.osm4j.core.model.iface.OsmRelation;
import de.topobyte.osm4j.core.model.iface.OsmWay;
import de.topobyte.osm4j.utils.merge.AbstractMerge;
import java.util.Collection;
import java.util.Comparator;
import java.util.PriorityQueue;

public class AbstractSortedMerge extends AbstractMerge {
   private Comparator<? super OsmNode> comparatorNodes;
   private Comparator<? super OsmWay> comparatorWays;
   private Comparator<? super OsmRelation> comparatorRelations;
   protected PriorityQueue<AbstractSortedMerge.Input<OsmNode>> nodeItems = new PriorityQueue<>(2, new AbstractSortedMerge.InputComparatorNodes());
   protected PriorityQueue<AbstractSortedMerge.Input<OsmWay>> wayItems = new PriorityQueue<>(2, new AbstractSortedMerge.InputComparatorWays());
   protected PriorityQueue<AbstractSortedMerge.Input<OsmRelation>> relationItems = new PriorityQueue<>(2, new AbstractSortedMerge.InputComparatorRelations());

   public AbstractSortedMerge(
      Collection<OsmIterator> inputs,
      Comparator<? super OsmNode> comparatorNodes,
      Comparator<? super OsmWay> comparatorWays,
      Comparator<? super OsmRelation> comparatorRelations
   ) {
      super(inputs);
      this.comparatorNodes = comparatorNodes;
      this.comparatorWays = comparatorWays;
      this.comparatorRelations = comparatorRelations;
   }

   protected <T extends OsmEntity> AbstractSortedMerge.Input<T> createItem(T element, OsmIterator iterator) {
      AbstractSortedMerge.Input<T> item = new AbstractSortedMerge.Input<>(iterator);
      item.currentEntity = element;
      item.currentId = item.currentEntity.getId();
      return item;
   }

   protected boolean advanceNodeItem(AbstractSortedMerge.Input<OsmNode> item, boolean putBack) {
      if (!item.iterator.hasNext()) {
         return false;
      } else {
         EntityContainer container = (EntityContainer)item.iterator.next();
         OsmEntity entity = container.getEntity();
         if (container.getType() == EntityType.Node) {
            item.currentEntity = (OsmNode)entity;
            item.currentId = entity.getId();
            if (putBack) {
               this.nodeItems.add(item);
            }

            return true;
         } else {
            if (container.getType() == EntityType.Way) {
               AbstractSortedMerge.Input<OsmWay> newItem = new AbstractSortedMerge.Input(item.iterator);
               newItem.currentEntity = (OsmWay)entity;
               newItem.currentId = entity.getId();
               this.wayItems.add(newItem);
            } else if (container.getType() == EntityType.Relation) {
               AbstractSortedMerge.Input<OsmRelation> newItem = new AbstractSortedMerge.Input(item.iterator);
               newItem.currentEntity = (OsmRelation)entity;
               newItem.currentId = entity.getId();
               this.relationItems.add(newItem);
            }

            return false;
         }
      }
   }

   protected boolean advanceWayItem(AbstractSortedMerge.Input<OsmWay> item, boolean putBack) {
      if (!item.iterator.hasNext()) {
         return false;
      } else {
         EntityContainer container = (EntityContainer)item.iterator.next();
         OsmEntity entity = container.getEntity();
         if (container.getType() == EntityType.Way) {
            item.currentEntity = (OsmWay)entity;
            item.currentId = entity.getId();
            if (putBack) {
               this.wayItems.add(item);
            }

            return true;
         } else {
            if (container.getType() == EntityType.Relation) {
               AbstractSortedMerge.Input<OsmRelation> newItem = new AbstractSortedMerge.Input(item.iterator);
               newItem.currentEntity = (OsmRelation)entity;
               newItem.currentId = entity.getId();
               this.relationItems.add(newItem);
            }

            return false;
         }
      }
   }

   protected boolean advanceRelationItem(AbstractSortedMerge.Input<OsmRelation> item, boolean putBack) {
      if (!item.iterator.hasNext()) {
         return false;
      } else {
         EntityContainer container = (EntityContainer)item.iterator.next();
         OsmEntity entity = container.getEntity();
         if (container.getType() == EntityType.Relation) {
            item.currentEntity = (OsmRelation)entity;
            item.currentId = entity.getId();
            if (putBack) {
               this.relationItems.add(item);
            }

            return true;
         } else {
            return false;
         }
      }
   }

   protected class Input<T extends OsmEntity> {
      OsmIterator iterator;
      T currentEntity;
      long currentId;

      public Input(OsmIterator iterator) {
         this.iterator = iterator;
      }
   }

   private class InputComparatorNodes implements Comparator<AbstractSortedMerge.Input<OsmNode>> {
      private InputComparatorNodes() {
      }

      public int compare(AbstractSortedMerge.Input<OsmNode> o1, AbstractSortedMerge.Input<OsmNode> o2) {
         return AbstractSortedMerge.this.comparatorNodes.compare(o1.currentEntity, o2.currentEntity);
      }
   }

   private class InputComparatorRelations implements Comparator<AbstractSortedMerge.Input<OsmRelation>> {
      private InputComparatorRelations() {
      }

      public int compare(AbstractSortedMerge.Input<OsmRelation> o1, AbstractSortedMerge.Input<OsmRelation> o2) {
         return AbstractSortedMerge.this.comparatorRelations.compare(o1.currentEntity, o2.currentEntity);
      }
   }

   private class InputComparatorWays implements Comparator<AbstractSortedMerge.Input<OsmWay>> {
      private InputComparatorWays() {
      }

      public int compare(AbstractSortedMerge.Input<OsmWay> o1, AbstractSortedMerge.Input<OsmWay> o2) {
         return AbstractSortedMerge.this.comparatorWays.compare(o1.currentEntity, o2.currentEntity);
      }
   }
}
