package de.topobyte.osm4j.extra.extracts;

import de.topobyte.adt.geo.BBox;
import de.topobyte.adt.geo.BBoxString;
import de.topobyte.osm4j.core.access.OsmInputException;
import de.topobyte.osm4j.core.access.OsmIteratorInput;
import de.topobyte.osm4j.core.model.iface.OsmBounds;
import de.topobyte.osm4j.extra.batch.BatchFilesUtil;
import de.topobyte.osm4j.extra.datatree.DataTree;
import de.topobyte.osm4j.extra.datatree.DataTreeBoxGeometryCreator;
import de.topobyte.osm4j.extra.datatree.DataTreeFiles;
import de.topobyte.osm4j.extra.datatree.DataTreeUtil;
import de.topobyte.osm4j.extra.datatree.merge.ThreadedTreeFilesMerger;
import de.topobyte.osm4j.extra.datatree.merge.TreeFilesMerger;
import de.topobyte.osm4j.extra.datatree.nodetree.NodeTreeCreatorMaxNodes;
import de.topobyte.osm4j.extra.datatree.nodetree.count.NodeTreeLeafCounterFactory;
import de.topobyte.osm4j.extra.datatree.nodetree.count.ThreadedNodeTreeLeafCounterFactory;
import de.topobyte.osm4j.extra.datatree.nodetree.distribute.NodeTreeDistributorFactory;
import de.topobyte.osm4j.extra.datatree.nodetree.distribute.ThreadedNodeTreeDistributorFactory;
import de.topobyte.osm4j.extra.datatree.output.ClosingDataTreeOutputFactory;
import de.topobyte.osm4j.extra.datatree.output.DataTreeOutputFactory;
import de.topobyte.osm4j.extra.datatree.sort.TreeFileSorter;
import de.topobyte.osm4j.extra.datatree.ways.MissingWayNodesExtractor;
import de.topobyte.osm4j.extra.datatree.ways.MissingWayNodesFinder;
import de.topobyte.osm4j.extra.datatree.ways.ThreadedMissingWayNodesFinder;
import de.topobyte.osm4j.extra.datatree.ways.ThreadedWaysDistributor;
import de.topobyte.osm4j.extra.datatree.ways.ThreadedWaysToTreeMapper;
import de.topobyte.osm4j.extra.datatree.ways.WaysDistributor;
import de.topobyte.osm4j.extra.datatree.ways.WaysToTreeMapper;
import de.topobyte.osm4j.extra.idbboxlist.IdBboxListGeometryCreator;
import de.topobyte.osm4j.extra.relations.ComplexRelationsDistributor;
import de.topobyte.osm4j.extra.relations.NonTreeRelationsSplitter;
import de.topobyte.osm4j.extra.relations.RelationsSeparator;
import de.topobyte.osm4j.extra.relations.RelationsSplitterAndMemberCollector;
import de.topobyte.osm4j.extra.relations.SimpleRelationsDistributor;
import de.topobyte.osm4j.extra.ways.ThreadedWaysSorterByFirstNodeId;
import de.topobyte.osm4j.extra.ways.WaysSorterByFirstNodeId;
import de.topobyte.osm4j.utils.FileFormat;
import de.topobyte.osm4j.utils.OsmFileInput;
import de.topobyte.osm4j.utils.OsmIoUtils;
import de.topobyte.osm4j.utils.OsmOutputConfig;
import de.topobyte.osm4j.utils.OsmUtils;
import de.topobyte.osm4j.utils.config.limit.ElementCountLimit;
import de.topobyte.osm4j.utils.config.limit.RelationMemberLimit;
import de.topobyte.osm4j.utils.config.limit.WayNodeLimit;
import de.topobyte.osm4j.utils.split.ThreadedEntitySplitter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.io.FileUtils;

