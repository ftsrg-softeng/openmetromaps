package de.topobyte.osm4j.extra.datatree.output;

import com.vividsolutions.jts.geom.Envelope;
import de.topobyte.largescalefileio.ClosingFileOutputStreamFactory;
import de.topobyte.largescalefileio.SimpleClosingFileOutputStreamFactory;
import de.topobyte.osm4j.core.access.OsmOutputStream;
import de.topobyte.osm4j.core.access.OsmOutputStreamStreamOutput;
import de.topobyte.osm4j.core.access.OsmStreamOutput;
import de.topobyte.osm4j.core.model.impl.Bounds;
import de.topobyte.osm4j.extra.datatree.DataTreeFiles;
import de.topobyte.osm4j.extra.datatree.Node;
import de.topobyte.osm4j.utils.OsmIoUtils;
import de.topobyte.osm4j.utils.OsmOutputConfig;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class ClosingDataTreeOutputFactory implements DataTreeOutputFactory {
   private DataTreeFiles treeFiles;
   private OsmOutputConfig outputConfig;
   private ClosingFileOutputStreamFactory outputStreamFactory;

   public ClosingDataTreeOutputFactory(DataTreeFiles treeFiles, OsmOutputConfig outputConfig) {
      this.treeFiles = treeFiles;
      this.outputConfig = outputConfig;
      this.outputStreamFactory = new SimpleClosingFileOutputStreamFactory();
   }

   @Override
   public OsmStreamOutput init(Node leaf, boolean writeBounds) throws IOException {
      Path file = this.treeFiles.getPath(leaf);
      Path dir = this.treeFiles.getSubdirPath(leaf);
      Files.createDirectories(dir);
      OutputStream os = this.outputStreamFactory.create(file.toFile());
      OutputStream bos = new BufferedOutputStream(os);
      OsmOutputStream osmOutput = OsmIoUtils.setupOsmOutput(bos, this.outputConfig, true);
      OsmStreamOutput output = new OsmOutputStreamStreamOutput(bos, osmOutput);
      if (writeBounds) {
         Envelope box = leaf.getEnvelope();
         osmOutput.write(new Bounds(box.getMinX(), box.getMaxX(), box.getMaxY(), box.getMinY()));
      }

      return output;
   }
}
