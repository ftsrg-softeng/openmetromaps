package de.topobyte.osm4j.extra.relations;

import de.topobyte.osm4j.core.model.iface.OsmRelation;
import java.util.Collection;

public class RelationGroupMultiple implements RelationGroup {
   private long id;
   private Collection<OsmRelation> relations;

   public RelationGroupMultiple(long id, Collection<OsmRelation> relations) {
      this.id = id;
      this.relations = relations;
   }

   @Override
   public long getId() {
      return this.id;
   }

   @Override
   public Collection<OsmRelation> getRelations() {
      return this.relations;
   }
}
