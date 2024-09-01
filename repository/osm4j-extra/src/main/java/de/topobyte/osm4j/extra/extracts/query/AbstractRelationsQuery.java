package de.topobyte.osm4j.extra.extracts.query;

import com.vividsolutions.jts.geom.Envelope;
import com.vividsolutions.jts.geom.GeometryFactory;
import de.topobyte.jts.utils.GeometryGroup;
import de.topobyte.jts.utils.predicate.PredicateEvaluator;
import de.topobyte.osm4j.core.dataset.InMemoryListDataSet;
import de.topobyte.osm4j.core.model.iface.OsmNode;
import de.topobyte.osm4j.core.model.iface.OsmRelation;
import de.topobyte.osm4j.core.resolve.CompositeOsmEntityProvider;
import de.topobyte.osm4j.core.resolve.EntityFinder;
import de.topobyte.osm4j.core.resolve.EntityNotFoundException;
import de.topobyte.osm4j.extra.QueryUtil;
import de.topobyte.osm4j.geometry.BboxBuilder;
import de.topobyte.osm4j.geometry.LineworkBuilder;
import de.topobyte.osm4j.geometry.LineworkBuilderResult;
import de.topobyte.osm4j.geometry.MissingEntitiesStrategy;
import de.topobyte.osm4j.geometry.MissingWayNodeStrategy;
import de.topobyte.osm4j.geometry.RegionBuilder;
import de.topobyte.osm4j.geometry.RegionBuilderResult;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class AbstractRelationsQuery {
   protected InMemoryListDataSet dataNodes;
   protected InMemoryListDataSet dataWays;
   protected InMemoryListDataSet dataRelations;
   protected PredicateEvaluator test;
   protected boolean fastRelationTests;
   protected CompositeOsmEntityProvider provider;
   protected GeometryFactory factory = new GeometryFactory();
   protected LineworkBuilder lineworkBuilder = new LineworkBuilder(this.factory);
   protected RegionBuilder regionBuilder = new RegionBuilder(this.factory);

   public AbstractRelationsQuery(
      InMemoryListDataSet dataNodes, InMemoryListDataSet dataWays, InMemoryListDataSet dataRelations, PredicateEvaluator test, boolean fastRelationTests
   ) {
      this.dataNodes = dataNodes;
      this.dataWays = dataWays;
      this.dataRelations = dataRelations;
      this.test = test;
      this.fastRelationTests = fastRelationTests;
      this.provider = new CompositeOsmEntityProvider(dataNodes, dataWays, dataRelations);
      this.lineworkBuilder.setMissingEntitiesStrategy(MissingEntitiesStrategy.BUILD_PARTIAL);
      this.lineworkBuilder.setMissingWayNodeStrategy(MissingWayNodeStrategy.SPLIT_POLYLINE);
      this.regionBuilder.setMissingEntitiesStrategy(MissingEntitiesStrategy.BUILD_PARTIAL);
      this.regionBuilder.setMissingWayNodeStrategy(MissingWayNodeStrategy.OMIT_VERTEX_FROM_POLYLINE);
   }

   protected boolean intersects(OsmRelation relation, RelationQueryBag queryBag, EntityFinder finder) {
      if (QueryUtil.anyMemberContainedIn(relation, queryBag.nodeIds, queryBag.wayIds)) {
         return true;
      } else {
         Set<OsmNode> nodes = new HashSet<>();

         try {
            finder.findMemberNodesAndWayNodes(relation, nodes);
         } catch (EntityNotFoundException var10) {
         }

         Envelope envelope = BboxBuilder.box(nodes);
         if (this.test.intersects(envelope)) {
            if (this.fastRelationTests) {
               return true;
            } else {
               try {
                  LineworkBuilderResult result = this.lineworkBuilder.build(relation, this.provider);
                  GeometryGroup group = result.toGeometryGroup(this.factory);
                  if (this.test.intersects(group)) {
                     return true;
                  }
               } catch (EntityNotFoundException var9) {
                  System.out.println("Unable to build relation: " + relation.getId());
               }

               try {
                  RegionBuilderResult result = this.regionBuilder.build(relation, this.provider);
                  GeometryGroup group = result.toGeometryGroup(this.factory);
                  if (this.test.intersects(group)) {
                     return true;
                  }
               } catch (EntityNotFoundException var8) {
                  System.out.println("Unable to build relation: " + relation.getId());
               }

               return false;
            }
         } else {
            return false;
         }
      }
   }

   protected boolean intersects(OsmRelation start, List<OsmRelation> relations, RelationQueryBag queryBag, EntityFinder finder) throws IOException {
      if (QueryUtil.anyMemberContainedIn(relations, queryBag.nodeIds, queryBag.wayIds)) {
         return true;
      } else {
         Set<OsmNode> nodes = new HashSet<>();

         try {
            finder.findMemberNodesAndWayNodes(relations, nodes);
         } catch (EntityNotFoundException var11) {
         }

         Envelope envelope = BboxBuilder.box(nodes);
         if (this.test.intersects(envelope)) {
            if (this.fastRelationTests) {
               return true;
            } else {
               try {
                  LineworkBuilderResult result = this.lineworkBuilder.build(relations, this.provider);
                  GeometryGroup group = result.toGeometryGroup(this.factory);
                  if (this.test.intersects(group)) {
                     return true;
                  }
               } catch (EntityNotFoundException var10) {
                  System.out.println("Unable to build relation group");
               }

               try {
                  RegionBuilderResult result = this.regionBuilder.build(start, this.provider);
                  GeometryGroup group = result.toGeometryGroup(this.factory);
                  if (this.test.intersects(group)) {
                     return true;
                  }
               } catch (EntityNotFoundException var9) {
                  System.out.println("Unable to build relation group");
               }

               return false;
            }
         } else {
            return false;
         }
      }
   }
}
