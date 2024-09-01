package de.topobyte.jeography.tiles.manager;

import de.topobyte.jeography.core.Tile;
import de.topobyte.jeography.tiles.LoadListener;
import de.topobyte.jeography.tiles.source.ImageProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProviderImageManager<D> extends AbstractImageManagerWithMemoryCache<Tile, D> {
   static final Logger logger = LoggerFactory.getLogger(ProviderImageManager.class);
   private ImageProvider<Tile, D> imageProvider;

   public ProviderImageManager(ImageProvider<Tile, D> imageProvider) {
      this(imageProvider, 150);
   }

   public ProviderImageManager(ImageProvider<Tile, D> imageProvider, int cacheSize) {
      super(cacheSize);
      this.imageProvider = imageProvider;
      imageProvider.addLoadListener(new ProviderImageManager.LoadListenerImpl());
   }

   public D get(Tile thing) {
      D image = this.memoryCache.get(thing);
      if (image != null) {
         return image;
      } else {
         if (this.imageProvider != null) {
            this.imageProvider.provide(thing);
         }

         return null;
      }
   }

   public void destroy() {
   }

   public void unchache(Tile tile) {
      this.memoryCache.remove(tile);
   }

   public void clearCache() {
      this.memoryCache.clear();
   }

   public ImageProvider<Tile, D> getImageProvider() {
      return this.imageProvider;
   }

   public void willNeed(Tile thing) {
   }

   private class LoadListenerImpl implements LoadListener<Tile, D> {
      public LoadListenerImpl() {
      }

      public void loaded(Tile thing, D image) {
         ProviderImageManager.this.memoryCache.put(thing, image);
         ProviderImageManager.this.notifyListeners(thing, image);
      }

      public void loadFailed(Tile thing) {
      }
   }
}
