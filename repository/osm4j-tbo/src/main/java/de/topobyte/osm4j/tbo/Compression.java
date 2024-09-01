package de.topobyte.osm4j.tbo;

public enum Compression {
   NONE(0),
   DEFLATE(1),
   LZ4(2);

   private int id;

   private Compression(int id) {
      this.id = id;
   }

   public int getId() {
      return this.id;
   }
}
