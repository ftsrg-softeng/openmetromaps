package de.topobyte.osm4j.extra.extracts.query;

import com.google.common.collect.Sets;
import com.google.common.collect.Sets.SetView;
import com.slimjars.dist.gnu.trove.map.TLongObjectMap;
import com.slimjars.dist.gnu.trove.map.hash.TLongObjectHashMap;
import com.slimjars.dist.gnu.trove.set.TLongSet;
import de.topobyte.jts.utils.predicate.PredicateEvaluator;
import de.topobyte.osm4j.core.dataset.InMemoryListDataSet;
import de.topobyte.osm4j.core.model.iface.OsmRelation;
import de.topobyte.osm4j.core.resolve.EntityFinder;
import de.topobyte.osm4j.core.resolve.EntityFinders;
import de.topobyte.osm4j.core.resolve.EntityNotFoundException;
import de.topobyte.osm4j.core.resolve.EntityNotFoundStrategy;
import de.topobyte.osm4j.extra.MissingEntityCounter;
import de.topobyte.osm4j.extra.QueryUtil;
import de.topobyte.osm4j.extra.relations.Group;
import de.topobyte.osm4j.extra.relations.RelationGraph;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ComplexRelationsQuery extends AbstractRelationsQuery {
   static final Logger logger = LoggerFactory.getLogger(ComplexRelationsQuery.class);

   public ComplexRelationsQuery(
      InMemoryListDataSet dataNodes, InMemoryListDataSet dataWays, InMemoryListDataSet dataRelations, PredicateEvaluator test, boolean fastRelationTests
   ) {
      super(dataNodes, dataWays, dataRelations, test, fastRelationTests);
   }

   public void execute(RelationQueryBag queryBag) throws IOException {
      RelationGraph relationGraph = new RelationGraph(true, true);
      relationGraph.build(this.dataRelations.getRelations());
      List<Group> groups = relationGraph.buildGroups();
      logger.debug(String.format("This batch has %d groups and %d simple relations", groups.size(), relationGraph.getIdsSimpleRelations().size()));
      Set<OsmRelation> foundSimple = new HashSet<>();
      Set<OsmRelation> foundComplex = new HashSet<>();
      if (!relationGraph.getIdsSimpleRelations().isEmpty()) {
         this.executeSimple(queryBag, relationGraph.getIdsSimpleRelations(), foundSimple);
      }

      if (!groups.isEmpty()) {
         this.executeGroups(queryBag, groups, foundComplex);
      }

      SetView<OsmRelation> found = Sets.union(foundSimple, foundComplex);
      TLongObjectMap<OsmRelation> relations = new TLongObjectHashMap();

      for (OsmRelation relation : found) {
         relations.put(relation.getId(), relation);
      }

      logger.debug(String.format("writing %d relations", relations.size()));
      QueryUtil.writeRelations(relations, queryBag.outRelations.getOsmOutput());
   }

   private void executeSimple(RelationQueryBag queryBag, TLongSet simpleRelationIds, Set<OsmRelation> found) throws IOException {
      EntityFinder finder = EntityFinders.create(this.provider, EntityNotFoundStrategy.IGNORE);

      for (OsmRelation relation : this.dataRelations.getRelations()) {
         if (simpleRelationIds.contains(relation.getId()) && this.intersects(relation, queryBag, finder)) {
            found.add(relation);
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

   private void executeGroups(RelationQueryBag queryBag, List<Group> groups, Set<OsmRelation> found) throws IOException {
      EntityFinder finder = EntityFinders.create(this.provider, EntityNotFoundStrategy.IGNORE);

      for (Group group : groups) {
         TLongSet ids = group.getRelationIds();
         logger.debug(String.format("group with %d relations", ids.size()));

         List<OsmRelation> groupRelations;
         try {
            groupRelations = finder.findRelations(ids);
         } catch (EntityNotFoundException var16) {
            continue;
         }

         RelationGraph groupGraph = new RelationGraph(true, false);
         groupGraph.build(groupRelations);
         List<Group> groupGroups = groupGraph.buildGroups();
         logger.debug("subgroups: " + groupGroups.size());

         for (Group subGroup : groupGroups) {
            OsmRelation start;
            List<OsmRelation> subRelations;
            try {
               start = this.dataRelations.getRelation(subGroup.getStart());
               subRelations = finder.findRelations(subGroup.getRelationIds());
            } catch (EntityNotFoundException var17) {
               continue;
            }

            if (this.intersects(start, subRelations, queryBag, finder)) {
               found.addAll(subRelations);
            }
         }
      }

      queryBag.nComplex = queryBag.nComplex + found.size();

      for (OsmRelation relation : found) {
         MissingEntityCounter counter = new MissingEntityCounter();
         QueryUtil.putNodes(relation, queryBag.additionalNodes, this.dataNodes, queryBag.nodeIds, counter);
         QueryUtil.putWaysAndWayNodes(relation, queryBag.additionalNodes, queryBag.additionalWays, this.provider, queryBag.wayIds, counter);
         if (counter.nonZero()) {
            System.out.println(String.format("relation %d: unable to find %s", relation.getId(), counter.toMessage()));
         }
      }
   }
}
