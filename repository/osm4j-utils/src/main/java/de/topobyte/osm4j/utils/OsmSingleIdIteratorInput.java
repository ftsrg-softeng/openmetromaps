package de.topobyte.osm4j.utils;

import de.topobyte.osm4j.core.access.OsmIdIterator;
import de.topobyte.osm4j.core.access.OsmIdIteratorInput;
import java.io.IOException;
import java.io.InputStream;

public class OsmSingleIdIteratorInput implements OsmIdIteratorInput {
   private InputStream input;
   private OsmIdIterator iterator;

   public OsmSingleIdIteratorInput(InputStream input, OsmIdIterator iterator) {
      this.input = input;
      this.iterator = iterator;
   }

   public void close() throws IOException {
      this.input.close();
   }

   public OsmIdIterator getIterator() {
      return this.iterator;
   }
}
