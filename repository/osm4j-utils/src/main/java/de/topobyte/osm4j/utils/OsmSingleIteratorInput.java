package de.topobyte.osm4j.utils;

import de.topobyte.osm4j.core.access.OsmIterator;
import de.topobyte.osm4j.core.access.OsmIteratorInput;
import java.io.IOException;
import java.io.InputStream;

public class OsmSingleIteratorInput implements OsmIteratorInput {
   private InputStream input;
   private OsmIterator iterator;

   public OsmSingleIteratorInput(InputStream input, OsmIterator iterator) {
      this.input = input;
      this.iterator = iterator;
   }

   public void close() throws IOException {
      this.input.close();
   }

   public OsmIterator getIterator() {
      return this.iterator;
   }
}
