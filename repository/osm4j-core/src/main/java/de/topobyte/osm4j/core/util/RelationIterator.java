package de.topobyte.osm4j.core.util;

import de.topobyte.osm4j.core.access.OsmIterator;
import de.topobyte.osm4j.core.model.iface.EntityType;
import de.topobyte.osm4j.core.model.iface.OsmRelation;

public class RelationIterator extends EntityIterator<OsmRelation> {
   public RelationIterator(OsmIterator iterator) {
      super(iterator, EntityType.Relation);
   }
}
