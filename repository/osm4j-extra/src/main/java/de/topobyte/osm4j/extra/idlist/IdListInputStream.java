package de.topobyte.osm4j.extra.idlist;

import de.topobyte.compactio.CompactReader;
import de.topobyte.compactio.InputStreamCompactReader;
import java.io.IOException;
import java.io.InputStream;

public class IdListInputStream implements IdInput {
   private InputStream input;
   private CompactReader reader;
   private long last = 0L;

   public IdListInputStream(InputStream input) {
      this.input = input;
      this.reader = new InputStreamCompactReader(input);
   }

   @Override
   public void close() throws IOException {
      this.input.close();
   }

   @Override
   public long next() throws IOException {
      long diff = this.reader.readVariableLengthUnsignedInteger();
      long id = this.last + diff;
      this.last = id;
      return id;
   }
}
