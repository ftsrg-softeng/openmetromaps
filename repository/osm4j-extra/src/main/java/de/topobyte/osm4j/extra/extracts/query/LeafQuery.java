package de.topobyte.osm4j.extra.extracts.query;

import com.slimjars.dist.gnu.trove.map.TLongObjectMap;
import com.slimjars.dist.gnu.trove.map.hash.TLongObjectHashMap;
import com.slimjars.dist.gnu.trove.set.TLongSet;
import com.slimjars.dist.gnu.trove.set.hash.TLongHashSet;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import de.topobyte.jts.utils.GeometryGroup;
import de.topobyte.jts.utils.predicate.PredicateEvaluator;
import de.topobyte.osm4j.core.access.OsmStreamOutput;
import de.topobyte.osm4j.core.dataset.InMemoryListDataSet;
import de.topobyte.osm4j.core.model.iface.OsmNode;
import de.topobyte.osm4j.core.model.iface.OsmWay;
import de.topobyte.osm4j.core.resolve.EntityNotFoundException;
import de.topobyte.osm4j.extra.QueryUtil;
import de.topobyte.osm4j.extra.datatree.DataTreeFiles;
import de.topobyte.osm4j.extra.datatree.Node;
import de.topobyte.osm4j.geometry.WayBuilder;
import de.topobyte.osm4j.geometry.WayBuilderResult;
import de.topobyte.osm4j.utils.FileFormat;
import de.topobyte.osm4j.utils.OsmOutputConfig;
import java.io.IOException;
import java.nio.file.Path;

public class LeafQuery extends AbstractQuery {
   private PredicateEvaluator test;
   private DataTreeFiles filesTreeNodes;
   private DataTreeFiles filesTreeWays;
   private DataTreeFiles filesTreeSimpleRelations;
   private DataTreeFiles filesTreeComplexRelations;
   private boolean fastRelationTests;
   private Path pathOutNodes;
   private Path pathOutWays;
   private Path pathOutAdditionalNodes;
   private Path pathOutAdditionalWays;
   private Path pathOutSimpleRelations;
   private Path pathOutComplexRelations;
   private OsmStreamOutput outNodes;
   private OsmStreamOutput outWays;
   private OsmStreamOutput outSimpleRelations;
   private OsmStreamOutput outComplexRelations;
   private InMemoryListDataSet dataNodes;
   private InMemoryListDataSet dataWays;
   private InMemoryListDataSet dataSimpleRelations;
   private InMemoryListDataSet dataComplexRelations;
   private TLongSet nodeIds = new TLongHashSet();
   private TLongSet wayIds = new TLongHashSet();
   private TLongObjectMap<OsmNode> additionalNodes = new TLongObjectHashMap();
   private TLongObjectMap<OsmWay> additionalWays = new TLongObjectHashMap();
   private GeometryFactory factory = new GeometryFactory();
   private WayBuilder wayBuilder = new WayBuilder(this.factory);

   public LeafQuery(
      PredicateEvaluator test,
      DataTreeFiles filesTreeNodes,
      DataTreeFiles filesTreeWays,
      DataTreeFiles filesTreeSimpleRelations,
      DataTreeFiles filesTreeComplexRelations,
      FileFormat inputFormat,
      OsmOutputConfig outputConfigIntermediate,
      OsmOutputConfig outputConfig,
      boolean fastRelationsTests
   ) {
      super(inputFormat, outputConfigIntermediate, outputConfig);
      this.test = test;
      this.filesTreeNodes = filesTreeNodes;
      this.filesTreeWays = filesTreeWays;
      this.filesTreeSimpleRelations = filesTreeSimpleRelations;
      this.filesTreeComplexRelations = filesTreeComplexRelations;
      this.fastRelationTests = fastRelationsTests;
   }

