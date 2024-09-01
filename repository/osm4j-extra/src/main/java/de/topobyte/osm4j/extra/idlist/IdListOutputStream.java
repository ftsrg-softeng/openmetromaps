package de.topobyte.osm4j.extra.idlist;

import de.topobyte.compactio.CompactWriter;
import de.topobyte.compactio.OutputStreamCompactWriter;
import java.io.IOException;
import java.io.OutputStream;

public class IdListOutputStream {
   private OutputStream output;
   private CompactWriter writer;
   private long last = 0L;

   public IdListOutputStream(OutputStream output) {
      this.output = output;
      this.writer = new OutputStreamCompactWriter(output);
   }

   public void close() throws IOException {
      this.output.close();
   }

   public void write(long id) throws IOException {
      if (id <= this.last) {
         throw new IOException(String.format("ids must be strictly monotonically increasing (%d <= %d)", id, this.last));
      } else {
         long diff = id - this.last;
         this.writer.writeVariableLengthUnsignedInteger(diff);
         this.last = id;
      }
   }
}
