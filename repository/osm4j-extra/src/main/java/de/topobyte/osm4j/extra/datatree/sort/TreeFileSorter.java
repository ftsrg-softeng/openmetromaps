package de.topobyte.osm4j.extra.datatree.sort;

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
import de.topobyte.osm4j.utils.sort.MemorySort;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class TreeFileSorter {
   private Path pathTree;
   private String fileNamesUnsorted;
   private String fileNamesSorted;
   private FileFormat inputFormat;
   private OsmOutputConfig outputConfig;
   private boolean keepUnsorted;
   private DataTree tree;
   private List<Node> leafs;

   public TreeFileSorter(
      Path pathTree, String fileNamesUnsorted, String fileNamesSorted, FileFormat inputFormat, OsmOutputConfig outputConfig, boolean keepUnsorted
   ) {
      this.pathTree = pathTree;
      this.fileNamesSorted = fileNamesSorted;
      this.fileNamesUnsorted = fileNamesUnsorted;
      this.inputFormat = inputFormat;
      this.outputConfig = outputConfig;
      this.keepUnsorted = keepUnsorted;
   }

   public void execute() throws IOException {
      this.prepare();
      this.sort();
   }

   protected void prepare() throws IOException {
      this.tree = DataTreeOpener.open(this.pathTree.toFile());
      this.leafs = this.tree.getLeafs();
   }

   protected void sort() throws IOException {
      DataTreeFiles filesUnsorted = new DataTreeFiles(this.pathTree, this.fileNamesUnsorted);
      DataTreeFiles filesSorted = new DataTreeFiles(this.pathTree, this.fileNamesSorted);

      for (Node leaf : this.leafs) {
         Path unsorted = filesUnsorted.getPath(leaf);
         Path sorted = filesSorted.getPath(leaf);
         InputStream input = StreamUtil.bufferedInputStream(unsorted);
         OutputStream output = StreamUtil.bufferedOutputStream(sorted);
         OsmIterator osmInput = OsmIoUtils.setupOsmIterator(input, this.inputFormat, this.outputConfig.isWriteMetadata());
         OsmOutputStream osmOutput = OsmIoUtils.setupOsmOutput(output, this.outputConfig);
         MemorySort sort = new MemorySort(osmOutput, osmInput);
         sort.setIgnoreDuplicates(true);
         sort.run();
         output.close();
         input.close();
         if (!this.keepUnsorted) {
            Files.delete(unsorted);
         }
      }
   }
}
