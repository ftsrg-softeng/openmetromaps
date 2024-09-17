package de.topobyte.osm4j.extra.datatree.ways;

import com.slimjars.dist.gnu.trove.map.TLongObjectMap;
import com.slimjars.dist.gnu.trove.map.hash.TLongObjectHashMap;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.LinearRing;
import com.vividsolutions.jts.geom.Polygon;
import de.topobyte.jts.utils.GeometryGroup;
import de.topobyte.largescalefileio.ClosingFileOutputStreamFactory;
import de.topobyte.largescalefileio.SimpleClosingFileOutputStreamFactory;
import de.topobyte.melon.io.StreamUtil;
import de.topobyte.osm4j.core.access.OsmOutputStream;
import de.topobyte.osm4j.core.access.OsmOutputStreamStreamOutput;
import de.topobyte.osm4j.core.access.OsmStreamOutput;
import de.topobyte.osm4j.core.dataset.InMemoryListDataSet;
import de.topobyte.osm4j.core.dataset.ListDataSetLoader;
import de.topobyte.osm4j.core.model.iface.OsmNode;
import de.topobyte.osm4j.core.model.iface.OsmWay;
import de.topobyte.osm4j.core.model.util.OsmModelUtil;
import de.topobyte.osm4j.core.resolve.EntityNotFoundException;
import de.topobyte.osm4j.core.resolve.OsmEntityProvider;
import de.topobyte.osm4j.extra.QueryUtil;
import de.topobyte.osm4j.extra.datatree.DataTree;
import de.topobyte.osm4j.extra.datatree.DataTreeFiles;
import de.topobyte.osm4j.extra.datatree.DataTreeOpener;
import de.topobyte.osm4j.extra.datatree.Node;
import de.topobyte.osm4j.geometry.WayBuilder;
import de.topobyte.osm4j.geometry.WayBuilderResult;
import de.topobyte.osm4j.utils.FileFormat;
import de.topobyte.osm4j.utils.OsmIoUtils;
import de.topobyte.osm4j.utils.OsmOutputConfig;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Path;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

public abstract class AbstractWaysDistributor implements WaysDistributor {
   private Path pathTree;
   private String fileNamesNodes1;
   private String fileNamesNodes2;
   private String fileNamesWays;
   private String fileNamesOutputWays;
   private String fileNamesOutputNodes;
   private FileFormat inputFormatNodes;
   private FileFormat inputFormatWays;
   private OsmOutputConfig outputConfig;
   private DataTree tree;
   private List<Node> leafs;
   protected Map<Node, OsmStreamOutput> outputsWays = new HashMap<>();
   protected Map<Node, OsmStreamOutput> outputsNodes = new HashMap<>();
   private long counter = 0L;
   private long noneFound = 0L;
   private long unableToBuild = 0L;
   private long start = System.currentTimeMillis();
   private NumberFormat format = NumberFormat.getNumberInstance(Locale.US);
   private ClosingFileOutputStreamFactory factory = new SimpleClosingFileOutputStreamFactory();
   protected boolean stopped = false;
   private GeometryFactory f = new GeometryFactory();
   private WayBuilder wb = new WayBuilder(this.f);

   public AbstractWaysDistributor(
      Path pathTree,
      String fileNamesNodes1,
      String fileNamesNodes2,
      String fileNamesWays,
      String fileNamesOutputWays,
      String fileNamesOutputNodes,
      FileFormat inputFormatNodes,
      FileFormat inputFormatWays,
      OsmOutputConfig outputConfig
   ) {
      this.pathTree = pathTree;
      this.fileNamesNodes1 = fileNamesNodes1;
      this.fileNamesNodes2 = fileNamesNodes2;
      this.fileNamesWays = fileNamesWays;
      this.fileNamesOutputWays = fileNamesOutputWays;
      this.fileNamesOutputNodes = fileNamesOutputNodes;
      this.inputFormatNodes = inputFormatNodes;
      this.inputFormatWays = inputFormatWays;
      this.outputConfig = outputConfig;
   }

   @Override
   public void execute() throws IOException {
      this.prepare();
      this.distribute();
      this.finish();
   }

