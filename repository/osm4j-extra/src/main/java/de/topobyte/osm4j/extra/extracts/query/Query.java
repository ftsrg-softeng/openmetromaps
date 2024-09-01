package de.topobyte.osm4j.extra.extracts.query;

import com.slimjars.dist.gnu.trove.set.TLongSet;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Envelope;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryFactory;
import de.topobyte.jts.utils.predicate.PredicateEvaluator;
import de.topobyte.osm4j.core.access.OsmIterator;
import de.topobyte.osm4j.core.access.OsmIteratorInput;
import de.topobyte.osm4j.core.access.OsmStreamOutput;
import de.topobyte.osm4j.core.dataset.InMemoryListDataSet;
import de.topobyte.osm4j.core.model.iface.OsmNode;
import de.topobyte.osm4j.core.model.iface.OsmWay;
import de.topobyte.osm4j.extra.QueryUtil;
import de.topobyte.osm4j.extra.datatree.DataTree;
import de.topobyte.osm4j.extra.datatree.DataTreeFiles;
import de.topobyte.osm4j.extra.datatree.DataTreeOpener;
import de.topobyte.osm4j.extra.datatree.Node;
import de.topobyte.osm4j.extra.extracts.BatchFileNames;
import de.topobyte.osm4j.extra.extracts.ExtractionPaths;
import de.topobyte.osm4j.extra.extracts.TreeFileNames;
import de.topobyte.osm4j.extra.idbboxlist.IdBboxEntry;
import de.topobyte.osm4j.extra.idbboxlist.IdBboxUtil;
import de.topobyte.osm4j.utils.FileFormat;
import de.topobyte.osm4j.utils.OsmFileInput;
import de.topobyte.osm4j.utils.OsmOutputConfig;
import de.topobyte.osm4j.utils.merge.sorted.SortedMerge;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.io.FileUtils;

public class Query extends AbstractQuery {
   private Envelope queryEnvelope;
   private PredicateEvaluator test;
   private Path pathOutput;
   private Path pathTmp;
   private ExtractionPaths paths;
   private TreeFileNames treeNames;
   private BatchFileNames relationNames;
   private boolean keepTmp;
   private boolean fastRelationTests;
   private RelationFilter relationFilter;
   private Path pathTmpTreeNodes;
   private Path pathTmpTreeWays;
   private Path pathTmpTreeSimpleRelations;
   private Path pathTmpTreeComplexRelations;
   private Path pathTmpTreeAdditionalNodes;
   private Path pathTmpTreeAdditionalWays;
   private Path pathTmpSimpleNodes;
   private Path pathTmpSimpleWays;
   private Path pathTmpSimpleRelations;
   private Path pathTmpComplexNodes;
   private Path pathTmpComplexWays;
   private Path pathTmpComplexRelations;
   private GeometryFactory factory = new GeometryFactory();
   private DataTree tree;
   private DataTreeFiles filesTreeNodes;
   private DataTreeFiles filesTreeWays;
   private DataTreeFiles filesTreeSimpleRelations;
   private DataTreeFiles filesTreeComplexRelations;
   private List<OsmFileInput> filesNodes = new ArrayList<>();
   private List<OsmFileInput> filesWays = new ArrayList<>();
   private List<OsmFileInput> filesSimpleRelations = new ArrayList<>();
   private List<OsmFileInput> filesComplexRelations = new ArrayList<>();
   private int nNodes = 0;
   private int nWays = 0;
   private int nSimpleRelations = 0;
   private int nComplexRelations = 0;
   private int tmpIndexTree = 0;
   private int tmpIndexSimple = 0;
   private int tmpIndexComplex = 0;

