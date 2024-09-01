package de.topobyte.osm4j.extra.extracts.query;

import de.topobyte.melon.io.StreamUtil;
import de.topobyte.osm4j.core.access.OsmIteratorInput;
import de.topobyte.osm4j.core.access.OsmOutputStream;
import de.topobyte.osm4j.core.access.OsmOutputStreamStreamOutput;
import de.topobyte.osm4j.core.access.OsmStreamOutput;
import de.topobyte.osm4j.core.dataset.InMemoryListDataSet;
import de.topobyte.osm4j.core.dataset.ListDataSetLoader;
import de.topobyte.osm4j.utils.FileFormat;
import de.topobyte.osm4j.utils.OsmFileInput;
import de.topobyte.osm4j.utils.OsmIoUtils;
import de.topobyte.osm4j.utils.OsmOutputConfig;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Path;

public abstract class AbstractQuery {
   protected FileFormat inputFormat;
   protected OsmOutputConfig outputConfigIntermediate;
   protected OsmOutputConfig outputConfig;

   public AbstractQuery(FileFormat inputFormat, OsmOutputConfig outputConfigIntermediate, OsmOutputConfig outputConfig) {
      this.inputFormat = inputFormat;
      this.outputConfigIntermediate = outputConfigIntermediate;
      this.outputConfig = outputConfig;
   }

   protected String filename(int index) {
      return String.format("%d%s", index, OsmIoUtils.extension(this.outputConfigIntermediate.getFileFormat()));
   }

   protected OsmStreamOutput createOutput(Path path) throws IOException {
      OutputStream outputStream = StreamUtil.bufferedOutputStream(path);
      OsmOutputStream osmOutputStream = OsmIoUtils.setupOsmOutput(outputStream, this.outputConfigIntermediate);
      return new OsmOutputStreamStreamOutput(outputStream, osmOutputStream);
   }

   protected OsmStreamOutput createFinalOutput(Path path) throws IOException {
      OutputStream outputStream = StreamUtil.bufferedOutputStream(path);
      OsmOutputStream osmOutputStream = OsmIoUtils.setupOsmOutput(outputStream, this.outputConfig);
      return new OsmOutputStreamStreamOutput(outputStream, osmOutputStream);
   }

   protected void finish(OsmStreamOutput osmOutput) throws IOException {
      osmOutput.getOsmOutput().complete();
      osmOutput.close();
   }

   protected InMemoryListDataSet read(Path path) throws IOException {
      OsmFileInput fileInput = new OsmFileInput(path, this.inputFormat);
      OsmIteratorInput input = fileInput.createIterator(true, this.outputConfig.isWriteMetadata());
      InMemoryListDataSet data = ListDataSetLoader.read(input.getIterator(), true, true, true);
      input.close();
      return data;
   }
}