public class ExtractionFilesBuilder {
   private static final String KEY_TOTAL = "total";
   private static final String KEY_SPLIT = "split";
   private static final String KEY_COMPUTE_BBOX = "compute bbox";
   private static final String KEY_NODE_TREE = "build nodetree";
   private static final String KEY_SORT_WAYS = "sort ways by first node id";
   private static final String KEY_MAP_WAYS = "map ways to tree";
   private static final String KEY_FIND_MISSING_WAY_NODES = "find missing way nodes";
   private static final String KEY_EXTRACT_MISSING_WAY_NODES = "extract missing way nodes";
   private static final String KEY_DISTRIBUTE_WAYS = "distribute ways";
   private static final String KEY_MERGE_NODES = "merge tree node files";
   private static final String KEY_MERGE_WAYS = "merge tree way files";
   private static final String KEY_SEPARATE_RELATIONS = "separate simple/complex relations";
   private static final String KEY_SPLIT_RELATIONS = "split relations, collect members";
   private static final String KEY_DISTRIBUTE_RELATIONS = "distribute relations";
   private static final String KEY_SORT_COMPLEX_RELATIONS = "sort complex tree relations";
   private static final String KEY_SORT_RELATIONS = "sort non-tree relations";
   private static final String KEY_CLEAN_UP = "clean up";
   private static final String KEY_CREATE_GEOMETRIES = "create geometries";
   private static final int SPLIT_INITIAL = 20;
   private static final int SPLIT_ITERATION = 8;
   private Path pathInput;
   private FileFormat inputFormat;
   private Path pathOutput;
   private FileFormat outputFormat;
   private ExtractionFileNames fileNames;
   private int maxNodes;
   private boolean includeMetadata;
   private int maxMembersSimple;
   private int maxMembersComplex;
   private boolean computeBbox;
   private Path pathTree;
   private Path pathWaysByNodes;
   private Path pathNodes;
   private Path pathWays;
   private Path pathRelations;
   private Path pathSimpleRelations;
   private Path pathComplexRelations;
   private Path pathSimpleRelationsDir;
   private Path pathComplexRelationsDir;
   private Path pathSimpleRelationsNonTree;
   private Path pathComplexRelationsNonTree;
   private Path pathSimpleRelationsNonTreeBboxes;
   private Path pathComplexRelationsNonTreeBboxes;
   private Path pathSimpleRelationsEmpty;
   private Path pathComplexRelationsEmpty;
   private Path pathSimpleRelationsSorted;
   private Path pathComplexRelationsSorted;
   private Path pathSimpleRelationsSortedBboxes;
   private Path pathComplexRelationsSortedBboxes;
   private Path pathTreeGeometry;
   private Path pathSimpleRelationsSortedGeometry;
   private Path pathComplexRelationsSortedGeometry;
   private boolean keepSplittedNodes = false;
   private boolean keepSplittedWays = false;
   private boolean keepSplittedRelations = false;
   private boolean keepWaysByNodes = false;
   private boolean keepRelations = false;
   private boolean keepRelationBatches = false;
   private boolean keepNonTreeRelations = false;
   private boolean keepUnsortedRelations = false;
   private TimeTable t = new TimeTable();

   public ExtractionFilesBuilder(
      Path pathInput,
      FileFormat inputFormat,
      Path pathOutput,
      FileFormat outputFormat,
      ExtractionFileNames fileNames,
      int maxNodes,
      boolean includeMetadata,
      int maxMembersSimple,
      int maxMembersComplex,
      boolean computeBbox
   ) {
      this.pathInput = pathInput;
      this.inputFormat = inputFormat;
      this.pathOutput = pathOutput;
      this.outputFormat = outputFormat;
      this.fileNames = fileNames;
      this.maxNodes = maxNodes;
      this.includeMetadata = includeMetadata;
      this.maxMembersSimple = maxMembersSimple;
      this.maxMembersComplex = maxMembersComplex;
      this.computeBbox = computeBbox;
   }

