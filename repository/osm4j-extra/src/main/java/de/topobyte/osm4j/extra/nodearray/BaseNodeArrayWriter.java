package de.topobyte.osm4j.extra.nodearray;

import java.io.DataOutputStream;
import java.io.IOException;

public abstract class BaseNodeArrayWriter implements NodeArrayWriter {
   protected DataOutputStream out;
   protected long lastId = -1L;

   public BaseNodeArrayWriter(DataOutputStream out) {
      this.out = out;
   }

   @Override
   public void finish() throws IOException {
      this.out.close();
   }
}
