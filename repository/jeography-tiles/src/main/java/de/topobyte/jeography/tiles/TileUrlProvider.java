package de.topobyte.jeography.tiles;

import de.topobyte.jeography.core.Tile;

public class TileUrlProvider implements UrlProvider<Tile> {
   private String urlTemplate;

   public TileUrlProvider(String urlTemplate) {
      this.urlTemplate = urlTemplate;
   }

   public String getUrl(Tile tile) {
      return String.format(this.urlTemplate, tile.getZoom(), tile.getTx(), tile.getTy());
   }
}
