package de.topobyte.osm4j.extra.relations.split;

import de.topobyte.osm4j.core.model.iface.OsmRelation;
import de.topobyte.osm4j.extra.batch.SizeBatch;

class RelationBatch extends SizeBatch<OsmRelation> {
   RelationBatch(int maxMembers) {
      super(maxMembers);
   }

   protected int size(OsmRelation element) {
      return element.getNumberOfMembers();
   }
}
