package de.topobyte.osm4j.extra.datatree.merge;

import de.topobyte.osm4j.extra.datatree.Node;
import de.topobyte.osm4j.utils.FileFormat;
import de.topobyte.osm4j.utils.OsmOutputConfig;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public class SimpleTreeFilesMerger extends AbstractTreeFilesMerger {
   public SimpleTreeFilesMerger(
      Path pathTree,
      List<String> fileNamesSorted,
      List<String> fileNamesUnsorted,
      String fileNamesOutput,
      FileFormat inputFormat,
      OsmOutputConfig outputConfig,
      boolean deleteInput
   ) {
      super(pathTree, fileNamesSorted, fileNamesUnsorted, fileNamesOutput, inputFormat, outputConfig, deleteInput);
   }

   @Override
   public void execute() throws IOException {
      this.prepare();
      this.run();
   }

   public void run() throws IOException {
      int i = 0;

      for (Node leaf : this.leafs) {
         System.out.println(String.format("Processing leaf %d/%d", ++i, this.leafs.size()));
         this.mergeFiles(leaf);
         this.stats(i);
      }
   }
}
