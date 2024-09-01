package de.topobyte.jeography.tiles.manager;

import de.topobyte.jeography.tiles.CachePathProvider;
import de.topobyte.jeography.tiles.UrlProvider;
import de.topobyte.jeography.tiles.source.ImageSource;
import de.topobyte.jeography.tiles.source.ImageSourcePathPattern;
import de.topobyte.jeography.tiles.source.ImageSourceUrlPattern;
import de.topobyte.jeography.tiles.source.MultiLevelImageSource;
import de.topobyte.jeography.tiles.source.UnwrappingImageSourceWithFileCache;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class PriorityImageManagerHttpDisk<T> extends ImageManagerSourceRam<T, BufferedImage> {
   boolean online = true;
   private ImageSourcePathPattern<T> sourceDisk;
   private ImageSourceUrlPattern<T> sourceUrl;

   public <X extends CachePathProvider<T> & UrlProvider<T>> PriorityImageManagerHttpDisk(int nThreads, int cacheSize, X resolver) {
      super(nThreads, cacheSize, null);
      this.sourceDisk = new ImageSourcePathPattern<>(resolver);
      this.sourceUrl = new ImageSourceUrlPattern<>(resolver, 3);
      UnwrappingImageSourceWithFileCache<T> sourceUrlWithCache = new UnwrappingImageSourceWithFileCache<>(this.sourceUrl, resolver);
      List<ImageSource<T, BufferedImage>> sources = new ArrayList<>();
      sources.add(this.sourceDisk);
      sources.add(sourceUrlWithCache);
      this.imageSource = new MultiLevelImageSource<>(sources);
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
