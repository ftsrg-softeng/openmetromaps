package de.topobyte.osm4j.extra.datatree.ways;

import com.slimjars.dist.gnu.trove.map.TLongObjectMap;
import com.slimjars.dist.gnu.trove.map.hash.TLongObjectHashMap;
import de.topobyte.largescalefileio.ClosingFileOutputStreamFactory;
import de.topobyte.largescalefileio.SimpleClosingFileOutputStreamFactory;
import de.topobyte.osm4j.core.access.OsmIterator;
import de.topobyte.osm4j.core.access.OsmOutputStream;
import de.topobyte.osm4j.core.access.OsmOutputStreamStreamOutput;
import de.topobyte.osm4j.core.access.OsmStreamOutput;
import de.topobyte.osm4j.core.model.iface.OsmWay;
import de.topobyte.osm4j.extra.datatree.DataTree;
import de.topobyte.osm4j.extra.datatree.DataTreeFiles;
import de.topobyte.osm4j.extra.datatree.DataTreeOpener;
import de.topobyte.osm4j.extra.datatree.Node;
import de.topobyte.osm4j.extra.threading.ObjectBuffer;
import de.topobyte.osm4j.extra.threading.TaskRunnable;
import de.topobyte.osm4j.extra.threading.write.WayWriteRequest;
import de.topobyte.osm4j.extra.threading.write.WriteRequest;
import de.topobyte.osm4j.extra.threading.write.WriterRunner;
import de.topobyte.osm4j.utils.FileFormat;
import de.topobyte.osm4j.utils.OsmIoUtils;
import de.topobyte.osm4j.utils.OsmOutputConfig;
import de.topobyte.osm4j.utils.buffer.ParallelExecutor;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class ThreadedWaysToTreeMapper implements WaysToTreeMapper {
   private Path pathTree;
   private String fileNamesOutput;
   private OsmOutputConfig outputConfig;
   private AbstractWaysToTreeMapper mapper;
   private ObjectBuffer<WriteRequest> buffer = new ObjectBuffer<>(10000, 100);
   private DataTree tree;
   private TLongObjectMap<OsmStreamOutput> outputs = new TLongObjectHashMap();

   public ThreadedWaysToTreeMapper(
      OsmIterator nodeIterator, Path pathTree, Path pathWays, FileFormat inputFormatWays, String fileNamesOutput, OsmOutputConfig outputConfig
   ) {
      this.pathTree = pathTree;
      this.fileNamesOutput = fileNamesOutput;
      this.outputConfig = outputConfig;
      this.mapper = new AbstractWaysToTreeMapper(nodeIterator, pathTree, pathWays, inputFormatWays, outputConfig.isWriteMetadata()) {
         @Override
         protected void process(OsmWay way, Node leaf) throws IOException {
            ThreadedWaysToTreeMapper.this.put(way, leaf);
         }

         @Override
         protected void finish() throws IOException {
            super.finish();
            ThreadedWaysToTreeMapper.this.buffer.close();
         }
      };
   }

   @Override
   public void execute() throws IOException {
      this.prepare();
      Runnable runnableMapper = new TaskRunnable(this.mapper);
      WriterRunner writer = new WriterRunner(this.buffer);
      List<Runnable> tasks = new ArrayList<>();
      tasks.add(runnableMapper);
      tasks.add(writer);
      ParallelExecutor executor = new ParallelExecutor(tasks);
      executor.execute();
      this.finish();
   }

   protected void put(OsmWay way, Node leaf) throws IOException {
      OsmStreamOutput output = (OsmStreamOutput)this.outputs.get(leaf.getPath());
      this.buffer.write(new WayWriteRequest(way, output.getOsmOutput()));
   }

   private void prepare() throws IOException {
      this.tree = DataTreeOpener.open(this.pathTree.toFile());
      DataTreeFiles filesOutput = new DataTreeFiles(this.pathTree, this.fileNamesOutput);
      List<Node> leafs = this.tree.getLeafs();
      ClosingFileOutputStreamFactory factoryOut = new SimpleClosingFileOutputStreamFactory();

      for (Node leaf : leafs) {
         File fileOutput = filesOutput.getFile(leaf);
         OutputStream output = factoryOut.create(fileOutput);
         OutputStream var10 = new BufferedOutputStream(output);
         OsmOutputStream osmOutput = OsmIoUtils.setupOsmOutput(var10, this.outputConfig, true);
         OsmStreamOutput out = new OsmOutputStreamStreamOutput(var10, osmOutput);
         this.outputs.put(leaf.getPath(), out);
      }
   }

   private void finish() throws IOException {
      for (OsmStreamOutput output : this.outputs.valueCollection()) {
         output.getOsmOutput().complete();
         output.close();
      }
   }
}