   protected void prepare() throws IOException {
      this.tree = DataTreeOpener.open(this.pathTree.toFile());
      this.leafs = this.tree.getLeafs();
      DataTreeFiles filesWays = new DataTreeFiles(this.pathTree, this.fileNamesOutputWays);
      DataTreeFiles filesNodes = new DataTreeFiles(this.pathTree, this.fileNamesOutputNodes);

      for (Node leaf : this.leafs) {
         OsmStreamOutput outputWays = this.createOutput(filesWays.getFile(leaf));
         this.outputsWays.put(leaf, outputWays);
         OsmStreamOutput outputNodes = this.createOutput(filesNodes.getFile(leaf));
         this.outputsNodes.put(leaf, outputNodes);
      }
   }

   private OsmStreamOutput createOutput(File file) throws IOException {
      OutputStream output = this.factory.create(file);
      OutputStream var4 = new BufferedOutputStream(output);
      OsmOutputStream osmOutput = OsmIoUtils.setupOsmOutput(var4, this.outputConfig, true);
      return new OsmOutputStreamStreamOutput(var4, osmOutput);
   }

   protected void finish() throws IOException {
      for (OsmStreamOutput output : this.outputsWays.values()) {
         output.getOsmOutput().complete();
         output.close();
      }

      for (OsmStreamOutput output : this.outputsNodes.values()) {
         output.getOsmOutput().complete();
         output.close();
      }
   }

   protected abstract void leafData(LeafData var1) throws IOException;

   protected abstract void write(Node var1, OsmWay var2, TLongObjectMap<OsmNode> var3) throws IOException;

   protected void distribute() throws IOException {
      DataTreeFiles filesNodes1 = new DataTreeFiles(this.pathTree, this.fileNamesNodes1);
      DataTreeFiles filesNodes2 = new DataTreeFiles(this.pathTree, this.fileNamesNodes2);
      DataTreeFiles filesWays = new DataTreeFiles(this.pathTree, this.fileNamesWays);
      int i = 0;
      Iterator<Node> iterator = this.leafs.iterator();

      while (!this.stopped && iterator.hasNext()) {
         Node leaf = iterator.next();
         System.out.println(String.format("Processing leaf %d/%d", ++i, this.leafs.size()));
         File fileNodes1 = filesNodes1.getFile(leaf);
         File fileNodes2 = filesNodes2.getFile(leaf);
         File fileWays = filesWays.getFile(leaf);
         InputStream inputNodes1 = StreamUtil.bufferedInputStream(fileNodes1);
         InputStream inputNodes2 = StreamUtil.bufferedInputStream(fileNodes2);
         InputStream inputWays = StreamUtil.bufferedInputStream(fileWays);
         long nodesSize1 = fileNodes1.length();
         System.out.println(String.format("Loading nodes file of size: %.3fMB", (double)nodesSize1 / 1024.0 / 1024.0));
         InMemoryListDataSet dataNodes1 = ListDataSetLoader.read(
            OsmIoUtils.setupOsmIterator(inputNodes1, this.inputFormatNodes, this.outputConfig.isWriteMetadata()), true, true, true
         );
         long nodesSize2 = fileNodes2.length();
         System.out.println(String.format("Loading nodes file of size: %.3fMB", (double)nodesSize2 / 1024.0 / 1024.0));
         InMemoryListDataSet dataNodes2 = ListDataSetLoader.read(
            OsmIoUtils.setupOsmIterator(inputNodes2, this.inputFormatNodes, this.outputConfig.isWriteMetadata()), true, true, true
         );
         long waysSize = fileWays.length();
         System.out.println(String.format("Loading ways file of size: %.3fMB", (double)waysSize / 1024.0 / 1024.0));
         InMemoryListDataSet dataWays = ListDataSetLoader.read(
            OsmIoUtils.setupOsmIterator(inputWays, this.inputFormatWays, this.outputConfig.isWriteMetadata()), true, true, true
         );
         inputNodes1.close();
         inputNodes2.close();
         inputWays.close();
         System.out.println("Number of ways: " + dataWays.getWays().size());
         this.leafData(new LeafData(leaf, dataWays, dataNodes1, dataNodes2));
         this.stats(i);
      }
   }

