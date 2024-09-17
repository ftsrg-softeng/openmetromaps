package de.topobyte.osm4j.extra.datatree.nodetree.count;

import de.topobyte.osm4j.core.access.OsmIterator;
import de.topobyte.osm4j.extra.datatree.DataTree;
import de.topobyte.osm4j.extra.datatree.Node;

public interface NodeTreeLeafCounterFactory {
   NodeTreeLeafCounter createLeafCounter(DataTree var1, OsmIterator var2, Node var3);
}
