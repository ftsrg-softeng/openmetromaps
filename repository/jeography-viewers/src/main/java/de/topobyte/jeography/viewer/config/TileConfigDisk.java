package de.topobyte.jeography.viewer.config;

import de.topobyte.jeography.core.Tile;
import de.topobyte.jeography.tiles.TileCachePathProvider;
import de.topobyte.jeography.tiles.manager.ImageManager;
import de.topobyte.jeography.tiles.manager.ImageManagerDisk;
import de.topobyte.jeography.viewer.core.PaintListener;
import java.awt.image.BufferedImage;
import java.io.File;

public class TileConfigDisk implements TileConfig {
   private int id;
   private String name;
   private String path;

   public TileConfigDisk(int id, String name, String path) {
      this.id = id;
      this.name = name;
      this.path = path;
   }

   @Override
   public int getId() {
      return this.id;
   }

   @Override
   public String getName() {
      return this.name;
   }

   public String getPath() {
      return this.path;
   }

   @Override
   public String toString() {
      return "name: " + this.getName() + ", path: " + this.getPath();
   }

   @Override
   public boolean equals(Object o) {
      if (o instanceof TileConfigDisk) {
         TileConfigDisk other = (TileConfigDisk)o;
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
         TileCachePathProvider tileResoluterBase = new TileCachePathProvider(this.getPath());
         return new ImageManagerDisk(tileResoluterBase);
      }
   }

   @Override
   public PaintListener createGlobalManager() {
      return null;
   }
}
