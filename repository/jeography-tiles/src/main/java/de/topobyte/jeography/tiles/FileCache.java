package de.topobyte.jeography.tiles;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileCache<T> {
   static final Logger logger = LoggerFactory.getLogger(FileCache.class);
   private CachePathProvider<T> resolver;

   public FileCache(CachePathProvider<T> resolver) {
      this.resolver = resolver;
   }

   public void push(T thing, BufferedImageAndBytes biab) {
      String cacheFileName = this.resolver.getCacheFile(thing);
      logger.debug("Writing to cache: " + cacheFileName);

      try {
         FileOutputStream fos = new FileOutputStream(cacheFileName);
         fos.write(biab.bytes);
         fos.close();
      } catch (FileNotFoundException var5) {
         var5.printStackTrace();
      } catch (IOException var6) {
         var6.printStackTrace();
      }
   }
}
