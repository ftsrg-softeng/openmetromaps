package de.topobyte.osm4j.extra.nodearray;

import de.topobyte.melon.io.StreamUtil;
import de.topobyte.osm4j.core.access.OsmIterator;
import de.topobyte.osm4j.core.model.iface.EntityContainer;
import de.topobyte.osm4j.core.model.iface.EntityType;
import de.topobyte.osm4j.core.model.iface.OsmNode;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Path;

public class NodeArrayCreator {
   private OsmIterator input;
   private Path outputPath;
   private NodeArrayType type;
   private NodeArrayWriter writer;

   public NodeArrayCreator(OsmIterator input, Path outputPath, NodeArrayType type) {
      this.input = input;
      this.outputPath = outputPath;
      this.type = type;
   }

   public void execute() throws IOException {
      this.initOutput();
      this.run();
   }

   private void initOutput() throws IOException {
      OutputStream bos = StreamUtil.bufferedOutputStream(this.outputPath);
      DataOutputStream out = new DataOutputStream(bos);
      switch (this.type) {
         case DOUBLE:
         default:
            this.writer = new NodeArrayWriterDouble(out);
            break;
         case FLOAT:
            this.writer = new NodeArrayWriterFloat(out);
            break;
         case INTEGER:
            this.writer = new NodeArrayWriterInteger(out);
            break;
         case SHORT:
            this.writer = new NodeArrayWriterShort(out);
      }
   }

   private void run() throws IOException {
      while (this.input.hasNext()) {
         EntityContainer container = (EntityContainer)this.input.next();
         if (container.getType() == EntityType.Node) {
            OsmNode node = (OsmNode)container.getEntity();
            this.writer.write(node);
            continue;
         }
         break;
      }

      this.writer.finish();
   }
}
