package de.topobyte.osm4j.core.util;

import de.topobyte.osm4j.core.access.OsmIterator;
import de.topobyte.osm4j.core.model.iface.EntityType;
import de.topobyte.osm4j.core.model.iface.OsmNode;

public class NodeIterator extends EntityIterator<OsmNode> {
   public NodeIterator(OsmIterator iterator) {
      super(iterator, EntityType.Node);
   }
}
