package de.topobyte.osm4j.core.access;

import de.topobyte.osm4j.core.model.iface.EntityContainer;
import de.topobyte.osm4j.core.model.iface.OsmBounds;
import java.util.Iterator;

public interface OsmIterator extends Iterable<EntityContainer>, Iterator<EntityContainer> {
   boolean hasBounds();

   OsmBounds getBounds();
}
