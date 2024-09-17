package de.topobyte.jeography.tiles;

import java.awt.image.BufferedImage;

public class BufferedImageAndBytes {
   BufferedImage image;
   byte[] bytes;

   public BufferedImageAndBytes(BufferedImage image, byte[] bytes) {
      this.image = image;
      this.bytes = bytes;
   }

   public BufferedImage getImage() {
      return this.image;
   }

   public byte[] getBytes() {
      return this.bytes;
   }
}