   public Query(
      Envelope queryEnvelope,
      PredicateEvaluator test,
      Path pathOutput,
      Path pathTmp,
      ExtractionPaths paths,
      TreeFileNames treeNames,
      BatchFileNames relationNames,
      FileFormat inputFormat,
      OsmOutputConfig outputConfigIntermediate,
      OsmOutputConfig outputConfig,
      boolean keepTmp,
      boolean fastRelationTests,
      RelationFilter relationFilter
   ) {
      super(inputFormat, outputConfigIntermediate, outputConfig);
      this.queryEnvelope = queryEnvelope;
      this.test = test;
      this.pathOutput = pathOutput;
      this.pathTmp = pathTmp;
      this.paths = paths;
      this.treeNames = treeNames;
      this.relationNames = relationNames;
      this.keepTmp = keepTmp;
      this.fastRelationTests = fastRelationTests;
      this.relationFilter = relationFilter;
   }

   public void execute() throws IOException {
      this.createTemporaryDirectory();
      this.openTree();
      Geometry box = this.factory.toGeometry(this.queryEnvelope);

      for (Node leaf : this.tree.query(box)) {
         String leafName = Long.toHexString(leaf.getPath());
         if (this.test.contains(leaf.getEnvelope())) {
            System.out.println("Leaf is completely contained: " + leafName);
            this.addCompletelyContainedLeaf(leaf);
         } else {
            System.out.println("Loading data from leaf: " + leafName);
            this.addIntersectingLeaf(leaf);
         }
      }

      System.out.println(String.format("Total number of nodes: %d", this.nNodes));
      System.out.println(String.format("Total number of ways: %d", this.nWays));
      System.out.println(String.format("Total number of simple relations: %d", this.nSimpleRelations));
      System.out.println(String.format("Total number of complex relations: %d", this.nComplexRelations));
      List<IdBboxEntry> entriesSimple = IdBboxUtil.read(this.paths.getSimpleRelationsBboxes());
      List<IdBboxEntry> entriesComplex = IdBboxUtil.read(this.paths.getComplexRelationsBboxes());

      for (IdBboxEntry entry : entriesSimple) {
         long id = entry.getId();
         if (this.test.contains(entry.getEnvelope())) {
            System.out.println("Simple batch completely contained: " + id);
            this.addCompletelyContainedBatch(this.paths.getSimpleRelations(), id, this.filesSimpleRelations);
         } else if (this.test.intersects(entry.getEnvelope())) {
            System.out.println("Loading data from simple batch: " + id);
            this.tmpIndexSimple++;
            String tmpFilenames = this.filename(this.tmpIndexSimple);
            System.out.println("Writing to files: " + tmpFilenames);
            Path pathDir = this.paths.getSimpleRelations().resolve(Long.toString(entry.getId()));
            Path pathNodes = pathDir.resolve(this.relationNames.getNodes());
            Path pathWays = pathDir.resolve(this.relationNames.getWays());
            Path pathRelations = pathDir.resolve(this.relationNames.getRelations());
            Path pathOutNodes = this.pathTmpSimpleNodes.resolve(tmpFilenames);
            Path pathOutWays = this.pathTmpSimpleWays.resolve(tmpFilenames);
            Path pathOutRelations = this.pathTmpSimpleRelations.resolve(tmpFilenames);
            this.runRelationsQuery(true, tmpFilenames, pathNodes, pathWays, pathRelations, pathOutNodes, pathOutWays, pathOutRelations);
         }
      }

      for (IdBboxEntry entryx : entriesComplex) {
         long id = entryx.getId();
         if (this.test.contains(entryx.getEnvelope())) {
            System.out.println("Complex batch completely contained: " + id);
            this.addCompletelyContainedBatch(this.paths.getComplexRelations(), id, this.filesComplexRelations);
         } else if (this.test.intersects(entryx.getEnvelope())) {
            System.out.println("Loading data from complex batch: " + id);
            this.tmpIndexComplex++;
            String tmpFilenames = this.filename(this.tmpIndexComplex);
            System.out.println("Writing to files: " + tmpFilenames);
            Path pathDir = this.paths.getComplexRelations().resolve(Long.toString(entryx.getId()));
            Path pathNodes = pathDir.resolve(this.relationNames.getNodes());
            Path pathWays = pathDir.resolve(this.relationNames.getWays());
            Path pathRelations = pathDir.resolve(this.relationNames.getRelations());
            Path pathOutNodes = this.pathTmpComplexNodes.resolve(tmpFilenames);
            Path pathOutWays = this.pathTmpComplexWays.resolve(tmpFilenames);
            Path pathOutRelations = this.pathTmpComplexRelations.resolve(tmpFilenames);
            this.runRelationsQuery(false, tmpFilenames, pathNodes, pathWays, pathRelations, pathOutNodes, pathOutWays, pathOutRelations);
         }
      }

      OsmStreamOutput output = this.createFinalOutput(this.pathOutput);
      List<OsmFileInput> mergeFiles = new ArrayList<>();
      mergeFiles.addAll(this.filesNodes);
      mergeFiles.addAll(this.filesWays);
      mergeFiles.addAll(this.filesSimpleRelations);
      mergeFiles.addAll(this.filesComplexRelations);
      System.out.println(String.format("Merging %d files", mergeFiles.size()));
      List<OsmIteratorInput> mergeIteratorInputs = new ArrayList<>();
      List<OsmIterator> mergeIterators = new ArrayList<>();

      for (OsmFileInput input : mergeFiles) {
         OsmIteratorInput iteratorInput = input.createIterator(true, this.outputConfig.isWriteMetadata());
         mergeIteratorInputs.add(iteratorInput);
         mergeIterators.add(iteratorInput.getIterator());
      }

      SortedMerge merge = new SortedMerge(output.getOsmOutput(), mergeIterators);
      merge.run();

      for (OsmIteratorInput input : mergeIteratorInputs) {
         input.close();
      }

      output.close();
      if (!this.keepTmp) {
         FileUtils.deleteDirectory(this.pathTmp.toFile());
      }
   }