   public QueryResult execute(
      Node leaf,
      Path pathOutNodes,
      Path pathOutWays,
      Path pathOutSimpleRelations,
      Path pathOutComplexRelations,
      Path pathOutAdditionalNodes,
      Path pathOutAdditionalWays
   ) throws IOException {
      this.pathOutNodes = pathOutNodes;
      this.pathOutWays = pathOutWays;
      this.pathOutSimpleRelations = pathOutSimpleRelations;
      this.pathOutComplexRelations = pathOutComplexRelations;
      this.pathOutAdditionalNodes = pathOutAdditionalNodes;
      this.pathOutAdditionalWays = pathOutAdditionalWays;
      System.out.println("loading data");
      this.readData(leaf);
      this.createOutputs();
      System.out.println("querying nodes");
      this.queryNodes();
      System.out.println("querying ways");
      this.queryWays();
      System.out.println("querying simple relations");
      RelationQueryBag queryBagSimple = new RelationQueryBag(this.outSimpleRelations, this.additionalNodes, this.additionalWays, this.nodeIds, this.wayIds);
      SimpleRelationsQuery simpleRelationsQuery = new SimpleRelationsQuery(
         this.dataNodes, this.dataWays, this.dataSimpleRelations, this.test, this.fastRelationTests
      );
      simpleRelationsQuery.execute(queryBagSimple);
      System.out.println("querying complex relations");
      RelationQueryBag queryBagComplex = new RelationQueryBag(this.outComplexRelations, this.additionalNodes, this.additionalWays, this.nodeIds, this.wayIds);
      ComplexRelationsQuery complexRelationsQuery = new ComplexRelationsQuery(
         this.dataNodes, this.dataWays, this.dataComplexRelations, this.test, this.fastRelationTests
      );
      complexRelationsQuery.execute(queryBagComplex);
      System.out.println("writing additional nodes");
      this.writeAdditionalNodes();
      System.out.println("writing additional ways");
      this.writeAdditionalWays();
      System.out.println("closing output");
      this.finishOutputs();
      return new QueryResult(this.nodeIds.size(), this.wayIds.size(), queryBagSimple.nSimple, queryBagComplex.nComplex);
   }

   private void createOutputs() throws IOException {
      this.outNodes = this.createOutput(this.pathOutNodes);
      this.outWays = this.createOutput(this.pathOutWays);
      this.outSimpleRelations = this.createOutput(this.pathOutSimpleRelations);
      this.outComplexRelations = this.createOutput(this.pathOutComplexRelations);
   }

   private void finishOutputs() throws IOException {
      this.finish(this.outNodes);
      this.finish(this.outWays);
      this.finish(this.outSimpleRelations);
      this.finish(this.outComplexRelations);
   }

   private void readData(Node leaf) throws IOException {
      this.dataNodes = this.read(this.filesTreeNodes.getPath(leaf));
      this.dataWays = this.read(this.filesTreeWays.getPath(leaf));
      this.dataSimpleRelations = this.read(this.filesTreeSimpleRelations.getPath(leaf));
      this.dataComplexRelations = this.read(this.filesTreeComplexRelations.getPath(leaf));
   }

   private void queryNodes() throws IOException {
      for (OsmNode node : this.dataNodes.getNodes()) {
         if (this.test.contains(new Coordinate(node.getLongitude(), node.getLatitude()))) {
            this.nodeIds.add(node.getId());
            this.outNodes.getOsmOutput().write(node);
         }
      }
   }

   private void queryWays() throws IOException {
      for (OsmWay way : this.dataWays.getWays()) {
         boolean in = QueryUtil.anyNodeContainedIn(way, this.nodeIds);
         if (!in && way.getNumberOfNodes() > 1) {
            try {
               WayBuilderResult result = this.wayBuilder.build(way, this.dataNodes);
               GeometryGroup group = result.toGeometryGroup(this.factory);
               if (this.test.intersects(group)) {
                  in = true;
               }
            } catch (EntityNotFoundException var6) {
               System.out.println("Unable to build way: " + way.getId());
            }
         }

         if (in) {
            this.wayIds.add(way.getId());
            this.outWays.getOsmOutput().write(way);

            try {
               QueryUtil.putNodes(way, this.additionalNodes, this.dataNodes, this.nodeIds);
            } catch (EntityNotFoundException var7) {
               System.out.println("Unable to find all nodes for way: " + way.getId());
            }
         }
      }
   }

   private void writeAdditionalNodes() throws IOException {
      OsmStreamOutput output = this.createOutput(this.pathOutAdditionalNodes);
      QueryUtil.writeNodes(this.additionalNodes, output.getOsmOutput());
      this.finish(output);
   }

   private void writeAdditionalWays() throws IOException {
      OsmStreamOutput output = this.createOutput(this.pathOutAdditionalWays);
      QueryUtil.writeWays(this.additionalWays, output.getOsmOutput());
      this.finish(output);
   }
}
