package de.topobyte.osm4j.extra.datatree.nodetree.distribute;

import de.topobyte.osm4j.core.access.OsmIterator;
import de.topobyte.osm4j.extra.datatree.DataTree;
import de.topobyte.osm4j.extra.datatree.Node;
import de.topobyte.osm4j.extra.datatree.output.DataTreeOutputFactory;

public class ThreadedNodeTreeDistributorFactory implements NodeTreeDistributorFactory {
   @Override
   public NodeTreeDistributor createDistributor(DataTree tree, Node head, OsmIterator iterator, DataTreeOutputFactory outputFactory) {
      return new ThreadedNodeTreeDistributor(tree, head, iterator, outputFactory);
   }
}
