package de.topobyte.osm4j.pbf.seq;

public class ByteArray {
   private byte[] data;
   private int length;

   public ByteArray(byte[] data, int length) {
      this.data = data;
      this.length = length;
   }

   public byte[] getData() {
      return this.data;
   }

   public int getLength() {
      return this.length;
   }
}
