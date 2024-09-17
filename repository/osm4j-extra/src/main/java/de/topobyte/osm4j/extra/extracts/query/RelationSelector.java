package de.topobyte.osm4j.extra.extracts.query;

import com.slimjars.dist.gnu.trove.set.TLongSet;
import com.slimjars.dist.gnu.trove.set.hash.TLongHashSet;
import de.topobyte.osm4j.core.dataset.InMemoryListDataSet;
import de.topobyte.osm4j.core.model.iface.EntityType;
import de.topobyte.osm4j.core.model.iface.OsmRelation;
import de.topobyte.osm4j.core.model.iface.OsmRelationMember;
import de.topobyte.osm4j.core.model.util.OsmModelUtil;
import de.topobyte.osm4j.core.resolve.EntityNotFoundException;
import java.util.ArrayDeque;
import java.util.List;
import java.util.Queue;

public class RelationSelector {
   public InMemoryListDataSet select(RelationFilter filter, InMemoryListDataSet data) {
      InMemoryListDataSet result = new InMemoryListDataSet();
      List<OsmRelation> resultRelations = result.getRelations();
      List<OsmRelation> relations = data.getRelations();

      for (OsmRelation relation : relations) {
         if (filter.take(relation)) {
            resultRelations.add(relation);
         }
      }

      if (resultRelations.size() == relations.size()) {
         return result;
      } else {
         TLongSet ids = new TLongHashSet();

         for (OsmRelation relationx : resultRelations) {
            ids.add(relationx.getId());
         }

         Queue<OsmRelation> rQueue = new ArrayDeque<>(resultRelations);
         TLongSet idQueue = new TLongHashSet();

         while (!rQueue.isEmpty()) {
            OsmRelation next = rQueue.remove();

            for (OsmRelationMember member : OsmModelUtil.membersAsList(next)) {
               if (member.getType() == EntityType.Relation) {
                  long id = member.getId();
                  if (!ids.contains(id) && !idQueue.contains(id)) {
                     idQueue.add(id);
                  }
               }
            }

            for (long id : idQueue.toArray()) {
               try {
                  idQueue.remove(id);
                  OsmRelation relationx = data.getRelation(id);
                  resultRelations.add(relationx);
                  ids.add(relationx.getId());
                  rQueue.add(relationx);
               } catch (EntityNotFoundException var16) {
               }
            }
         }

         return result;
      }
   }
}
