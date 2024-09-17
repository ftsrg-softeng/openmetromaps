package de.topobyte.osm4j.utils.merge.sorted;

import de.topobyte.osm4j.core.access.OsmIdIterator;
import de.topobyte.osm4j.core.model.iface.EntityType;
import de.topobyte.osm4j.core.model.iface.IdContainer;
import de.topobyte.osm4j.utils.merge.AbstractIdMerge;
import java.util.Collection;
import java.util.Comparator;
import java.util.PriorityQueue;

public class AbstractSortedIdMerge extends AbstractIdMerge {
   protected PriorityQueue<AbstractSortedIdMerge.Input> nodeItems = new PriorityQueue<>(2, new AbstractSortedIdMerge.InputComparator());
   protected PriorityQueue<AbstractSortedIdMerge.Input> wayItems = new PriorityQueue<>(2, new AbstractSortedIdMerge.InputComparator());
   protected PriorityQueue<AbstractSortedIdMerge.Input> relationItems = new PriorityQueue<>(2, new AbstractSortedIdMerge.InputComparator());

   public AbstractSortedIdMerge(Collection<OsmIdIterator> inputs) {
      super(inputs);
   }

   protected AbstractSortedIdMerge.Input createItem(long id, OsmIdIterator iterator) {
      AbstractSortedIdMerge.Input item = new AbstractSortedIdMerge.Input(iterator);
      item.currentId = id;
      return item;
   }

   protected boolean advanceNodeItem(AbstractSortedIdMerge.Input item, boolean putBack) {
      if (!item.iterator.hasNext()) {
         return false;
      } else {
         IdContainer container = (IdContainer)item.iterator.next();
         if (container.getType() == EntityType.Node) {
            item.currentId = container.getId();
            if (putBack) {
               this.nodeItems.add(item);
            }

            return true;
         } else {
            if (container.getType() == EntityType.Way) {
               AbstractSortedIdMerge.Input newItem = new AbstractSortedIdMerge.Input(item.iterator);
               newItem.currentId = container.getId();
               this.wayItems.add(newItem);
            } else if (container.getType() == EntityType.Relation) {
               AbstractSortedIdMerge.Input newItem = new AbstractSortedIdMerge.Input(item.iterator);
               newItem.currentId = container.getId();
               this.relationItems.add(newItem);
            }

            return false;
         }
      }
   }

   protected boolean advanceWayItem(AbstractSortedIdMerge.Input item, boolean putBack) {
      if (!item.iterator.hasNext()) {
         return false;
      } else {
         IdContainer container = (IdContainer)item.iterator.next();
         if (container.getType() == EntityType.Way) {
            item.currentId = container.getId();
            if (putBack) {
               this.wayItems.add(item);
            }

            return true;
         } else {
            if (container.getType() == EntityType.Relation) {
               AbstractSortedIdMerge.Input newItem = new AbstractSortedIdMerge.Input(item.iterator);
               newItem.currentId = container.getId();
               this.relationItems.add(newItem);
            }

            return false;
         }
      }
   }

   protected boolean advanceRelationItem(AbstractSortedIdMerge.Input item, boolean putBack) {
      if (!item.iterator.hasNext()) {
         return false;
      } else {
         IdContainer container = (IdContainer)item.iterator.next();
         if (container.getType() == EntityType.Relation) {
            item.currentId = container.getId();
            if (putBack) {
               this.relationItems.add(item);
            }

            return true;
         } else {
            return false;
         }
      }
   }

   protected class Input {
      OsmIdIterator iterator;
      long currentId;

      public Input(OsmIdIterator iterator) {
         this.iterator = iterator;
      }
   }

   private class InputComparator implements Comparator<AbstractSortedIdMerge.Input> {
      private InputComparator() {
      }

      public int compare(AbstractSortedIdMerge.Input o1, AbstractSortedIdMerge.Input o2) {
         return Long.compare(o1.currentId, o2.currentId);
      }
   }
}
