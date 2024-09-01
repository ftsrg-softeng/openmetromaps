package de.topobyte.jeography.viewer.config;

import de.topobyte.jeography.core.Tile;
import de.topobyte.jeography.tiles.TileUrlProvider;
import de.topobyte.jeography.tiles.manager.ImageManager;
import de.topobyte.jeography.tiles.manager.PriorityImageManagerHttp;
import de.topobyte.jeography.viewer.core.PaintListener;
import java.awt.image.BufferedImage;

public class TileConfigUrl implements TileConfig {
   private int id;
   private String name;
   private String url;
   private String userAgent;

   public TileConfigUrl(int id, String name, String url) {
      this.id = id;
      this.name = name;
      this.url = url;
   }

   public void setUserAgent(String userAgent) {
      this.userAgent = userAgent;
   }

   @Override
   public int getId() {
      return this.id;
   }

   @Override
   public String getName() {
      return this.name;
   }

   public String getUrl() {
      return this.url;
   }

   public String getUserAgent() {
      return this.userAgent;
   }

   @Override
   public String toString() {
      return "name: " + this.getName() + ", url: " + this.getUrl();
   }

   @Override
   public boolean equals(Object o) {
      if (o instanceof TileConfigUrl) {
         TileConfigUrl other = (TileConfigUrl)o;
         return other.id == this.id;
      } else {
         return false;
      }
   }

   @Override
   public int hashCode() {
      return this.id;
   }

   @Override
   public ImageManager<Tile, BufferedImage> createImageManager() {
      TileUrlProvider tileResoluterBase = new TileUrlProvider(this.getUrl());
      PriorityImageManagerHttp<Tile> manager = new PriorityImageManagerHttp(4, 150, tileResoluterBase);
      if (this.userAgent != null) {
         manager.setUserAgent(this.userAgent);
      }

      return manager;
   }

   @Override
   public PaintListener createGlobalManager() {
      return null;
   }
}
