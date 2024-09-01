package de.topobyte.osm4j.utils.merge.unsorted;

import de.topobyte.osm4j.core.access.OsmIterator;
import de.topobyte.osm4j.core.model.iface.OsmEntity;
import de.topobyte.osm4j.core.model.iface.OsmNode;
import de.topobyte.osm4j.core.model.iface.OsmRelation;
import de.topobyte.osm4j.core.model.iface.OsmWay;
import de.topobyte.osm4j.utils.merge.AbstractMerge;
import java.util.Collection;
import java.util.Deque;
import java.util.LinkedList;

public class AbstractUnsortedMerge extends AbstractMerge {
   protected Deque<AbstractUnsortedMerge.Input<OsmNode>> nodeItems = new LinkedList<>();
   protected Deque<AbstractUnsortedMerge.Input<OsmWay>> wayItems = new LinkedList<>();
   protected Deque<AbstractUnsortedMerge.Input<OsmRelation>> relationItems = new LinkedList<>();

   public AbstractUnsortedMerge(Collection<OsmIterator> inputs) {
      super(inputs);
   }

   protected <T extends OsmEntity> AbstractUnsortedMerge.Input<T> createItem(T element, OsmIterator iterator) {
      AbstractUnsortedMerge.Input<T> item = new AbstractUnsortedMerge.Input<>(iterator);
      item.firstEntity = element;
      return item;
   }

   protected class Input<T extends OsmEntity> {
      OsmIterator iterator;
      T firstEntity;

      public Input(OsmIterator iterator) {
         this.iterator = iterator;
      }
   }
}
