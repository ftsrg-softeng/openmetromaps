package de.topobyte.osm4j.utils;

import de.topobyte.osm4j.core.access.OsmReader;
import de.topobyte.osm4j.core.access.OsmReaderInput;
import java.io.IOException;
import java.io.InputStream;

public class OsmSingleReaderInput implements OsmReaderInput {
   private InputStream input;
   private OsmReader reader;

   public OsmSingleReaderInput(InputStream input, OsmReader reader) {
      this.input = input;
      this.reader = reader;
   }

   public void close() throws IOException {
      this.input.close();
   }

   public OsmReader getReader() {
      return this.reader;
   }
}
