package de.topobyte.osm4j.extra.extracts.query;

import de.topobyte.jts.utils.predicate.PredicateEvaluator;
import de.topobyte.osm4j.core.dataset.InMemoryListDataSet;
import de.topobyte.osm4j.core.model.iface.OsmRelation;
import de.topobyte.osm4j.core.resolve.EntityFinder;
import de.topobyte.osm4j.core.resolve.EntityFinders;
import de.topobyte.osm4j.core.resolve.EntityNotFoundStrategy;
import de.topobyte.osm4j.extra.MissingEntityCounter;
import de.topobyte.osm4j.extra.QueryUtil;
import java.io.IOException;

public class SimpleRelationsQuery extends AbstractRelationsQuery {
   public SimpleRelationsQuery(
      InMemoryListDataSet dataNodes, InMemoryListDataSet dataWays, InMemoryListDataSet dataRelations, PredicateEvaluator test, boolean fastRelationTests
   ) {
      super(dataNodes, dataWays, dataRelations, test, fastRelationTests);
   }

   public void execute(RelationQueryBag queryBag) throws IOException {
      EntityFinder finder = EntityFinders.create(this.provider, EntityNotFoundStrategy.IGNORE);

      for (OsmRelation relation : this.dataRelations.getRelations()) {
         if (this.intersects(relation, queryBag, finder)) {
            queryBag.outRelations.getOsmOutput().write(relation);
            queryBag.nSimple++;
            MissingEntityCounter counter = new MissingEntityCounter();
            QueryUtil.putNodes(relation, queryBag.additionalNodes, this.dataNodes, queryBag.nodeIds, counter);
            QueryUtil.putWaysAndWayNodes(relation, queryBag.additionalNodes, queryBag.additionalWays, this.provider, queryBag.wayIds, counter);
            if (counter.nonZero()) {
               System.out.println(String.format("relation %d: unable to find %s", relation.getId(), counter.toMessage()));
            }
         }
      }
   }
}
