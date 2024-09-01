package de.topobyte.osm4j.utils.split;

import de.topobyte.osm4j.core.access.OsmIterator;
import de.topobyte.osm4j.utils.OsmOutputConfig;
import de.topobyte.osm4j.utils.buffer.OsmBuffer;
import de.topobyte.osm4j.utils.buffer.ParallelExecutor;
import de.topobyte.osm4j.utils.buffer.RunnableBufferBridge;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class ThreadedEntitySplitter extends AbstractEntitySplitter {
   private int bufferSize;
   private int maxNumberOfBuffers;

   public ThreadedEntitySplitter(
      OsmIterator iterator, Path pathNodes, Path pathWays, Path pathRelations, OsmOutputConfig outputConfig, int bufferSize, int maxNumberOfBuffers
   ) {
      super(iterator, pathNodes, pathWays, pathRelations, outputConfig);
      this.bufferSize = bufferSize;
      this.maxNumberOfBuffers = maxNumberOfBuffers;
   }

   public void execute() throws IOException {
      this.init();
      this.passBounds();
      this.run();
      this.finish();
   }

   private void run() throws IOException {
      OsmBuffer buffer = new OsmBuffer(this.bufferSize, this.maxNumberOfBuffers);
      RunnableBufferBridge bridge = new RunnableBufferBridge(this.iterator, buffer);
      RunnableEntitySplitter splitter = new RunnableEntitySplitter(buffer, this.oosNodes, this.oosWays, this.oosRelations);
      List<Runnable> tasks = new ArrayList<>();
      tasks.add(bridge);
      tasks.add(splitter);
      ParallelExecutor executor = new ParallelExecutor(tasks);
      executor.execute();
   }
}
