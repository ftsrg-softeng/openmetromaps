package de.topobyte.osm4j.extra.datatree.nodetree.distribute;

import de.topobyte.osm4j.core.access.OsmIterator;
import de.topobyte.osm4j.extra.datatree.DataTree;
import de.topobyte.osm4j.extra.datatree.Node;
import de.topobyte.osm4j.extra.datatree.output.DataTreeOutputFactory;

public interface NodeTreeDistributorFactory {
   NodeTreeDistributor createDistributor(DataTree var1, Node var2, OsmIterator var3, DataTreeOutputFactory var4);
}
