package de.topobyte.osm4j.extra.relations;

import de.topobyte.osm4j.core.model.iface.OsmRelation;
import java.util.Collection;

public interface RelationGroup {
   long getId();

   Collection<OsmRelation> getRelations();
}
