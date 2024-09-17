package de.topobyte.osm4j.pbf.raf;

public class BlockInfo {
   private long position;
   private int lengthHeader;
   private int lengthData;

   public BlockInfo(long position, int lengthHeader, int lengthData) {
      this.position = position;
      this.lengthHeader = lengthHeader;
      this.lengthData = lengthData;
   }

   public long getPosition() {
      return this.position;
   }

   public int getLengthHeader() {
      return this.lengthHeader;
   }

   public int getLengthData() {
      return this.lengthData;
   }
}
