package de.topobyte.jeography.tiles.manager;

import de.topobyte.jeography.tiles.cache.MemoryCachePlus;

public abstract class AbstractImageManagerWithMemoryCachePlus<T, D> extends AbstractImageManager<T, D> {
   protected int desiredCacheSize;
   protected MemoryCachePlus<T, D> memoryCache;

   public AbstractImageManagerWithMemoryCachePlus() {
      this(150);
   }

   public AbstractImageManagerWithMemoryCachePlus(int desiredCacheSize) {
      this.desiredCacheSize = desiredCacheSize;
      this.memoryCache = new MemoryCachePlus<>(desiredCacheSize);
   }

   public void willNeed(T thing) {
      this.memoryCache.refresh(thing);
   }

   public void setCacheHintMinimumSize(int size) {
      if (this.memoryCache.getSize() < size) {
         this.memoryCache.setSize(size);
      } else if (size < this.desiredCacheSize) {
         this.memoryCache.setSize(this.desiredCacheSize);
      }
   }
}
