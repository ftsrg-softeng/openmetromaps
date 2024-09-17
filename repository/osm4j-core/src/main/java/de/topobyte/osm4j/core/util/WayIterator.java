package de.topobyte.osm4j.core.util;

import de.topobyte.osm4j.core.access.OsmIterator;
import de.topobyte.osm4j.core.model.iface.EntityType;
import de.topobyte.osm4j.core.model.iface.OsmWay;

public class WayIterator extends EntityIterator<OsmWay> {
   public WayIterator(OsmIterator iterator) {
      super(iterator, EntityType.Way);
   }
}
