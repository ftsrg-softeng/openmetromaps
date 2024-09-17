package de.topobyte.jeography.tiles.source;

import de.topobyte.jeography.tiles.CachePathProvider;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.imageio.ImageIO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ImageSourcePathPattern<T> implements ImageSource<T, BufferedImage> {
   static final Logger logger = LoggerFactory.getLogger(ImageSourcePathPattern.class);
   private CachePathProvider<T> resolver;

   public ImageSourcePathPattern(CachePathProvider<T> resolver) {
      this.resolver = resolver;
   }

   public void setPathResoluter(CachePathProvider<T> resolver) {
      this.resolver = resolver;
   }

   public BufferedImage load(T thing) {
      BufferedImage image = this.loadImage(thing);
      return image == null ? null : image;
   }

   public BufferedImage loadImage(T thing) {
      String cacheFile = this.resolver.getCacheFile(thing);
      File file = new File(cacheFile);
      if (!file.exists()) {
         return null;
      } else {
         FileInputStream fis = null;

         try {
            fis = new FileInputStream(file);
            return ImageIO.read(fis);
         } catch (FileNotFoundException var18) {
            var18.printStackTrace();
         } catch (IOException var19) {
            var19.printStackTrace();
         } finally {
            if (fis != null) {
               try {
                  fis.close();
               } catch (IOException var17) {
                  logger.warn("unable to close FileInputStream: " + var17.getMessage());
               }
            }
         }

         return null;
      }
   }
}
