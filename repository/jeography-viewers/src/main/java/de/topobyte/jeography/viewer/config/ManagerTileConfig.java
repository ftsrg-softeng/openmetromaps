package de.topobyte.jeography.viewer.config;

import de.topobyte.jeography.core.Tile;
import de.topobyte.jeography.tiles.manager.ImageManager;
import de.topobyte.jeography.viewer.core.PaintListener;
import java.awt.image.BufferedImage;

public class ManagerTileConfig implements TileConfig {
   private final int id;
   private final String name;
   private final ImageManager<Tile, BufferedImage> manager;

   public ManagerTileConfig(int id, String name, ImageManager<Tile, BufferedImage> manager) {
      this.id = id;
      this.name = name;
      this.manager = manager;
   }

   @Override
   public int getId() {
      return this.id;
   }

   @Override
   public String getName() {
      return this.name;
   }

   @Override
   public ImageManager<Tile, BufferedImage> createImageManager() {
      return this.manager;
   }

   @Override
   public PaintListener createGlobalManager() {
      return null;
   }
}
