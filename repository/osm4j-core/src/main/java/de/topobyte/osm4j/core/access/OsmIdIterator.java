package de.topobyte.osm4j.core.access;

import de.topobyte.osm4j.core.model.iface.IdContainer;
import de.topobyte.osm4j.core.model.iface.OsmBounds;
import java.util.Iterator;

public interface OsmIdIterator extends Iterable<IdContainer>, Iterator<IdContainer> {
   boolean hasBounds();

   OsmBounds getBounds();
}
