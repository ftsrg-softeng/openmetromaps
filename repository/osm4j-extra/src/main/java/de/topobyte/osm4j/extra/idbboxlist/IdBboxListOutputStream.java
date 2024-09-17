package de.topobyte.osm4j.extra.idbboxlist;

import com.vividsolutions.jts.geom.Envelope;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class IdBboxListOutputStream {
   private DataOutputStream dataOutput;

   public IdBboxListOutputStream(OutputStream output) {
      this.dataOutput = new DataOutputStream(output);
   }

   public void close() throws IOException {
      this.dataOutput.close();
   }

   public void write(IdBboxEntry entry) throws IOException {
      this.dataOutput.writeLong(entry.getId());
      Envelope e = entry.getEnvelope();
      this.dataOutput.writeDouble(e.getMinX());
      this.dataOutput.writeDouble(e.getMaxX());
      this.dataOutput.writeDouble(e.getMinY());
      this.dataOutput.writeDouble(e.getMaxY());
      this.dataOutput.writeInt(entry.getSize());
   }
}
