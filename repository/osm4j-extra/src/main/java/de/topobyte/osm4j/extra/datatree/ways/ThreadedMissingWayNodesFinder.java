package de.topobyte.osm4j.extra.datatree.ways;

import de.topobyte.osm4j.extra.datatree.Node;
import de.topobyte.osm4j.utils.FileFormat;
import java.io.IOException;
import java.nio.file.Path;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.ThreadPoolExecutor.CallerRunsPolicy;

public class ThreadedMissingWayNodesFinder extends AbstractMissingWayNodesFinder {
   public ThreadedMissingWayNodesFinder(
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
      BlockingQueue<Runnable> tasks = new ArrayBlockingQueue<>(10);
      ThreadPoolExecutor exec = new ThreadPoolExecutor(3, 3, 1L, TimeUnit.MINUTES, tasks, new CallerRunsPolicy());
      int i = 0;

      for (Node leaf : this.leafs) {
         System.out.println(String.format("Processing leaf %d/%d", ++i, this.leafs.size()));
         final MissingWayNodesFinderTask task = this.creatTask(leaf);
         Runnable runnable = new Runnable() {
            @Override
            public void run() {
               try {
                  task.execute();
                  ThreadedMissingWayNodesFinder.this.syncStats(task);
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
         } catch (InterruptedException var8) {
         }
      }
   }

   private synchronized void syncStats(MissingWayNodesFinderTask t) {
      this.stats(t);
   }
}
