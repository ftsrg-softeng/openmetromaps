package de.topobyte.jeography.tiles;

import de.topobyte.jeography.core.Tile;

public class TileUrlAndCachePathProvider implements CachePathProvider<Tile>, UrlProvider<Tile> {
   private String cacheFileTemplate;
   private String urlTemplate;

   public TileUrlAndCachePathProvider(String cacheDir, String urlTemplate) {
      this.cacheFileTemplate = cacheDir + "/%d_%d_%d.png";
      this.urlTemplate = urlTemplate;
   }

   public String getCacheFile(Tile tile) {
      return String.format(this.cacheFileTemplate, tile.getZoom(), tile.getTx(), tile.getTy());
   }

   public String getUrl(Tile tile) {
      return String.format(this.urlTemplate, tile.getZoom(), tile.getTx(), tile.getTy());
   }
}
