package de.topobyte.osm4j.extra.idbboxlist;

import com.vividsolutions.jts.geom.Envelope;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

public class IdBboxListInputStream implements IdBboxInput {
   private DataInputStream dataInput;

   public IdBboxListInputStream(InputStream input) {
      this.dataInput = new DataInputStream(input);
   }

   @Override
   public void close() throws IOException {
      this.dataInput.close();
   }

   @Override
   public IdBboxEntry next() throws IOException {
      long id = this.dataInput.readLong();
      double minX = this.dataInput.readDouble();
      double maxX = this.dataInput.readDouble();
      double minY = this.dataInput.readDouble();
      double maxY = this.dataInput.readDouble();
      int size = this.dataInput.readInt();
      return new IdBboxEntry(id, new Envelope(minX, maxX, minY, maxY), size);
   }
}
