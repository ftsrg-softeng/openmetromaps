package de.topobyte.awt.util;

import java.awt.image.BufferedImage;

public class ImageUtil {
   public static BufferedImage duplicate(BufferedImage image) {
      BufferedImage copy = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
      copy.getGraphics().drawImage(image, 0, 0, null);
      return copy;
   }
}
