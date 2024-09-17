package de.topobyte.jeography.tiles.source;

import de.topobyte.jeography.tiles.BufferedImageAndBytes;
import de.topobyte.jeography.tiles.UrlProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ImageProviderHttp<T> extends ImageProvider<T, BufferedImageAndBytes> {
   static final Logger logger = LoggerFactory.getLogger(ImageProvider.class);
   ImageSourceUrlPattern<T> imageSource;

   public ImageProviderHttp(UrlProvider<T> resolver, int nThreads, int nTries) {
      super(nThreads);
      this.imageSource = new ImageSourceUrlPattern<>(resolver, nTries);
   }

   public void setUserAgent(String userAgent) {
      this.imageSource.setUserAgent(userAgent);
   }

   public BufferedImageAndBytes load(T thing) {
      return this.imageSource.loadImage(thing);
   }
}