   private void createTemporaryDirectory() throws IOException {
      if (this.pathTmp == null) {
         this.pathTmp = Files.createTempDirectory("extract");
      }

      System.out.println("Temporary directory: " + this.pathTmp);
      Files.createDirectories(this.pathTmp);
      if (!Files.isDirectory(this.pathTmp)) {
         System.out.println("Unable to create temporary directory for intermediate files");
         System.exit(1);
      }

      if (this.pathTmp.toFile().listFiles().length != 0) {
         System.out.println("Temporary directory for intermediate files is not empty");
         System.exit(1);
      }

      System.out.println("Storing intermediate files here: " + this.pathTmp);
      Path pathTmpTree = this.pathTmp.resolve("tree");
      Path pathTmpSimple = this.pathTmp.resolve("simple-relations");
      Path pathTmpComplex = this.pathTmp.resolve("complex-relations");
      this.pathTmpTreeNodes = pathTmpTree.resolve("nodes");
      this.pathTmpTreeWays = pathTmpTree.resolve("ways");
      this.pathTmpTreeSimpleRelations = pathTmpTree.resolve("relations.simple");
      this.pathTmpTreeComplexRelations = pathTmpTree.resolve("relations.complex");
      this.pathTmpTreeAdditionalNodes = pathTmpTree.resolve("nodes-extra");
      this.pathTmpTreeAdditionalWays = pathTmpTree.resolve("ways-extra");
      this.pathTmpSimpleNodes = pathTmpSimple.resolve("nodes");
      this.pathTmpSimpleWays = pathTmpSimple.resolve("ways");
      this.pathTmpSimpleRelations = pathTmpSimple.resolve("relations");
      this.pathTmpComplexNodes = pathTmpComplex.resolve("nodes");
      this.pathTmpComplexWays = pathTmpComplex.resolve("ways");
      this.pathTmpComplexRelations = pathTmpComplex.resolve("relations");
      Files.createDirectory(pathTmpTree);
      Files.createDirectory(pathTmpSimple);
      Files.createDirectory(pathTmpComplex);
      Files.createDirectory(this.pathTmpTreeNodes);
      Files.createDirectory(this.pathTmpTreeWays);
      Files.createDirectory(this.pathTmpTreeSimpleRelations);
      Files.createDirectory(this.pathTmpTreeComplexRelations);
      Files.createDirectory(this.pathTmpTreeAdditionalNodes);
      Files.createDirectory(this.pathTmpTreeAdditionalWays);
      Files.createDirectory(this.pathTmpSimpleNodes);
      Files.createDirectory(this.pathTmpSimpleWays);
      Files.createDirectory(this.pathTmpSimpleRelations);
      Files.createDirectory(this.pathTmpComplexNodes);
      Files.createDirectory(this.pathTmpComplexWays);
      Files.createDirectory(this.pathTmpComplexRelations);
   }

