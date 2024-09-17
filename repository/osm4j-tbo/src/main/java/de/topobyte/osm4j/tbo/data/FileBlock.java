package de.topobyte.osm4j.tbo.data;

import de.topobyte.osm4j.tbo.Compression;

public class FileBlock {
   private final int type;
   private final Compression compression;
   private final int uncompressedLength;
   private final int numObjects;
   private final byte[] buffer;
   private final int length;

   public FileBlock(int type, Compression compression, int uncompressedLength, int numObjects, byte[] buffer, int length) {
      this.type = type;
      this.compression = compression;
      this.uncompressedLength = uncompressedLength;
      this.numObjects = numObjects;
      this.buffer = buffer;
      this.length = length;
   }

   public int getType() {
      return this.type;
   }

   public Compression getCompression() {
      return this.compression;
   }

   public int getUncompressedLength() {
      return this.uncompressedLength;
   }

   public int getNumObjects() {
      return this.numObjects;
   }

   public byte[] getBuffer() {
      return this.buffer;
   }

   public int getLength() {
      return this.length;
   }
}
