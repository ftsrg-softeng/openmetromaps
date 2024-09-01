package de.topobyte.osm4j.extra.nodearray;

import de.topobyte.osm4j.core.model.iface.OsmNode;
import java.io.DataOutputStream;
import java.io.IOException;

public class NodeArrayWriterShort extends BaseNodeArrayWriter {
   public NodeArrayWriterShort(DataOutputStream out) {
      super(out);
   }

   @Override
   public void write(OsmNode node) throws IOException {
      while (this.lastId < node.getId() - 1L) {
         this.out.writeShort(NodeArrayShort.NULL);
         this.out.writeShort(NodeArrayShort.NULL);
         this.lastId++;
      }

      this.out.writeShort(Coding.encodeLonAsShort(node.getLongitude()));
      this.out.writeShort(Coding.encodeLatAsShort(node.getLatitude()));
      this.lastId++;
   }
}
