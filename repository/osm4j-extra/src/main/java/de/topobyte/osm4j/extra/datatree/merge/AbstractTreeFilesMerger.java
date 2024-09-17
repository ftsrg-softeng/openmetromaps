package de.topobyte.osm4j.extra.datatree.merge;

import de.topobyte.melon.io.StreamUtil;
import de.topobyte.osm4j.core.access.OsmIterator;
import de.topobyte.osm4j.core.access.OsmOutputStream;
import de.topobyte.osm4j.extra.datatree.DataTree;
import de.topobyte.osm4j.extra.datatree.DataTreeFiles;
import de.topobyte.osm4j.extra.datatree.DataTreeOpener;
import de.topobyte.osm4j.extra.datatree.Node;
import de.topobyte.osm4j.utils.FileFormat;
import de.topobyte.osm4j.utils.OsmIoUtils;
import de.topobyte.osm4j.utils.OsmOutputConfig;
import de.topobyte.osm4j.utils.merge.sorted.SortedMerge;
import de.topobyte.osm4j.utils.sort.MemorySortIterator;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractTreeFilesMerger implements TreeFilesMerger {
   private Path pathTree;
   private List<String> fileNamesSorted = new ArrayList<>();
   private List<String> fileNamesUnsorted = new ArrayList<>();
   private String fileNamesOutput;
   private FileFormat inputFormat;
   private OsmOutputConfig outputConfig;
   private boolean deleteInput;
   protected DataTree tree;
   protected List<Node> leafs;
   private long start = System.currentTimeMillis();

   public AbstractTreeFilesMerger(
      Path pathTree,
      List<String> fileNamesSorted,
      List<String> fileNamesUnsorted,
      String fileNamesOutput,
      FileFormat inputFormat,
      OsmOutputConfig outputConfig,
      boolean deleteInput
   ) {
      this.pathTree = pathTree;
      this.fileNamesSorted = fileNamesSorted;
      this.fileNamesUnsorted = fileNamesUnsorted;
      this.fileNamesOutput = fileNamesOutput;
      this.inputFormat = inputFormat;
      this.outputConfig = outputConfig;
      this.deleteInput = deleteInput;
   }

   protected void prepare() throws IOException {
      this.tree = DataTreeOpener.open(this.pathTree.toFile());
      this.leafs = this.tree.getLeafs();
   }

   protected void mergeFiles(Node leaf) throws IOException {
      DataTreeFiles filesOutputNodes = new DataTreeFiles(this.pathTree, this.fileNamesOutput);
      List<File> inputFiles = new ArrayList<>();
      List<InputStream> inputs = new ArrayList<>();
      List<OsmIterator> osmInputs = new ArrayList<>();

      for (String fileName : this.fileNamesSorted) {
         DataTreeFiles files = new DataTreeFiles(this.pathTree, fileName);
         File file = files.getFile(leaf);
         inputFiles.add(file);
         InputStream input = StreamUtil.bufferedInputStream(file);
         inputs.add(input);
         OsmIterator osmInput = OsmIoUtils.setupOsmIterator(input, this.inputFormat, this.outputConfig.isWriteMetadata());
         osmInputs.add(osmInput);
      }

      for (String fileName : this.fileNamesUnsorted) {
         DataTreeFiles files = new DataTreeFiles(this.pathTree, fileName);
         File file = files.getFile(leaf);
         inputFiles.add(file);
         InputStream input = StreamUtil.bufferedInputStream(file);
         inputs.add(input);
         OsmIterator osmInput = OsmIoUtils.setupOsmIterator(input, this.inputFormat, this.outputConfig.isWriteMetadata());
         OsmIterator sorted = new MemorySortIterator(osmInput);
         osmInputs.add(sorted);
      }

      File fileOutputNodes = filesOutputNodes.getFile(leaf);
      OutputStream output = StreamUtil.bufferedOutputStream(fileOutputNodes);
      OsmOutputStream osmOutput = OsmIoUtils.setupOsmOutput(output, this.outputConfig);
      SortedMerge merge = new SortedMerge(osmOutput, osmInputs);
      merge.run();

      for (InputStream input : inputs) {
         input.close();
      }

      output.close();
      if (this.deleteInput) {
         for (File file : inputFiles) {
            file.delete();
         }
      }
   }

   protected void stats(int leafsDone) {
      long now = System.currentTimeMillis();
      long past = now - this.start;
      long estimate = Math.round((double)past / (double)leafsDone * (double)this.leafs.size());
      System.out.println(String.format("Past: %.2f", (double)(past / 1000L) / 60.0));
      System.out.println(String.format("Estimate: %.2f", (double)(estimate / 1000L) / 60.0));
   }
}
