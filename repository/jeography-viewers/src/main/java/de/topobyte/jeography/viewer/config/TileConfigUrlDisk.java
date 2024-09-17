package de.topobyte.jeography.viewer.config;

import de.topobyte.jeography.core.Tile;
import de.topobyte.jeography.tiles.TileUrlAndCachePathProvider;
import de.topobyte.jeography.tiles.manager.ImageManager;
import de.topobyte.jeography.tiles.manager.PriorityImageManagerHttpDisk;
import de.topobyte.jeography.viewer.core.PaintListener;
import java.awt.image.BufferedImage;
import java.io.File;

public class TileConfigUrlDisk implements TileConfig {
   private int id;
   private String name;
   private String url;
   private String path;
   private String userAgent;

   public TileConfigUrlDisk(int id, String name, String url, String path) {
      this.id = id;
      this.name = name;
      this.url = url;
      this.path = path;
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

   public String getPath() {
      return this.path;
   }

   public String getUserAgent() {
      return this.userAgent;
   }

   @Override
   public String toString() {
      return "name: " + this.getName() + ", url: " + this.getUrl() + ", path: " + this.getPath();
   }

   @Override
   public boolean equals(Object o) {
      if (o instanceof TileConfigUrlDisk) {
         TileConfigUrlDisk other = (TileConfigUrlDisk)o;
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
      File pathDir = new File(this.getPath());
      if (!pathDir.exists()) {
         pathDir.mkdirs();
      }

      if (!pathDir.exists() && pathDir.isDirectory()) {
         return null;
      } else {
         TileUrlAndCachePathProvider tileResoluterBase = new TileUrlAndCachePathProvider(this.getPath(), this.getUrl());
         PriorityImageManagerHttpDisk<Tile> manager = new PriorityImageManagerHttpDisk(4, 150, tileResoluterBase);
         if (this.userAgent != null) {
            manager.setUserAgent(this.userAgent);
         }

         return manager;
      }
   }

   @Override
   public PaintListener createGlobalManager() {
      return null;
   }
}