   private void openTree() throws IOException {
      Path pathTree = this.paths.getTree();
      this.tree = DataTreeOpener.open(pathTree.toFile());
      this.filesTreeNodes = new DataTreeFiles(pathTree, this.treeNames.getNodes());
      this.filesTreeWays = new DataTreeFiles(pathTree, this.treeNames.getWays());
      this.filesTreeSimpleRelations = new DataTreeFiles(pathTree, this.treeNames.getSimpleRelations());
      this.filesTreeComplexRelations = new DataTreeFiles(pathTree, this.treeNames.getComplexRelations());
   }

   private OsmFileInput input(Path path) {
      return new OsmFileInput(path, this.inputFormat);
   }

   private OsmFileInput intermediate(Path path) {
      return new OsmFileInput(path, this.outputConfigIntermediate.getFileFormat());
   }

   private void addCompletelyContainedLeaf(Node leaf) {
      this.filesNodes.add(this.input(this.filesTreeNodes.getPath(leaf)));
      this.filesWays.add(this.input(this.filesTreeWays.getPath(leaf)));
      this.filesSimpleRelations.add(this.input(this.filesTreeSimpleRelations.getPath(leaf)));
      this.filesComplexRelations.add(this.input(this.filesTreeComplexRelations.getPath(leaf)));
   }

   private void addIntersectingLeaf(Node leaf) throws IOException {
      LeafQuery leafQuery = new LeafQuery(
         this.test,
         this.filesTreeNodes,
         this.filesTreeWays,
         this.filesTreeSimpleRelations,
         this.filesTreeComplexRelations,
         this.inputFormat,
         this.outputConfigIntermediate,
         this.outputConfig,
         this.fastRelationTests
      );
      this.tmpIndexTree++;
      String tmpFilenames = this.filename(this.tmpIndexTree);
      Path pathOutNodes = this.pathTmpTreeNodes.resolve(tmpFilenames);
      Path pathOutWays = this.pathTmpTreeWays.resolve(tmpFilenames);
      Path pathOutSimpleRelations = this.pathTmpTreeSimpleRelations.resolve(tmpFilenames);
      Path pathOutComplexRelations = this.pathTmpTreeComplexRelations.resolve(tmpFilenames);
      Path pathOutAdditionalNodes = this.pathTmpTreeAdditionalNodes.resolve(tmpFilenames);
      Path pathOutAdditionalWays = this.pathTmpTreeAdditionalWays.resolve(tmpFilenames);
      QueryResult results = leafQuery.execute(
         leaf, pathOutNodes, pathOutWays, pathOutSimpleRelations, pathOutComplexRelations, pathOutAdditionalNodes, pathOutAdditionalWays
      );
      this.nNodes = this.nNodes + results.getNumNodes();
      this.nWays = this.nWays + results.getNumWays();
      this.nSimpleRelations = this.nSimpleRelations + results.getNumSimpleRelations();
      this.nComplexRelations = this.nComplexRelations + results.getNumComplexRelations();
      this.filesNodes.add(this.intermediate(pathOutNodes));
      this.filesNodes.add(this.intermediate(pathOutAdditionalNodes));
      this.filesWays.add(this.intermediate(pathOutWays));
      this.filesWays.add(this.intermediate(pathOutAdditionalWays));
      this.filesSimpleRelations.add(this.intermediate(pathOutSimpleRelations));
      this.filesComplexRelations.add(this.intermediate(pathOutComplexRelations));
      System.out.println(String.format("Found %d nodes", results.getNumNodes()));
      System.out.println(String.format("Found %d ways", results.getNumWays()));
      System.out.println(String.format("Found %d simple relations", results.getNumSimpleRelations()));
      System.out.println(String.format("Found %d complex relations", results.getNumComplexRelations()));
   }

