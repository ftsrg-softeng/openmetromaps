package de.topobyte.osm4j.extra.datatree.nodetree.distribute;

import de.topobyte.osm4j.core.access.OsmIterator;
import de.topobyte.osm4j.core.access.OsmStreamOutput;
import de.topobyte.osm4j.core.model.iface.OsmNode;
import de.topobyte.osm4j.extra.datatree.DataTree;
import de.topobyte.osm4j.extra.datatree.Node;
import de.topobyte.osm4j.extra.datatree.output.DataTreeOutputFactory;
import de.topobyte.osm4j.extra.threading.ObjectBuffer;
import de.topobyte.osm4j.extra.threading.write.NodeWriteRequest;
import de.topobyte.osm4j.extra.threading.write.WriteRequest;
import de.topobyte.osm4j.extra.threading.write.WriterRunner;
import de.topobyte.osm4j.utils.buffer.ParallelExecutor;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ThreadedNodeTreeDistributor extends AbstractNodeTreeDistributor {
   private DataTreeOutputFactory outputFactory;
   private ObjectBuffer<WriteRequest> buffer = new ObjectBuffer<>(10000, 100);

   public ThreadedNodeTreeDistributor(DataTree tree, Node head, OsmIterator iterator, DataTreeOutputFactory outputFactory) {
      super(tree, head, iterator);
      this.outputFactory = outputFactory;
   }

   @Override
   protected void initOutputs() throws IOException {
      for (Node leaf : this.tree.getLeafs(this.head)) {
         OsmStreamOutput output = this.outputFactory.init(leaf, true);
         this.outputs.put(leaf, output);
      }
   }

   @Override
   protected void distributeNodes() throws IOException {
      Runnable distributor = new NodeIteratorRunnable(this.iterator) {
         @Override
         protected void handle(OsmNode node) throws IOException {
            ThreadedNodeTreeDistributor.this.handle(node);
         }

         @Override
         protected void finished() throws IOException {
            ThreadedNodeTreeDistributor.this.buffer.close();
         }
      };
      Runnable writer = new WriterRunner(this.buffer);
      List<Runnable> tasks = new ArrayList<>();
      tasks.add(distributor);
      tasks.add(writer);
      ParallelExecutor executor = new ParallelExecutor(tasks);
      executor.execute();
   }

   protected void handle(OsmNode node) throws IOException {
      for (Node leaf : this.tree.query(this.head, node.getLongitude(), node.getLatitude())) {
         if (leaf.getEnvelope().contains(node.getLongitude(), node.getLatitude())) {
            OsmStreamOutput output = this.outputs.get(leaf);
            this.buffer.write(new NodeWriteRequest(node, output.getOsmOutput()));
         }
      }
   }
}
