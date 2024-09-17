package de.topobyte.osm4j.extra.relations;

import de.topobyte.osm4j.core.model.iface.OsmRelation;
import de.topobyte.osm4j.core.resolve.OsmEntityProvider;

public class RelationGroupUtil {
   public static int groupSize(Group group, OsmEntityProvider data) {
      int n = 0;

      for (long member : group.getRelationIds().toArray()) {
         try {
            OsmRelation relation = data.getRelation(member);
            n += relation.getNumberOfMembers();
         } catch (Exception var9) {
         }
      }

      return n;
   }
}
