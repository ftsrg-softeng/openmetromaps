package de.topobyte.jeography.tiles;

import de.topobyte.jeography.core.Tile;

public class TileCachePathProvider implements CachePathProvider<Tile> {
   private String cacheFileTemplate;

   public TileCachePathProvider(String cacheDir) {
      this.cacheFileTemplate = cacheDir + "/%d_%d_%d.png";
   }

   public String getCacheFile(Tile tile) {
      return String.format(this.cacheFileTemplate, tile.getZoom(), tile.getTx(), tile.getTy());
   }
}