   protected void build(Node leaf, OsmWay way, OsmEntityProvider entityProvider) throws IOException {
      TLongObjectMap<OsmNode> nodes = new TLongObjectHashMap();

      List<Node> leafs;
      try {
         if (way.getNumberOfNodes() == 1) {
            leafs = this.buildSingleNodeWay(way, nodes, entityProvider);
         } else if (way.getNumberOfNodes() >= 4 && OsmModelUtil.isClosed(way)) {
            leafs = this.buildClosedWay(way, nodes, entityProvider);
         } else {
            leafs = this.buildNonClosedWay(way, nodes, entityProvider);
         }
      } catch (EntityNotFoundException var8) {
         System.out.println("Entity not found while building way: " + way.getId());
         return;
      }

      for (Node ileaf : leafs) {
         if (ileaf != leaf) {
            this.write(ileaf, way, nodes);
         }
      }

      if (leafs.size() == 0) {
         System.out.println("No leaf found for way: " + way.getId());
      }

      this.counter++;
   }

   private List<Node> buildSingleNodeWay(OsmWay way, TLongObjectMap<OsmNode> nodes, OsmEntityProvider entityProvider) throws EntityNotFoundException {
      long nodeId = way.getNodeId(0);
      OsmNode node = entityProvider.getNode(nodeId);
      nodes.put(nodeId, node);
      return this.tree.query(node.getLongitude(), node.getLatitude());
   }

   private List<Node> buildNonClosedWay(OsmWay way, TLongObjectMap<OsmNode> nodes, OsmEntityProvider entityProvider) throws EntityNotFoundException {
      WayBuilderResult build = this.wb.build(way, entityProvider);
      GeometryGroup group = build.toGeometryGroup(this.f);
      QueryUtil.putNodes(way, nodes, entityProvider);
      return this.tree.query(group);
   }

   private List<Node> buildClosedWay(OsmWay way, TLongObjectMap<OsmNode> nodes, OsmEntityProvider entityProvider) throws EntityNotFoundException {
      WayBuilderResult build = this.wb.build(way, entityProvider);
      GeometryGroup group = build.toGeometryGroup(this.f);
      LinearRing ring = build.getLinearRing();
      Polygon polygon = this.f.createPolygon(ring);
      QueryUtil.putNodes(way, nodes, entityProvider);
      List<Node> leafs1 = new ArrayList<>(this.tree.query(group));
      List<Node> leafs2 = new ArrayList<>(this.tree.query(polygon));
      if (leafs1.size() == 1 && leafs2.size() == 1 && leafs1.get(0) == leafs2.get(0)) {
         return leafs1;
      } else {
         List<Node> merged = this.merge(leafs1, leafs2);
         if (merged.size() > leafs1.size()) {
            System.out.println(String.format("found way that contains leafs. outline: %d polygon: %d merged: %d", leafs1.size(), leafs2.size(), merged.size()));
         }

         return merged;
      }
   }

   private List<Node> merge(List<Node> a, List<Node> b) {
      List<Node> result = new ArrayList<>();
      Set<Node> set = new HashSet<>();

      for (Node node : a) {
         result.add(node);
         set.add(node);
      }

      for (Node node : b) {
         if (!set.contains(node)) {
            result.add(node);
         }
      }

      return result;
   }

   private void stats(int leafsDone) {
      System.out
         .println(
            String.format(
               "ways: %s, no leafs found: %s, unable to build: %s",
               this.format.format(this.counter),
               this.format.format(this.noneFound),
               this.format.format(this.unableToBuild)
            )
         );
      long now = System.currentTimeMillis();
      long past = now - this.start;
      long estimate = Math.round((double)past / (double)leafsDone * (double)this.leafs.size());
      System.out.println(String.format("Past: %.2f", (double)(past / 1000L) / 60.0));
      System.out.println(String.format("Estimate: %.2f", (double)(estimate / 1000L) / 60.0));
   }
}