   public void execute() throws IOException, OsmInputException {
      System.out.println("Output directory: " + this.pathOutput);
      Files.createDirectories(this.pathOutput);
      if (!Files.isDirectory(this.pathOutput)) {
         System.out.println("Unable to create output directory");
         System.exit(1);
      }

      if (this.pathOutput.toFile().listFiles().length != 0) {
         System.out.println("Output directory is not empty");
         System.exit(1);
      }

      String extension = OsmIoUtils.extension(this.outputFormat);
      this.pathNodes = this.pathOutput.resolve(this.fileNames.getSplitNodes());
      this.pathWays = this.pathOutput.resolve(this.fileNames.getSplitWays());
      this.pathRelations = this.pathOutput.resolve(this.fileNames.getSplitRelations());
      this.pathTree = this.pathOutput.resolve(this.fileNames.getTree());
      this.pathWaysByNodes = this.pathOutput.resolve(this.fileNames.getWaysByNodes());
      this.pathSimpleRelations = this.pathOutput.resolve("relations.simple" + extension);
      this.pathComplexRelations = this.pathOutput.resolve("relations.complex" + extension);
      this.pathSimpleRelationsDir = this.pathOutput.resolve("relations.simple");
      this.pathComplexRelationsDir = this.pathOutput.resolve("relations.complex");
      this.pathSimpleRelationsNonTree = this.pathOutput.resolve("relations.simple.nontree" + extension);
      this.pathComplexRelationsNonTree = this.pathOutput.resolve("relations.complex.nontree" + extension);
      this.pathSimpleRelationsNonTreeBboxes = this.pathOutput.resolve("relations.simple.nontree.bboxlist");
      this.pathComplexRelationsNonTreeBboxes = this.pathOutput.resolve("relations.complex.nontree.bboxlist");
      this.pathSimpleRelationsEmpty = this.pathOutput.resolve(this.fileNames.getSimpleRelationsEmpty());
      this.pathComplexRelationsEmpty = this.pathOutput.resolve(this.fileNames.getComplexRelationsEmpty());
      this.pathSimpleRelationsSorted = this.pathOutput.resolve(this.fileNames.getSimpleRelations());
      this.pathComplexRelationsSorted = this.pathOutput.resolve(this.fileNames.getComplexRelations());
      this.pathSimpleRelationsSortedBboxes = this.pathOutput.resolve(this.fileNames.getSimpleRelationsBboxes());
      this.pathComplexRelationsSortedBboxes = this.pathOutput.resolve(this.fileNames.getComplexRelationsBboxes());
      this.pathTreeGeometry = this.pathOutput.resolve("tree.wkt");
      this.pathSimpleRelationsSortedGeometry = this.pathOutput.resolve("simple.wkt");
      this.pathComplexRelationsSortedGeometry = this.pathOutput.resolve("complex.wkt");
      OsmFileInput fileInput = new OsmFileInput(this.pathInput, this.inputFormat);
      OsmFileInput fileInputNodes = new OsmFileInput(this.pathNodes, this.outputFormat);
      OsmFileInput fileInputWays = new OsmFileInput(this.pathWays, this.outputFormat);
      OsmFileInput fileInputRelations = new OsmFileInput(this.pathRelations, this.outputFormat);
      TreeFileNames treeNames = this.fileNames.getTreeNames();
      String fileNamesFinalNodes = treeNames.getNodes();
      String fileNamesFinalWays = treeNames.getWays();
      String fileNamesFinalRelationsSimple = treeNames.getSimpleRelations();
      String fileNamesFinalRelationsComplex = treeNames.getComplexRelations();
      String fileNamesInitialNodes = "initial-nodes" + extension;
      String fileNamesInitialWays = "dist-ways" + extension;
      String fileNamesMissingWayNodeIds = "dist-ways-missing.ids";
      String fileNamesMissingNodes = "missing-nodes" + extension;
      String fileNamesDistributedWays = "ways-unsorted" + extension;
      String fileNamesDistributedNodes = "nodes-unsorted" + extension;
      String fileNamesRelationsComplexUnsorted = "relations-complex-unsorted" + extension;
      BatchFileNames relationNames = this.fileNames.getRelationNames();
      String fileNamesRelations = relationNames.getRelations();
      OsmOutputConfig outputConfigSplit = new OsmOutputConfig(this.outputFormat, this.includeMetadata);
      OsmOutputConfig outputConfigTree = new OsmOutputConfig(this.outputFormat, this.includeMetadata);
      OsmOutputConfig outputConfigWays = new OsmOutputConfig(this.outputFormat, this.includeMetadata);
      OsmOutputConfig outputConfigRelations = new OsmOutputConfig(this.outputFormat, this.includeMetadata);
      outputConfigTree.getTboConfig().setLimitNodes(new ElementCountLimit(1024));
      outputConfigTree.getTboConfig().setLimitWays(new WayNodeLimit(2048));
      outputConfigTree.getTboConfig().setLimitRelations(new RelationMemberLimit(2048));
      outputConfigRelations.getTboConfig().setLimitRelations(new RelationMemberLimit(1024));
      OsmOutputConfig outputConfigTreeFinal = new OsmOutputConfig(this.outputFormat, this.includeMetadata);
      BBox bbox = null;
      OsmIteratorInput inputBounds = fileInput.createIterator(false, false);
      if (!inputBounds.getIterator().hasBounds() && !this.computeBbox) {
         System.out.println("Input does not provide bounds and no flag has been set to compute the bounding box");
         System.exit(1);
      }

      if (inputBounds.getIterator().hasBounds()) {
         OsmBounds bounds = inputBounds.getIterator().getBounds();
         bbox = new BBox(bounds.getLeft(), bounds.getBottom(), bounds.getRight(), bounds.getTop());
         System.out.println("bounds from file: " + BBoxString.create(bbox));
      }

      inputBounds.close();
      this.t.start("total");
      this.t.start("split");
      OsmIteratorInput input = fileInput.createIterator(true, this.includeMetadata);
      ThreadedEntitySplitter splitter = new ThreadedEntitySplitter(
         input.getIterator(), this.pathNodes, this.pathWays, this.pathRelations, outputConfigSplit, 10000, 200
      );
      splitter.execute();
      input.close();
      this.t.stop("split");
      this.printInfo();
      this.t.start("compute bbox");
      if (this.computeBbox) {
         bbox = OsmUtils.computeBBox(fileInputNodes);
         System.out.println("computed bounds: " + BBoxString.create(bbox));
      }

      this.t.stop("compute bbox");
      this.t.start("build nodetree");
      DataTree tree = DataTreeUtil.initNewTree(this.pathTree, bbox);
      DataTreeFiles treeFiles = new DataTreeFiles(this.pathTree, fileNamesInitialNodes);
      DataTreeOutputFactory dataTreeOutputFactory = new ClosingDataTreeOutputFactory(treeFiles, outputConfigTree);
      NodeTreeLeafCounterFactory counterFactory = new ThreadedNodeTreeLeafCounterFactory();
      NodeTreeDistributorFactory distributorFactory = new ThreadedNodeTreeDistributorFactory();
      NodeTreeCreatorMaxNodes creator = new NodeTreeCreatorMaxNodes(
         tree,
         fileInputNodes,
         dataTreeOutputFactory,
         this.maxNodes,
         20,
         8,
         this.pathTree,
         fileNamesInitialNodes,
         outputConfigTree,
         counterFactory,
         distributorFactory
      );
      creator.buildTree();
      this.t.stop("build nodetree");
      this.printInfo();
      this.t.start("sort ways by first node id");
      OsmIteratorInput inputWays = fileInputWays.createIterator(true, this.includeMetadata);
      WaysSorterByFirstNodeId waysSorter = new ThreadedWaysSorterByFirstNodeId(inputWays.getIterator(), this.pathWaysByNodes, outputConfigWays);
      waysSorter.execute();
      inputWays.close();
      this.t.stop("sort ways by first node id");
      this.t.start("map ways to tree");
      OsmIteratorInput inputNodes = fileInputNodes.createIterator(true, this.includeMetadata);
      WaysToTreeMapper waysMapper = new ThreadedWaysToTreeMapper(
         inputNodes.getIterator(), this.pathTree, this.pathWaysByNodes, this.outputFormat, fileNamesInitialWays, outputConfigTree
      );
      waysMapper.execute();
      inputNodes.close();
      if (!this.keepWaysByNodes) {
         FileUtils.deleteDirectory(this.pathWaysByNodes.toFile());
      }

      this.t.stop("map ways to tree");
      this.printInfo();
      this.t.start("find missing way nodes");
      MissingWayNodesFinder wayNodesFinder = new ThreadedMissingWayNodesFinder(
         this.pathTree,
         this.pathTree,
         this.pathTree,
         fileNamesInitialNodes,
         fileNamesInitialWays,
         fileNamesMissingWayNodeIds,
         this.outputFormat,
         this.outputFormat
      );
      wayNodesFinder.execute();
      this.t.stop("find missing way nodes");
      this.printInfo();
      this.t.start("extract missing way nodes");
      inputNodes = fileInputNodes.createIterator(true, this.includeMetadata);
      boolean threaded = true;
      MissingWayNodesExtractor wayNodesExtractor = new MissingWayNodesExtractor(
         inputNodes.getIterator(), this.pathTree, fileNamesMissingWayNodeIds, this.pathTree, fileNamesMissingNodes, outputConfigTree, threaded
      );
      wayNodesExtractor.execute();
      inputNodes.close();

      for (Path path : BatchFilesUtil.getPaths(this.pathTree, fileNamesMissingWayNodeIds)) {
         Files.delete(path);
      }

      this.t.stop("extract missing way nodes");
      this.printInfo();
      this.t.start("distribute ways");
      WaysDistributor waysDistributor = new ThreadedWaysDistributor(
         this.pathTree,
         fileNamesInitialNodes,
         fileNamesMissingNodes,
         fileNamesInitialWays,
         fileNamesDistributedWays,
         fileNamesDistributedNodes,
         this.outputFormat,
         this.outputFormat,
         outputConfigTree
      );
      waysDistributor.execute();
      this.t.stop("distribute ways");
      this.printInfo();
      this.t.start("merge tree node files");
      List<String> fileNamesSortedNodes = new ArrayList<>();
      List<String> fileNamesUnsortedNodes = new ArrayList<>();
      fileNamesSortedNodes.add(fileNamesInitialNodes);
      fileNamesSortedNodes.add(fileNamesMissingNodes);
      fileNamesUnsortedNodes.add(fileNamesDistributedNodes);
      TreeFilesMerger nodesMerger = new ThreadedTreeFilesMerger(
         this.pathTree, fileNamesSortedNodes, fileNamesUnsortedNodes, fileNamesFinalNodes, this.outputFormat, outputConfigTreeFinal, true
      );
      nodesMerger.execute();
      this.t.stop("merge tree node files");
      this.printInfo();
      this.t.start("merge tree way files");
      List<String> fileNamesSortedWays = new ArrayList<>();
      List<String> fileNamesUnsortedWays = new ArrayList<>();
      fileNamesUnsortedWays.add(fileNamesInitialWays);
      fileNamesUnsortedWays.add(fileNamesDistributedWays);
      TreeFilesMerger waysMerger = new ThreadedTreeFilesMerger(
         this.pathTree, fileNamesSortedWays, fileNamesUnsortedWays, fileNamesFinalWays, this.outputFormat, outputConfigTreeFinal, true
      );
      waysMerger.execute();
      this.t.stop("merge tree way files");
      this.printInfo();
      this.t.start("separate simple/complex relations");
      RelationsSeparator separator = new RelationsSeparator(fileInputRelations, this.pathSimpleRelations, this.pathComplexRelations, outputConfigRelations);
      separator.execute();
      this.t.stop("separate simple/complex relations");
      this.printInfo();
      this.t.start("split relations, collect members");
      OsmFileInput inputSimpleRelations = new OsmFileInput(this.pathSimpleRelations, this.outputFormat);
      OsmFileInput inputComplexRelations = new OsmFileInput(this.pathComplexRelations, this.outputFormat);
      RelationsSplitterAndMemberCollector relationSplitter = new RelationsSplitterAndMemberCollector(
         inputSimpleRelations,
         inputComplexRelations,
         this.pathSimpleRelationsDir,
         this.pathComplexRelationsDir,
         fileNamesRelations,
         fileInputWays,
         fileInputNodes,
         outputConfigRelations
      );
      relationSplitter.execute();
      if (!this.keepRelations) {
         Files.delete(this.pathSimpleRelations);
         Files.delete(this.pathComplexRelations);
      }

      this.t.stop("split relations, collect members");
      this.printInfo();
      this.t.start("distribute relations");
      String fileNamesNodes = "nodes" + extension;
      String fileNamesWays = "ways" + extension;
      SimpleRelationsDistributor simpleRelationsDistributor = new SimpleRelationsDistributor(
         this.pathTree,
         this.pathSimpleRelationsDir,
         this.pathSimpleRelationsEmpty,
         this.pathSimpleRelationsNonTree,
         this.pathSimpleRelationsNonTreeBboxes,
         fileNamesRelations,
         fileNamesWays,
         fileNamesNodes,
         fileNamesFinalRelationsSimple,
         this.outputFormat,
         outputConfigTree
      );
      simpleRelationsDistributor.execute();
      ComplexRelationsDistributor complexRelationsDistributor = new ComplexRelationsDistributor(
         this.pathTree,
         this.pathComplexRelationsDir,
         this.pathComplexRelationsEmpty,
         this.pathComplexRelationsNonTree,
         this.pathComplexRelationsNonTreeBboxes,
         fileNamesRelations,
         fileNamesWays,
         fileNamesNodes,
         fileNamesRelationsComplexUnsorted,
         this.outputFormat,
         outputConfigTree
      );
      complexRelationsDistributor.execute();
      this.t.stop("distribute relations");
      this.printInfo();
      this.t.start("sort complex tree relations");
      TreeFileSorter sorter = new TreeFileSorter(
         this.pathTree, fileNamesRelationsComplexUnsorted, fileNamesFinalRelationsComplex, this.outputFormat, outputConfigRelations, this.keepUnsortedRelations
      );
      sorter.execute();
      this.t.stop("sort complex tree relations");
      this.t.start("sort non-tree relations");
      NonTreeRelationsSplitter nonTreeSplitter = new NonTreeRelationsSplitter(
         this.pathSimpleRelationsNonTree,
         this.pathComplexRelationsNonTree,
         this.pathSimpleRelationsNonTreeBboxes,
         this.pathComplexRelationsNonTreeBboxes,
         this.pathSimpleRelationsDir,
         this.pathComplexRelationsDir,
         this.pathSimpleRelationsSorted,
         this.pathComplexRelationsSorted,
         this.outputFormat,
         outputConfigRelations,
         this.pathSimpleRelationsSortedBboxes,
         this.pathComplexRelationsSortedBboxes,
         this.maxMembersSimple,
         this.maxMembersComplex,
         this.keepUnsortedRelations
      );
      nonTreeSplitter.execute();
      if (!this.keepRelationBatches) {
         FileUtils.deleteDirectory(this.pathSimpleRelationsDir.toFile());
         FileUtils.deleteDirectory(this.pathComplexRelationsDir.toFile());
      }

      this.t.stop("sort non-tree relations");
      this.t.start("clean up");
      if (!this.keepNonTreeRelations) {
         Files.delete(this.pathSimpleRelationsNonTree);
         Files.delete(this.pathComplexRelationsNonTree);
         Files.delete(this.pathSimpleRelationsNonTreeBboxes);
         Files.delete(this.pathComplexRelationsNonTreeBboxes);
      }

      if (!this.keepSplittedNodes) {
         Files.delete(this.pathNodes);
      }

      if (!this.keepSplittedWays) {
         Files.delete(this.pathWays);
      }

      if (!this.keepSplittedRelations) {
         Files.delete(this.pathRelations);
      }

      this.t.stop("clean up");
      this.t.start("create geometries");
      DataTreeBoxGeometryCreator dataTreeBoxGeometryCreator = new DataTreeBoxGeometryCreator(this.pathTree.toFile(), this.pathTreeGeometry.toFile());
      dataTreeBoxGeometryCreator.execute();
      IdBboxListGeometryCreator idBboxListGeometryCreatorSimple = new IdBboxListGeometryCreator(
         this.pathSimpleRelationsSortedBboxes.toFile(), this.pathSimpleRelationsSortedGeometry.toFile()
      );
      idBboxListGeometryCreatorSimple.execute();
      IdBboxListGeometryCreator idBboxListGeometryCreatorComplex = new IdBboxListGeometryCreator(
         this.pathComplexRelationsSortedBboxes.toFile(), this.pathComplexRelationsSortedGeometry.toFile()
      );
      idBboxListGeometryCreatorComplex.execute();
      this.t.stop("create geometries");
      this.t.stop("total");
      this.printInfo();
   }

