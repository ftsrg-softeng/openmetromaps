package de.topobyte.jeography.tiles.manager;

import de.topobyte.jeography.tiles.cache.MemoryCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractImageManagerWithMemoryCache<T, D> extends AbstractImageManager<T, D> {
   static final Logger logger = LoggerFactory.getLogger(AbstractImageManagerWithMemoryCache.class);
   protected int desiredCacheSize;
   protected MemoryCache<T, D> memoryCache;

   public AbstractImageManagerWithMemoryCache() {
      this(150);
   }

   public AbstractImageManagerWithMemoryCache(int desiredCacheSize) {
      this.desiredCacheSize = desiredCacheSize;
      this.memoryCache = new MemoryCache<>(desiredCacheSize);
      logger.debug("desired size: " + desiredCacheSize);
   }

   public void setCacheHintMinimumSize(int size) {
      if (this.memoryCache.getSize() < size) {
         this.memoryCache.setSize(size);
      } else if (size < this.desiredCacheSize) {
         this.memoryCache.setSize(this.desiredCacheSize);
      }
   }
}
