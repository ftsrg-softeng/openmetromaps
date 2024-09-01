package de.topobyte.osm4j.extra.datatree.nodetree;

import de.topobyte.osm4j.core.access.OsmIterator;
import de.topobyte.osm4j.extra.datatree.DataTree;
import de.topobyte.osm4j.extra.datatree.nodetree.distribute.NodeTreeDistributor;
import de.topobyte.osm4j.extra.datatree.nodetree.distribute.NodeTreeDistributorFactory;
import de.topobyte.osm4j.extra.datatree.output.DataTreeOutputFactory;
import java.io.IOException;

public class NodeTreeCreator {
   private DataTree tree;
   private OsmIterator iterator;
   private DataTreeOutputFactory outputFactory;
   private NodeTreeDistributorFactory distributorFactory;

   public NodeTreeCreator(DataTree tree, OsmIterator iterator, DataTreeOutputFactory outputFactory, NodeTreeDistributorFactory distributorFactory) {
      this.tree = tree;
      this.outputFactory = outputFactory;
      this.distributorFactory = distributorFactory;
      this.iterator = iterator;
   }

   public void execute() throws IOException {
      NodeTreeDistributor distributor = this.distributorFactory.createDistributor(this.tree, this.tree.getRoot(), this.iterator, this.outputFactory);
      distributor.execute();
   }
}
