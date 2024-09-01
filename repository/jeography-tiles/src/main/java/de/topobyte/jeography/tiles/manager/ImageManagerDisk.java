package de.topobyte.jeography.tiles.manager;

import de.topobyte.jeography.tiles.CachePathProvider;
import de.topobyte.jeography.tiles.LoadListener;
import de.topobyte.jeography.tiles.source.ImageProviderDisk;
import java.awt.image.BufferedImage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ImageManagerDisk<T> extends AbstractImageManagerWithMemoryCache<T, BufferedImage> {
   static final Logger logger = LoggerFactory.getLogger(ImageManagerDisk.class);
   boolean online = true;
   ImageProviderDisk<T> diskProvider = null;

   public ImageManagerDisk(CachePathProvider<T> resolver) {
      this.diskProvider = new ImageProviderDisk<>(resolver);
      this.diskProvider.addLoadListener(new ImageManagerDisk.LoadListenerDisk(this));
   }

   public BufferedImage get(T thing) {
      BufferedImage image = this.memoryCache.get(thing);
      if (image != null) {
         return image;
      } else {
         this.diskProvider.provide(thing);
         return null;
      }
   }

   public void setNetworkState(boolean state) {
      this.online = state;
   }

   public boolean getNetworkState() {
      return this.online;
   }

   public void destroy() {
      this.diskProvider.stopRunning();
   }

   public void willNeed(T thing) {
   }

   private class LoadListenerDisk implements LoadListener<T, BufferedImage> {
      private ImageManagerDisk<T> manager;

      LoadListenerDisk(ImageManagerDisk<T> manager) {
         this.manager = manager;
      }

      public void loadFailed(T thing) {
         ImageManagerDisk.logger.debug("failed loading from disk");
         ImageManagerDisk.this.notifyListenersFail(thing);
      }

      public void loaded(T thing, BufferedImage image) {
         this.manager.memoryCache.put(thing, image);
         ImageManagerDisk.this.notifyListeners(thing, image);
      }
   }
}
