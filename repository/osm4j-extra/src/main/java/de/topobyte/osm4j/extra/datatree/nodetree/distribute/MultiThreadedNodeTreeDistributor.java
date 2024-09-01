package de.topobyte.osm4j.extra.datatree.nodetree.distribute;

import de.topobyte.osm4j.core.access.OsmIterator;
import de.topobyte.osm4j.core.access.OsmStreamOutput;
import de.topobyte.osm4j.core.model.iface.OsmNode;
import de.topobyte.osm4j.extra.datatree.DataTree;
import de.topobyte.osm4j.extra.datatree.Node;
import de.topobyte.osm4j.extra.datatree.output.DataTreeOutputFactory;
import de.topobyte.osm4j.extra.threading.ObjectBuffer;
import de.topobyte.osm4j.extra.threading.StatusRunnable;
import de.topobyte.osm4j.extra.threading.write.NodeWriteRequest;
import de.topobyte.osm4j.extra.threading.write.WriteRequest;
import de.topobyte.osm4j.extra.threading.write.WriterRunner;
import de.topobyte.osm4j.utils.buffer.OsmBuffer;
import de.topobyte.osm4j.utils.buffer.ParallelExecutor;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MultiThreadedNodeTreeDistributor extends AbstractNodeTreeDistributor {
   private int numOutputThreads;
   private List<DataTreeOutputFactory> outputFactories;
   private OsmBuffer buffer;
   private List<ObjectBuffer<WriteRequest>> obuffers;

   public MultiThreadedNodeTreeDistributor(DataTree tree, Node head, DataTreeOutputFactory outputFactory, OsmIterator iterator, int numOutputThreads) {
      super(tree, head, iterator);
      this.numOutputThreads = numOutputThreads;
      this.outputFactories = new ArrayList<>();

      for (int i = 0; i < numOutputThreads; i++) {
         this.outputFactories.add(outputFactory);
      }
   }

   public MultiThreadedNodeTreeDistributor(DataTree tree, Node head, List<DataTreeOutputFactory> outputFactories, OsmIterator iterator) {
      super(tree, head, iterator);
      this.outputFactories = outputFactories;
      this.numOutputThreads = outputFactories.size();
   }

   @Override
   protected void initOutputs() throws IOException {
      for (Node leaf : this.tree.getLeafs()) {
         DataTreeOutputFactory outputFactory = this.outputFactories.get(this.bucket(leaf));
         outputFactory.init(leaf, true);
      }
   }

   private void initBuffers() {
      this.buffer = new OsmBuffer(10000, 100);
      this.obuffers = new ArrayList<>();

      for (int i = 0; i < this.numOutputThreads; i++) {
         this.obuffers.add(new ObjectBuffer<>(10000, 100));
      }
   }

   @Override
   protected void distributeNodes() throws IOException {
      this.initBuffers();
      this.run();
   }

   private void printStatus() {
      StringBuilder b = new StringBuilder();
      b.append("buffer status: ");
      b.append(this.buffer.getSize());

      for (ObjectBuffer<WriteRequest> obuffer : this.obuffers) {
         b.append(", ");
         b.append(obuffer.getSize());
      }

      System.out.println(b.toString());
   }

   private void run() throws IOException {
      StatusRunnable status = new StatusRunnable(1000L) {
         @Override
         protected void printStatus() {
            MultiThreadedNodeTreeDistributor.this.printStatus();
         }
      };
      new Thread(status).start();
      List<Runnable> tasks = new ArrayList<>();
      Runnable distributor = new NodeIteratorRunnable(this.iterator) {
         @Override
         protected void handle(OsmNode node) throws IOException {
            MultiThreadedNodeTreeDistributor.this.handle(node);
         }

         @Override
         protected void finished() throws IOException {
            for (ObjectBuffer<WriteRequest> buffer : MultiThreadedNodeTreeDistributor.this.obuffers) {
               buffer.close();
            }
         }
      };
      tasks.add(distributor);

      for (int i = 0; i < this.numOutputThreads; i++) {
         WriterRunner writer = new WriterRunner(this.obuffers.get(i));
         tasks.add(writer);
      }

      ParallelExecutor executor = new ParallelExecutor(tasks);
      executor.execute();
      status.stop();
   }

   private void handle(OsmNode node) throws IOException {
      for (Node leaf : this.tree.query(this.head, node.getLongitude(), node.getLatitude())) {
         int bucket = this.bucket(leaf);
         ObjectBuffer<WriteRequest> buffer = this.obuffers.get(bucket);
         OsmStreamOutput output = this.outputs.get(leaf);
         buffer.write(new NodeWriteRequest(node, output.getOsmOutput()));
      }
   }

   private int bucket(Node leaf) {
      long path = leaf.getPath();
      return (int)(path % (long)this.numOutputThreads);
   }
}