   private void addCompletelyContainedBatch(Path pathRelations, long id, List<OsmFileInput> filesRelations) {
      Path path = pathRelations.resolve(Long.toString(id));
      this.filesNodes.add(this.input(path.resolve(this.relationNames.getNodes())));
      this.filesWays.add(this.input(path.resolve(this.relationNames.getWays())));
      filesRelations.add(this.input(path.resolve(this.relationNames.getRelations())));
   }

   private void runRelationsQuery(
      boolean simple, String tmpFilenames, Path pathNodes, Path pathWays, Path pathRelations, Path pathOutNodes, Path pathOutWays, Path pathOutRelations
   ) throws IOException {
      System.out.println("loading data");
      InMemoryListDataSet dataRelations = this.read(pathRelations);
      dataRelations.sort();
      InMemoryListDataSet selectedRelations;
      if (this.relationFilter == null) {
         selectedRelations = dataRelations;
      } else {
         selectedRelations = new RelationSelector().select(this.relationFilter, dataRelations);
         selectedRelations.sort();
         System.out.println(String.format("selected %d of %d relations", selectedRelations.getRelations().size(), dataRelations.getRelations().size()));
      }

      if (selectedRelations.getRelations().isEmpty()) {
         System.out.println("nothing selected, skipping");
      } else {
         InMemoryListDataSet dataNodes = this.read(pathNodes);
         InMemoryListDataSet dataWays = this.read(pathWays);
         OsmStreamOutput outRelations = this.createOutput(pathOutRelations);
         RelationQueryBag queryBag = new RelationQueryBag(outRelations);
         System.out.println("running query");
         this.queryNodes(dataNodes, queryBag.nodeIds);
         this.queryWays(dataWays, queryBag.nodeIds, queryBag.wayIds);
         if (simple) {
            SimpleRelationsQuery simpleRelationsQuery = new SimpleRelationsQuery(dataNodes, dataWays, selectedRelations, this.test, this.fastRelationTests);
            simpleRelationsQuery.execute(queryBag);
         } else {
            ComplexRelationsQuery complexRelationsQuery = new ComplexRelationsQuery(dataNodes, dataWays, selectedRelations, this.test, this.fastRelationTests);
            complexRelationsQuery.execute(queryBag);
         }

         this.finish(outRelations);
         System.out.println("writing nodes and ways");
         OsmStreamOutput outputNodes = this.createOutput(pathOutNodes);
         QueryUtil.writeNodes(queryBag.additionalNodes, outputNodes.getOsmOutput());
         this.finish(outputNodes);
         OsmStreamOutput outputWays = this.createOutput(pathOutWays);
         QueryUtil.writeWays(queryBag.additionalWays, outputWays.getOsmOutput());
         this.finish(outputWays);
         this.filesNodes.add(this.intermediate(pathOutNodes));
         this.filesWays.add(this.intermediate(pathOutWays));
         this.filesSimpleRelations.add(this.intermediate(pathOutRelations));
      }
   }

   private void queryNodes(InMemoryListDataSet dataNodes, TLongSet nodeIds) throws IOException {
      for (OsmNode node : dataNodes.getNodes()) {
         if (this.test.contains(new Coordinate(node.getLongitude(), node.getLatitude()))) {
            nodeIds.add(node.getId());
         }
      }
   }

   private void queryWays(InMemoryListDataSet dataWays, TLongSet nodeIds, TLongSet wayIds) throws IOException {
      for (OsmWay way : dataWays.getWays()) {
         boolean in = QueryUtil.anyNodeContainedIn(way, nodeIds);
         if (in) {
            wayIds.add(way.getId());
         }
      }
   }
}
