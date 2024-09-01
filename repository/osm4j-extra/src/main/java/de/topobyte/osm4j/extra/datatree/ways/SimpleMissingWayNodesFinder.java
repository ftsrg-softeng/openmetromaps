package de.topobyte.osm4j.extra.datatree.ways;

import de.topobyte.osm4j.extra.datatree.Node;
import de.topobyte.osm4j.utils.FileFormat;
import java.io.IOException;
import java.nio.file.Path;

public class SimpleMissingWayNodesFinder extends AbstractMissingWayNodesFinder {
   public SimpleMissingWayNodesFinder(
      Path pathNodeTree,
      Path pathWayTree,
      Path pathOutputTree,
      String fileNamesNodes,
      String fileNamesWays,
      String fileNamesOutput,
      FileFormat inputFormatNodes,
      FileFormat inputFormatWays
   ) {
      super(pathNodeTree, pathWayTree, pathOutputTree, fileNamesNodes, fileNamesWays, fileNamesOutput, inputFormatNodes, inputFormatWays);
   }

   @Override
   public void execute() throws IOException {
      this.prepare();
      this.processLeafs();
   }

   public void processLeafs() throws IOException {
      int i = 0;

      for (Node leaf : this.leafs) {
         System.out.println(String.format("Processing leaf %d/%d", ++i, this.leafs.size()));
         MissingWayNodesFinderTask task = this.creatTask(leaf);
         task.execute();
         this.stats(task);
      }
   }
}
