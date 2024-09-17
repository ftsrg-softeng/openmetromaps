package de.topobyte.osm4j.extra.relations;

import com.vividsolutions.jts.geom.Envelope;
import de.topobyte.largescalefileio.ClosingFileOutputStreamFactory;
import de.topobyte.largescalefileio.SimpleClosingFileOutputStreamFactory;
import de.topobyte.melon.io.StreamUtil;
import de.topobyte.osm4j.core.access.OsmIterator;
import de.topobyte.osm4j.core.access.OsmOutputStream;
import de.topobyte.osm4j.core.access.OsmOutputStreamStreamOutput;
import de.topobyte.osm4j.core.access.OsmStreamOutput;
import de.topobyte.osm4j.core.dataset.InMemoryMapDataSet;
import de.topobyte.osm4j.core.dataset.MapDataSetLoader;
import de.topobyte.osm4j.core.model.impl.Bounds;
import de.topobyte.osm4j.extra.datatree.DataTree;
import de.topobyte.osm4j.extra.datatree.DataTreeFiles;
import de.topobyte.osm4j.extra.datatree.DataTreeOpener;
import de.topobyte.osm4j.extra.datatree.Node;
import de.topobyte.osm4j.extra.idbboxlist.IdBboxListOutputStream;
import de.topobyte.osm4j.utils.FileFormat;
import de.topobyte.osm4j.utils.OsmIoUtils;
import de.topobyte.osm4j.utils.OsmOutputConfig;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class RelationsDistributorBase {
   protected Path pathTree;
   protected Path pathData;
   protected Path pathOutputEmpty;
   protected Path pathOutputNonTree;
   protected Path pathOutputBboxes;
   protected String fileNamesRelations;
   protected String fileNamesWays;
   protected String fileNamesNodes;
   protected String fileNamesTreeRelations;
   protected FileFormat inputFormat;
   protected OsmOutputConfig outputConfig;
   protected DataTree tree;
   protected List<Path> subdirs;
   protected DataTreeFiles treeFilesRelations;
   protected OsmStreamOutput outputEmpty;
   protected OsmStreamOutput outputNonTree;
   protected Map<Node, OsmStreamOutput> outputs = new HashMap<>();
   protected IdBboxListOutputStream outputBboxes;
   int nWrittenEmpty = 0;
   int nWrittenToTree = 0;
   int nRemaining = 0;

   public RelationsDistributorBase(
      Path pathTree,
      Path pathData,
      Path pathOutputEmpty,
      Path pathOutputNonTree,
      Path pathOutputBboxes,
      String fileNamesRelations,
      String fileNamesWays,
      String fileNamesNodes,
      String fileNamesTreeRelations,
      FileFormat inputFormat,
      OsmOutputConfig outputConfig
   ) {
      this.pathTree = pathTree;
      this.pathData = pathData;
      this.pathOutputEmpty = pathOutputEmpty;
      this.pathOutputNonTree = pathOutputNonTree;
      this.pathOutputBboxes = pathOutputBboxes;
      this.fileNamesRelations = fileNamesRelations;
      this.fileNamesWays = fileNamesWays;
      this.fileNamesNodes = fileNamesNodes;
      this.fileNamesTreeRelations = fileNamesTreeRelations;
      this.inputFormat = inputFormat;
      this.outputConfig = outputConfig;
   }

   protected void init() throws IOException {
      if (!Files.isDirectory(this.pathData)) {
         System.out.println("Input path is not a directory");
         System.exit(1);
      }

      this.tree = DataTreeOpener.open(this.pathTree.toFile());
      this.treeFilesRelations = new DataTreeFiles(this.pathTree, this.fileNamesTreeRelations);
      this.subdirs = new ArrayList<>();
      File[] subs = this.pathData.toFile().listFiles();

      for (File sub : subs) {
         if (sub.isDirectory()) {
            Path subPath = sub.toPath();
            Path relations = subPath.resolve(this.fileNamesRelations);
            Path ways = subPath.resolve(this.fileNamesWays);
            Path nodes = subPath.resolve(this.fileNamesNodes);
            if (Files.exists(relations) && Files.exists(ways) && Files.exists(nodes)) {
               this.subdirs.add(subPath);
            }
         }
      }

      Collections.sort(this.subdirs, new Comparator<Path>() {
         public int compare(Path o1, Path o2) {
            String name1 = o1.getFileName().toString();
            String name2 = o2.getFileName().toString();

            try {
               int n1 = Integer.parseInt(name1);
               int n2 = Integer.parseInt(name2);
               return Integer.compare(n1, n2);
            } catch (NumberFormatException var7) {
               return o1.compareTo(o2);
            }
         }
      });
      OutputStream outEmpty = StreamUtil.bufferedOutputStream(this.pathOutputEmpty);
      OsmOutputStream osmOutputEmpty = OsmIoUtils.setupOsmOutput(outEmpty, this.outputConfig);
      this.outputEmpty = new OsmOutputStreamStreamOutput(outEmpty, osmOutputEmpty);
      OutputStream outNonTree = StreamUtil.bufferedOutputStream(this.pathOutputNonTree);
      OsmOutputStream osmOutputNonTree = OsmIoUtils.setupOsmOutput(outNonTree, this.outputConfig);
      this.outputNonTree = new OsmOutputStreamStreamOutput(outNonTree, osmOutputNonTree);
      OutputStream outBboxes = StreamUtil.bufferedOutputStream(this.pathOutputBboxes);
      this.outputBboxes = new IdBboxListOutputStream(outBboxes);
      ClosingFileOutputStreamFactory factory = new SimpleClosingFileOutputStreamFactory();

      for (Node leaf : this.tree.getLeafs()) {
         File file = this.treeFilesRelations.getFile(leaf);
         OutputStream out = new BufferedOutputStream(factory.create(file));
         OsmOutputStream osmOutput = OsmIoUtils.setupOsmOutput(out, this.outputConfig, true);
         this.outputs.put(leaf, new OsmOutputStreamStreamOutput(out, osmOutput));
         Envelope box = leaf.getEnvelope();
         osmOutput.write(new Bounds(box.getMinX(), box.getMaxX(), box.getMaxY(), box.getMinY()));
      }
   }

   protected void run() throws IOException {
      int i = 0;

      for (Path path : this.subdirs) {
         System.out.println(String.format("Processing directory %d of %d", ++i, this.subdirs.size()));
         this.build(path);
         System.out.println(String.format("empty: %d, tree: %d, remaining: %d", this.nWrittenEmpty, this.nWrittenToTree, this.nRemaining));
      }
   }

   protected void finish() throws IOException {
      this.outputEmpty.getOsmOutput().complete();
      this.outputEmpty.close();
      this.outputNonTree.getOsmOutput().complete();
      this.outputNonTree.close();
      this.outputBboxes.close();

      for (OsmStreamOutput output : this.outputs.values()) {
         output.getOsmOutput().complete();
         output.close();
      }
   }

   protected abstract void build(Path var1) throws IOException;

   protected InMemoryMapDataSet read(Path path, boolean readMetadata, boolean keepTags) throws IOException {
      InputStream input = StreamUtil.bufferedInputStream(path.toFile());
      OsmIterator osmIterator = OsmIoUtils.setupOsmIterator(input, this.inputFormat, readMetadata);
      InMemoryMapDataSet data = MapDataSetLoader.read(osmIterator, keepTags, keepTags, keepTags);
      input.close();
      return data;
   }
}
