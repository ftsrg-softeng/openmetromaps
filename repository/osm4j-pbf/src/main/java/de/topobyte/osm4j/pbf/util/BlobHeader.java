package de.topobyte.osm4j.pbf.util;

import com.google.protobuf.ByteString;

public class BlobHeader {
   private String type;
   private int dataLength;
   private ByteString indexData;

   public BlobHeader(String type, int dataLength, ByteString indexData) {
      this.type = type;
      this.dataLength = dataLength;
      this.indexData = indexData;
   }

   public String getType() {
      return this.type;
   }

   public int getDataLength() {
      return this.dataLength;
   }

   public ByteString getIndexData() {
      return this.indexData;
   }
}
