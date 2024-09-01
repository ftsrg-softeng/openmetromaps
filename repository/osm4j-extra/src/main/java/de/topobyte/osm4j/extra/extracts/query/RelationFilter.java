package de.topobyte.osm4j.extra.extracts.query;

import de.topobyte.osm4j.core.model.iface.OsmRelation;

public interface RelationFilter {
   boolean take(OsmRelation var1);
}
