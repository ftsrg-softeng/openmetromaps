package de.topobyte.osm4j.tbo.writerhelper;

import de.topobyte.compactio.CompactWriter;
import de.topobyte.compactio.OutputStreamCompactWriter;
import de.topobyte.osm4j.core.model.iface.OsmWay;
import de.topobyte.osm4j.tbo.ByteArrayOutputStream;
import java.io.IOException;

public class WayBatch extends EntityBatch<OsmWay> {
   private long nidOffset = 0L;

   public WayBatch(boolean writeMetadata) {
      super(writeMetadata);
   }

   @Override
   public void write(CompactWriter writer) throws IOException {
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      CompactWriter bwriter = new OutputStreamCompactWriter(baos);
      this.writeTagStringPool(bwriter);
      this.writeAndReset(writer, baos);
      this.writeIds(bwriter);
      this.writeAndReset(writer, baos);
      this.writeNodes(bwriter);
      this.writeAndReset(writer, baos);
      this.writeTags(bwriter);
      this.writeAndReset(writer, baos);
      this.writeMetadata(bwriter);
      this.writeAndReset(writer, baos);
   }

   private void writeNodes(CompactWriter writer) throws IOException {
      for (OsmWay way : this.elements) {
         int nNodes = way.getNumberOfNodes();
         writer.writeVariableLengthUnsignedInteger((long)nNodes);

         for (int i = 0; i < nNodes; i++) {
            long nid = way.getNodeId(i);
            writer.writeVariableLengthSignedInteger(nid - this.nidOffset);
            this.nidOffset = nid;
         }
      }
   }

   @Override
   public void clear() {
      super.clear();
      this.nidOffset = 0L;
   }
}
