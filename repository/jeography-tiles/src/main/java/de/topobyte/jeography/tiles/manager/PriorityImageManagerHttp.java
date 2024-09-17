package de.topobyte.jeography.tiles.manager;

import de.topobyte.jeography.tiles.UrlProvider;
import de.topobyte.jeography.tiles.source.ImageSourceUrlPattern;
import de.topobyte.jeography.tiles.source.UnwrappingImageSource;
import java.awt.image.BufferedImage;

public class PriorityImageManagerHttp<T> extends ImageManagerSourceRam<T, BufferedImage> {
   boolean online = true;
   private ImageSourceUrlPattern<T> sourceUrl;

   public <X extends UrlProvider<T>> PriorityImageManagerHttp(int nThreads, int cacheSize, X resolver) {
      super(nThreads, cacheSize, null);
      this.sourceUrl = new ImageSourceUrlPattern<>(resolver, 3);
      this.imageSource = new UnwrappingImageSource(this.sourceUrl);
   }

   public void setNetworkState(boolean state) {
      this.online = state;
      this.sourceUrl.setOnline(state);
   }

   public boolean getNetworkState() {
      return this.online;
   }

   public void setUserAgent(String userAgent) {
      this.sourceUrl.setUserAgent(userAgent);
   }
}
