package de.topobyte.osm4j.extra.datatree.nodetree.count;

import com.slimjars.dist.gnu.trove.map.TLongLongMap;
import de.topobyte.osm4j.core.access.OsmIterator;
import de.topobyte.osm4j.extra.datatree.DataTree;
import de.topobyte.osm4j.extra.datatree.Node;
import de.topobyte.osm4j.utils.buffer.OsmBuffer;
import de.topobyte.osm4j.utils.buffer.ParallelExecutor;
import de.topobyte.osm4j.utils.buffer.RunnableBufferBridge;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ThreadedNodeTreeLeafCounter implements NodeTreeLeafCounter {
   private OsmIterator iterator;
   private IteratorNodeTreeLeafCounter counter;

   public ThreadedNodeTreeLeafCounter(DataTree tree, Node head, OsmIterator iterator) {
      this.iterator = iterator;
      this.counter = new IteratorNodeTreeLeafCounter(tree, head);
   }

   @Override
   public Node getHead() {
      return this.counter.getHead();
   }

   @Override
   public TLongLongMap getCounters() {
      return this.counter.getCounters();
   }

   @Override
   public void execute() throws IOException {
      this.count();
   }

   private void count() throws IOException {
      final OsmBuffer buffer = new OsmBuffer(10000, 20);
      RunnableBufferBridge bridge = new RunnableBufferBridge(this.iterator, buffer);
      Runnable runnableLeafCounter = new Runnable() {
         @Override
         public void run() {
            try {
               ThreadedNodeTreeLeafCounter.this.counter.execute(buffer);
            } catch (IOException var2) {
               throw new RuntimeException(var2);
            }
         }
      };
      List<Runnable> tasks = new ArrayList<>();
      tasks.add(bridge);
      tasks.add(runnableLeafCounter);
      ParallelExecutor executor = new ParallelExecutor(tasks);
      executor.execute();
   }
}
