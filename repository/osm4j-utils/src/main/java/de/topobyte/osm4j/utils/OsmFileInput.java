package de.topobyte.osm4j.utils;

import de.topobyte.melon.io.StreamUtil;
import de.topobyte.osm4j.core.access.OsmIdIterator;
import de.topobyte.osm4j.core.access.OsmIdIteratorInput;
import de.topobyte.osm4j.core.access.OsmIdReader;
import de.topobyte.osm4j.core.access.OsmIdReaderInput;
import de.topobyte.osm4j.core.access.OsmInputAccessFactory;
import de.topobyte.osm4j.core.access.OsmIterator;
import de.topobyte.osm4j.core.access.OsmIteratorInput;
import de.topobyte.osm4j.core.access.OsmReader;
import de.topobyte.osm4j.core.access.OsmReaderInput;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;

public class OsmFileInput implements OsmInputAccessFactory {
   private Path path;
   private FileFormat fileFormat;

   public OsmFileInput(OsmFile osmFile) {
      this.path = osmFile.getPath();
      this.fileFormat = osmFile.getFileFormat();
   }

   public OsmFileInput(Path path, FileFormat fileFormat) {
      this.path = path;
      this.fileFormat = fileFormat;
   }

   public OsmIteratorInput createIterator(boolean readTags, boolean readMetadata) throws IOException {
      InputStream input = StreamUtil.bufferedInputStream(this.path.toFile());
      OsmIterator iterator = OsmIoUtils.setupOsmIterator(input, this.fileFormat, readTags, readMetadata);
      return new OsmSingleIteratorInput(input, iterator);
   }

   public OsmReaderInput createReader(boolean readTags, boolean readMetadata) throws IOException {
      InputStream input = StreamUtil.bufferedInputStream(this.path.toFile());
      OsmReader reader = OsmIoUtils.setupOsmReader(input, this.fileFormat, readTags, readMetadata);
      return new OsmSingleReaderInput(input, reader);
   }

   public OsmIdIteratorInput createIdIterator() throws IOException {
      InputStream input = StreamUtil.bufferedInputStream(this.path.toFile());
      OsmIdIterator iterator = OsmIoUtils.setupOsmIdIterator(input, this.fileFormat);
      return new OsmSingleIdIteratorInput(input, iterator);
   }

   public OsmIdReaderInput createIdReader() throws IOException {
      InputStream input = StreamUtil.bufferedInputStream(this.path.toFile());
      OsmIdReader reader = OsmIoUtils.setupOsmIdReader(input, this.fileFormat);
      return new OsmSingleIdReaderInput(input, reader);
   }
}
