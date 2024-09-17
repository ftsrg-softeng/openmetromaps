package de.topobyte.osm4j.extra.nodearray;

import de.topobyte.osm4j.core.model.iface.OsmNode;
import java.io.DataOutputStream;
import java.io.IOException;

public class NodeArrayWriterFloat extends BaseNodeArrayWriter {
   public NodeArrayWriterFloat(DataOutputStream out) {
      super(out);
   }

   @Override
   public void write(OsmNode node) throws IOException {
      while (this.lastId < node.getId() - 1L) {
         this.out.writeInt(Integer.MAX_VALUE);
         this.out.writeInt(Integer.MAX_VALUE);
         this.lastId++;
      }

      this.out.writeFloat((float)node.getLongitude());
      this.out.writeFloat((float)node.getLatitude());
      this.lastId++;
   }
}