   public void printInfo() {
      String[] keys = new String[]{
         "total",
         "split",
         "compute bbox",
         "build nodetree",
         "sort ways by first node id",
         "map ways to tree",
         "find missing way nodes",
         "extract missing way nodes",
         "distribute ways",
         "merge tree node files",
         "merge tree way files",
         "separate simple/complex relations",
         "split relations, collect members",
         "distribute relations",
         "sort complex tree relations",
         "sort non-tree relations",
         "clean up",
         "create geometries"
      };

      for (String key : keys) {
         System.out.println(String.format("%s: %s", key, this.t.htime(key)));
      }
   }

   public boolean isKeepSplittedNodes() {
      return this.keepSplittedNodes;
   }

   public void setKeepSplittedNodes(boolean keepSplittedNodes) {
      this.keepSplittedNodes = keepSplittedNodes;
   }

   public boolean isKeepSplittedWays() {
      return this.keepSplittedWays;
   }

   public void setKeepSplittedWays(boolean keepSplittedWays) {
      this.keepSplittedWays = keepSplittedWays;
   }

   public boolean isKeepSplittedRelations() {
      return this.keepSplittedRelations;
   }

   public void setKeepSplittedRelations(boolean keepSplittedRelations) {
      this.keepSplittedRelations = keepSplittedRelations;
   }

   public boolean isKeepWaysByNodes() {
      return this.keepWaysByNodes;
   }

   public void setKeepWaysByNodes(boolean keepWaysByNodes) {
      this.keepWaysByNodes = keepWaysByNodes;
   }

   public boolean isKeepRelations() {
      return this.keepRelations;
   }

   public void setKeepRelations(boolean keepRelations) {
      this.keepRelations = keepRelations;
   }

   public boolean isKeepRelationBatches() {
      return this.keepRelationBatches;
   }

   public void setKeepRelationBatches(boolean keepRelationBatches) {
      this.keepRelationBatches = keepRelationBatches;
   }

   public boolean isKeepNonTreeRelations() {
      return this.keepNonTreeRelations;
   }

   public void setKeepNonTreeRelations(boolean keepNonTreeRelations) {
      this.keepNonTreeRelations = keepNonTreeRelations;
   }

   public boolean isKeepUnsortedRelations() {
      return this.keepUnsortedRelations;
   }

   public void setKeepUnsortedRelations(boolean keepUnsortedRelations) {
      this.keepUnsortedRelations = keepUnsortedRelations;
   }
}
