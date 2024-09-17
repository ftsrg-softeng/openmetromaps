package de.topobyte.swing.util;

import java.awt.image.BufferedImage;
import java.io.InputStream;
import javax.imageio.ImageIO;
import javax.swing.Icon;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ImageLoader {
   static final Logger logger = LoggerFactory.getLogger(ImageLoader.class);

   public static Icon load(String filename) {
      if (filename == null) {
         return null;
      } else {
         BufferedImage bi = null;

         try (InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(filename)) {
            bi = ImageIO.read(is);
         } catch (Exception var15) {
            logger.debug(String.format("unable to load icon: '%s'", filename), var15);
         }

         return bi != null ? new BufferedImageIcon(bi) : null;
      }
   }
}
