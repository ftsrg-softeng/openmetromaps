package de.topobyte.jeography.viewer.config;

import de.topobyte.jeography.core.Tile;
import de.topobyte.jeography.tiles.manager.ImageManager;
import de.topobyte.jeography.viewer.core.PaintListener;
import java.awt.image.BufferedImage;

public interface TileConfig {
   int getId();

   String getName();

   ImageManager<Tile, BufferedImage> createImageManager();

   PaintListener createGlobalManager();
}
