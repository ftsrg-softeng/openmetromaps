package de.topobyte.osm4j.extra.relations;

import de.topobyte.osm4j.core.model.iface.OsmRelation;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class RelationGroupSingle implements RelationGroup {
   private OsmRelation relation;

   public RelationGroupSingle(OsmRelation relation) {
      this.relation = relation;
   }

   @Override
   public long getId() {
      return this.relation.getId();
   }

   @Override
   public Collection<OsmRelation> getRelations() {
      List<OsmRelation> list = new ArrayList<>(1);
      list.add(this.relation);
      return list;
   }
}
