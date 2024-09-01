package de.topobyte.osm4j.extra.datatree.ways;

import de.topobyte.largescalefileio.ClosingFileOutputStreamFactory;
import de.topobyte.largescalefileio.SimpleClosingFileOutputStreamFactory;
import de.topobyte.osm4j.core.access.OsmIterator;
import de.topobyte.osm4j.core.access.OsmOutputStream;
import de.topobyte.osm4j.core.access.OsmOutputStreamStreamOutput;
import de.topobyte.osm4j.core.access.OsmStreamOutput;
import de.topobyte.osm4j.core.model.iface.OsmWay;
import de.topobyte.osm4j.extra.datatree.DataTreeFiles;
import de.topobyte.osm4j.extra.datatree.Node;
import de.topobyte.osm4j.utils.FileFormat;
import de.topobyte.osm4j.utils.OsmIoUtils;
import de.topobyte.osm4j.utils.OsmOutputConfig;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SimpleWaysToTreeMapper extends AbstractWaysToTreeMapper {
   private String fileNamesOutput;
   private OsmOutputConfig outputConfig;
   private Map<Node, OsmStreamOutput> outputs = new HashMap<>();

   public SimpleWaysToTreeMapper(
      OsmIterator nodeIterator, Path pathTree, Path pathWays, FileFormat inputFormatWays, String fileNamesOutput, OsmOutputConfig outputConfig
   ) {
      super(nodeIterator, pathTree, pathWays, inputFormatWays, outputConfig.isWriteMetadata());
      this.fileNamesOutput = fileNamesOutput;
      this.outputConfig = outputConfig;
   }

   @Override
   public void prepare() throws IOException {
      super.prepare();
      DataTreeFiles filesOutput = new DataTreeFiles(this.pathTree, this.fileNamesOutput);
      List<Node> leafs = this.tree.getLeafs();
      ClosingFileOutputStreamFactory factoryOut = new SimpleClosingFileOutputStreamFactory();

      for (Node leaf : leafs) {
         File fileOutput = filesOutput.getFile(leaf);
         OutputStream output = factoryOut.create(fileOutput);
         OutputStream var10 = new BufferedOutputStream(output);
         OsmOutputStream osmOutput = OsmIoUtils.setupOsmOutput(var10, this.outputConfig, true);
         OsmStreamOutput out = new OsmOutputStreamStreamOutput(var10, osmOutput);
         this.outputs.put(leaf, out);
      }
   }

   @Override
   public void execute() throws IOException {
      super.execute();

      for (OsmStreamOutput output : this.outputs.values()) {
         output.getOsmOutput().complete();
         output.close();
      }
   }

   @Override
   protected void process(OsmWay way, Node leaf) throws IOException {
      OsmStreamOutput output = this.outputs.get(leaf);
      output.getOsmOutput().write(way);
   }
}
