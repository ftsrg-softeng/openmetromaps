package de.topobyte.osm4j.tbo.writerhelper;

import de.topobyte.compactio.CompactWriter;
import de.topobyte.compactio.OutputStreamCompactWriter;
import de.topobyte.osm4j.core.model.iface.OsmNode;
import de.topobyte.osm4j.tbo.ByteArrayOutputStream;
import java.io.IOException;

public class NodeBatch extends EntityBatch<OsmNode> {
   private long latOffset = 0L;
   private long lonOffset = 0L;

   public NodeBatch(boolean writeMetadata) {
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
      this.writeCoords(bwriter);
      this.writeAndReset(writer, baos);
      this.writeTags(bwriter);
      this.writeAndReset(writer, baos);
      this.writeMetadata(bwriter);
      this.writeAndReset(writer, baos);
   }

   private void writeCoords(CompactWriter writer) throws IOException {
      for (OsmNode node : this.elements) {
         double lat = node.getLatitude();
         double lon = node.getLongitude();
         long mlat = this.toLong(lat);
         long mlon = this.toLong(lon);
         writer.writeVariableLengthSignedInteger(mlat - this.latOffset);
         writer.writeVariableLengthSignedInteger(mlon - this.lonOffset);
         this.latOffset = mlat;
         this.lonOffset = mlon;
      }
   }

   private long toLong(double degrees) {
      return (long)(degrees / 1.0E-7);
   }

   @Override
   public void clear() {
      super.clear();
      this.latOffset = 0L;
      this.lonOffset = 0L;
   }
}
