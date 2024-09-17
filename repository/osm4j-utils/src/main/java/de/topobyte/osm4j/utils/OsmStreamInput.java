package de.topobyte.osm4j.utils;

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

public class OsmStreamInput implements OsmInputAccessFactory {
   private InputStream input;
   private FileFormat fileFormat;

   public OsmStreamInput(OsmInputStream osmStream) {
      this.input = osmStream.getInputStream();
      this.fileFormat = osmStream.getFileFormat();
   }

   public OsmStreamInput(InputStream input, FileFormat fileFormat) {
      this.input = input;
      this.fileFormat = fileFormat;
   }

   public OsmIteratorInput createIterator(boolean readTags, boolean readMetadata) throws IOException {
      OsmIterator iterator = OsmIoUtils.setupOsmIterator(this.input, this.fileFormat, readTags, readMetadata);
      return new OsmSingleIteratorInput(this.input, iterator);
   }

   public OsmReaderInput createReader(boolean readTags, boolean readMetadata) throws IOException {
      OsmReader reader = OsmIoUtils.setupOsmReader(this.input, this.fileFormat, readTags, readMetadata);
      return new OsmSingleReaderInput(this.input, reader);
   }

   public OsmIdIteratorInput createIdIterator() throws IOException {
      OsmIdIterator iterator = OsmIoUtils.setupOsmIdIterator(this.input, this.fileFormat);
      return new OsmSingleIdIteratorInput(this.input, iterator);
   }

   public OsmIdReaderInput createIdReader() throws IOException {
      OsmIdReader reader = OsmIoUtils.setupOsmIdReader(this.input, this.fileFormat);
      return new OsmSingleIdReaderInput(this.input, reader);
   }
}
