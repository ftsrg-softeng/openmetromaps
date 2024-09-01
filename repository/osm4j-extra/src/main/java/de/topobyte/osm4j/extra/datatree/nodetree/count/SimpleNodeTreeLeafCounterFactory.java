package de.topobyte.osm4j.extra.datatree.nodetree.count;

import de.topobyte.osm4j.core.access.OsmIterator;
import de.topobyte.osm4j.extra.datatree.DataTree;
import de.topobyte.osm4j.extra.datatree.Node;

public class SimpleNodeTreeLeafCounterFactory implements NodeTreeLeafCounterFactory {
   @Override
   public NodeTreeLeafCounter createLeafCounter(DataTree tree, OsmIterator iterator, Node head) {
      return new SimpleNodeTreeLeafCounter(tree, head, iterator);
   }
}
