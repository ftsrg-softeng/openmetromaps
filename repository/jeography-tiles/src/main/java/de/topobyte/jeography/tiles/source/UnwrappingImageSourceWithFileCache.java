package de.topobyte.jeography.tiles.source;

import de.topobyte.jeography.tiles.BufferedImageAndBytes;
import de.topobyte.jeography.tiles.CachePathProvider;
import de.topobyte.jeography.tiles.FileCache;
import java.awt.image.BufferedImage;

public class UnwrappingImageSourceWithFileCache<T> implements ImageSource<T, BufferedImage> {
   public ImageSource<T, BufferedImageAndBytes> source;
   private FileCache<T> cache;

   public UnwrappingImageSourceWithFileCache(ImageSource<T, BufferedImageAndBytes> source, CachePathProvider<T> resolver) {
      this.source = source;
      this.cache = new FileCache<>(resolver);
   }

   public BufferedImage load(T thing) {
      BufferedImageAndBytes result = (BufferedImageAndBytes)this.source.load(thing);
      if (result != null) {
         this.cache.push(thing, result);
         return result.getImage();
      } else {
         return null;
      }
   }
}
