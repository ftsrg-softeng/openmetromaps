package de.topobyte.jeography.core.mapwindow;

import de.topobyte.jeography.core.TileOnWindow;

public interface TileMapWindow extends MapWindow, Iterable<TileOnWindow> {
   int getZoomLevel();

   double getCenterX();

   double getCenterY();

   double getPositionX(int var1);

   double getPositionY(int var1);

   boolean setTileSize(int var1);

   int getTileWidth();

   int getTileHeight();

   int getNumTilesX();

   int getNumTilesY();

   int minimumCacheSize();

   void addTileSizeListener(MapWindowTileSizeListener var1);
}
