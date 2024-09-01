package de.topobyte.osm4j.pbf.util;

import com.google.protobuf.ByteString;
import de.topobyte.osm4j.pbf.Compression;

public class BlockData {
   private ByteString blobData;
   private Compression compression;

   public BlockData(ByteString blobData, Compression compression) {
      this.blobData = blobData;
      this.compression = compression;
   }

   public ByteString getBlobData() {
      return this.blobData;
   }

   public void setBlobData(ByteString blobData) {
      this.blobData = blobData;
   }

   public Compression getCompression() {
      return this.compression;
   }

   public void setCompression(Compression compression) {
      this.compression = compression;
   }
}
