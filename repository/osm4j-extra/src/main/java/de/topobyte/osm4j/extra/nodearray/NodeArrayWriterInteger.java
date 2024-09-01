package de.topobyte.osm4j.extra.nodearray;

import de.topobyte.osm4j.core.model.iface.OsmNode;
import java.io.DataOutputStream;
import java.io.IOException;

public class NodeArrayWriterInteger extends BaseNodeArrayWriter {
   public NodeArrayWriterInteger(DataOutputStream out) {
      super(out);
   }

   @Override
   public void write(OsmNode node) throws IOException {
      while (this.lastId < node.getId() - 1L) {
         this.out.writeInt(NodeArrayInteger.NULL);
         this.out.writeInt(NodeArrayInteger.NULL);
         this.lastId++;
      }

      this.out.writeInt(Coding.encodeLonAsInt(node.getLongitude()));
      this.out.writeInt(Coding.encodeLatAsInt(node.getLatitude()));
      this.lastId++;
   }
}
