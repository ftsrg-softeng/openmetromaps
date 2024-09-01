package de.topobyte.jeography.tiles.source;

import de.topobyte.jeography.tiles.BufferedImageAndBytes;
import de.topobyte.jeography.tiles.CachePathProvider;
import de.topobyte.jeography.tiles.FileCache;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.imageio.ImageIO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ImageProviderDisk<T> extends ImageProvider<T, BufferedImage> {
   static final Logger logger = LoggerFactory.getLogger(ImageProviderDisk.class);
   private CachePathProvider<T> resolver;
   private FileCache<T> cache;

   public ImageProviderDisk(CachePathProvider<T> resolver) {
      super(1);
      this.resolver = resolver;
      this.cache = new FileCache<>(resolver);
   }

   public BufferedImage load(T thing) {
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

   public void push(T thing, BufferedImageAndBytes biab) {
      this.cache.push(thing, biab);
   }
}
