package de.topobyte.jeography.tiles.manager;

import de.topobyte.jeography.tiles.BufferedImageAndBytes;
import de.topobyte.jeography.tiles.CachePathProvider;
import de.topobyte.jeography.tiles.LoadListener;
import de.topobyte.jeography.tiles.UrlProvider;
import de.topobyte.jeography.tiles.source.ImageProviderDisk;
import de.topobyte.jeography.tiles.source.ImageProviderHttp;
import java.awt.image.BufferedImage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ImageManagerHttpDisk<T> extends AbstractImageManagerWithMemoryCachePlus<T, BufferedImage> {
   static final Logger logger = LoggerFactory.getLogger(ImageManagerHttpDisk.class);
   boolean online = true;
   ImageProviderDisk<T> diskProvider = null;
   ImageProviderHttp<T> httpProvider = null;

   public <X extends CachePathProvider<T> & UrlProvider<T>> ImageManagerHttpDisk(X resolver) {
      this.diskProvider = new ImageProviderDisk<>(resolver);
      this.httpProvider = new ImageProviderHttp<>(resolver, 4, 5);
      this.diskProvider.addLoadListener(new ImageManagerHttpDisk.LoadListenerDisk(this));
      this.httpProvider.addLoadListener(new ImageManagerHttpDisk.LoadListenerHttp(this));
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

   public void setUserAgent(String userAgent) {
      this.httpProvider.setUserAgent(userAgent);
   }

   public void destroy() {
      this.diskProvider.stopRunning();
      this.httpProvider.stopRunning();
   }

   private class LoadListenerDisk implements LoadListener<T, BufferedImage> {
      private ImageManagerHttpDisk<T> manager;

      LoadListenerDisk(ImageManagerHttpDisk<T> manager) {
         this.manager = manager;
      }

      public void loadFailed(T thing) {
         if (ImageManagerHttpDisk.this.online) {
            this.manager.httpProvider.provide(thing);
         }
      }

      public void loaded(T thing, BufferedImage image) {
         this.manager.memoryCache.put(thing, image);
         ImageManagerHttpDisk.this.notifyListeners(thing, image);
      }
   }

   private class LoadListenerHttp implements LoadListener<T, BufferedImageAndBytes> {
      private ImageManagerHttpDisk<T> manager;

      LoadListenerHttp(ImageManagerHttpDisk<T> manager) {
         this.manager = manager;
      }

      public void loadFailed(T thing) {
         ImageManagerHttpDisk.logger.debug("failed loading from HTTP ... giving up");
         ImageManagerHttpDisk.this.notifyListenersFail(thing);
      }

      public void loaded(T thing, BufferedImageAndBytes image) {
         this.manager.memoryCache.put(thing, image.getImage());
         ImageManagerHttpDisk.this.notifyListeners(thing, image.getImage());
         this.manager.diskProvider.push(thing, image);
      }
   }
}
