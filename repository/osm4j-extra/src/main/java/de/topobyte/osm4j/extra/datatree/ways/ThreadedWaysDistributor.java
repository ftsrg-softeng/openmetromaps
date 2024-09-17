package de.topobyte.osm4j.extra.datatree.ways;

import com.slimjars.dist.gnu.trove.map.TLongObjectMap;
import de.topobyte.osm4j.core.access.OsmStreamOutput;
import de.topobyte.osm4j.core.model.iface.OsmNode;
import de.topobyte.osm4j.core.model.iface.OsmWay;
import de.topobyte.osm4j.core.resolve.OsmEntityProvider;
import de.topobyte.osm4j.extra.datatree.Node;
import de.topobyte.osm4j.extra.threading.Buffer;
import de.topobyte.osm4j.extra.threading.ObjectBuffer;
import de.topobyte.osm4j.extra.threading.write.NodeWriteRequest;
import de.topobyte.osm4j.extra.threading.write.WayWriteRequest;
import de.topobyte.osm4j.extra.threading.write.WriteRequest;
import de.topobyte.osm4j.extra.threading.write.WriterRunner;
import de.topobyte.osm4j.utils.FileFormat;
import de.topobyte.osm4j.utils.OsmOutputConfig;
import de.topobyte.osm4j.utils.buffer.ParallelExecutor;
import de.topobyte.osm4j.utils.buffer.StoppableRunnable;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class ThreadedWaysDistributor extends AbstractWaysDistributor {
   private Buffer<LeafData> bufferData = new Buffer<>(1);
   private ObjectBuffer<WriteRequest> bufferWriter = new ObjectBuffer<>(1000, 100);

   public ThreadedWaysDistributor(
      Path pathTree,
      String fileNamesNodes1,
      String fileNamesNodes2,
      String fileNamesWays,
      String fileNamesOutputWays,
      String fileNamesOutputNodes,
      FileFormat inputFormatNodes,
      FileFormat inputFormatWays,
      OsmOutputConfig outputConfig
   ) {
      super(
         pathTree, fileNamesNodes1, fileNamesNodes2, fileNamesWays, fileNamesOutputWays, fileNamesOutputNodes, inputFormatNodes, inputFormatWays, outputConfig
      );
   }

   @Override
   public void execute() throws IOException {
      this.prepare();
      Runnable loader = new StoppableRunnable() {
         public void run() {
            try {
               ThreadedWaysDistributor.this.distribute();
               ThreadedWaysDistributor.this.bufferData.complete();
            } catch (IOException var2) {
               throw new RuntimeException(var2);
            }
         }

         public void stop() {
            ThreadedWaysDistributor.this.stopped = true;
            ThreadedWaysDistributor.this.bufferData.setInvalid();
         }
      };
      Runnable distributor = new StoppableRunnable() {
         public void run() {
            try {
               for (LeafData data : ThreadedWaysDistributor.this.bufferData) {
                  ThreadedWaysDistributor.this.processLeafData(data);
               }

               ThreadedWaysDistributor.this.bufferWriter.close();
            } catch (IOException var3) {
               throw new RuntimeException(var3);
            }
         }

         public void stop() {
            ThreadedWaysDistributor.this.bufferWriter.setInvalid();
         }
      };
      WriterRunner writer = new WriterRunner(this.bufferWriter);
      List<Runnable> tasks = new ArrayList<>();
      tasks.add(loader);
      tasks.add(distributor);
      tasks.add(writer);
      ParallelExecutor executor = new ParallelExecutor(tasks);
      executor.execute();
      this.finish();
   }

   @Override
   protected void leafData(LeafData leafData) throws IOException {
      this.bufferData.write(leafData);
   }

   private void processLeafData(LeafData leafData) throws IOException {
      OsmEntityProvider entityProvider = leafData.getNodeProvider();

      for (OsmWay way : leafData.getDataWays().getWays()) {
         this.build(leafData.getLeaf(), way, entityProvider);
      }

      this.bufferData.returnObject(leafData);
   }

   @Override
   protected void write(Node leaf, OsmWay way, TLongObjectMap<OsmNode> nodes) throws IOException {
      OsmStreamOutput wayOutput = this.outputsWays.get(leaf);
      OsmStreamOutput nodeOutput = this.outputsNodes.get(leaf);
      this.bufferWriter.write(new WayWriteRequest(way, wayOutput.getOsmOutput()));

      for (OsmNode node : nodes.valueCollection()) {
         this.bufferWriter.write(new NodeWriteRequest(node, nodeOutput.getOsmOutput()));
      }
   }
}
