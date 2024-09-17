package de.topobyte.osm4j.extra.nodearray;

import de.topobyte.osm4j.core.model.iface.OsmNode;
import java.io.DataOutputStream;
import java.io.IOException;

public class NodeArrayWriterDouble extends BaseNodeArrayWriter {
   public NodeArrayWriterDouble(DataOutputStream out) {
      super(out);
   }

   @Override
   public void write(OsmNode node) throws IOException {
      while (this.lastId < node.getId() - 1L) {
         this.out.writeLong(Long.MAX_VALUE);
         this.out.writeLong(Long.MAX_VALUE);
         this.lastId++;
      }

      this.out.writeDouble(node.getLongitude());
      this.out.writeDouble(node.getLatitude());
      this.lastId++;
   }
}
