package de.topobyte.osm4j.utils;

import de.topobyte.osm4j.core.access.OsmIdReader;
import de.topobyte.osm4j.core.access.OsmIdReaderInput;
import java.io.IOException;
import java.io.InputStream;

public class OsmSingleIdReaderInput implements OsmIdReaderInput {
   private InputStream input;
   private OsmIdReader reader;

   public OsmSingleIdReaderInput(InputStream input, OsmIdReader reader) {
      this.input = input;
      this.reader = reader;
   }

   public void close() throws IOException {
      this.input.close();
   }

   public OsmIdReader getReader() {
      return this.reader;
   }
}
