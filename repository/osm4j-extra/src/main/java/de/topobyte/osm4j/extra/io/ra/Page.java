package de.topobyte.osm4j.extra.io.ra;

public class Page {
   private long offset;
   private byte[] data;

   public Page(long offset, byte[] data) {
      this.offset = offset;
      this.data = data;
   }

   public long getOffset() {
      return this.offset;
   }

   public byte[] getData() {
      return this.data;
   }

   @Override
   public String toString() {
      return "Page at offset " + this.offset;
   }
}
