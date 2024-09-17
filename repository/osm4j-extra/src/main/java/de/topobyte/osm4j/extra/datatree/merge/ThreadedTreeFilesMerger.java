package de.topobyte.osm4j.extra.datatree.merge;

import de.topobyte.osm4j.extra.datatree.Node;
import de.topobyte.osm4j.utils.FileFormat;
import de.topobyte.osm4j.utils.OsmOutputConfig;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.ThreadPoolExecutor.CallerRunsPolicy;

public class ThreadedTreeFilesMerger extends AbstractTreeFilesMerger {
   private int leafsDone = 0;

   public ThreadedTreeFilesMerger(
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
      BlockingQueue<Runnable> tasks = new ArrayBlockingQueue<>(10);
      ThreadPoolExecutor exec = new ThreadPoolExecutor(3, 3, 1L, TimeUnit.MINUTES, tasks, new CallerRunsPolicy());
      int i = 0;

      for (final Node leaf : this.leafs) {
         System.out.println(String.format("Processing leaf %d/%d", ++i, this.leafs.size()));
         Runnable runnable = new Runnable() {
            @Override
            public void run() {
               try {
                  ThreadedTreeFilesMerger.this.mergeFiles(leaf);
                  ThreadedTreeFilesMerger.this.syncStats();
               } catch (IOException var2) {
                  throw new RuntimeException(var2);
               }
            }
         };
         exec.execute(runnable);
      }

      exec.shutdown();

      while (!exec.isTerminated()) {
         try {
            exec.awaitTermination(1L, TimeUnit.MINUTES);
         } catch (InterruptedException var7) {
         }
      }
   }

   private synchronized void syncStats() {
      this.leafsDone++;
      this.stats(this.leafsDone);
   }
}
