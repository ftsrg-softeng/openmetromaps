package de.topobyte.jeography.core.mapwindow;

import de.topobyte.jeography.core.TileOnWindow;
import java.util.Iterator;

class MapWindowIterator implements Iterator<TileOnWindow> {
   private SteppedMapWindow mapWindow;
   private int i = 0;
   private int nx = 0;
   private int ny = 0;
   private int n = 0;

   public MapWindowIterator(SteppedMapWindow mapWindow) {
      this.mapWindow = mapWindow;
      this.nx = mapWindow.getNumTilesX();
      this.ny = mapWindow.getNumTilesY();
      this.n = this.nx * this.ny;
   }

   @Override
   public boolean hasNext() {
      return this.i < this.n;
   }

   public TileOnWindow next() {
      int x = this.i % this.nx;
      int y = this.i / this.nx;
      int tx = this.mapWindow.tx + x;
      int ty = this.mapWindow.ty + y;
      int dx = -this.mapWindow.xoff + x * this.mapWindow.tileWidth;
      int dy = -this.mapWindow.yoff + y * this.mapWindow.tileHeight;
      this.i++;
      int ntiles = 1 << this.mapWindow.zoom;

      while (tx < 0) {
         tx += ntiles;
      }

      while (tx >= ntiles) {
         tx -= ntiles;
      }

      return new TileOnWindow(this.mapWindow.zoom, tx, ty, dx, dy);
   }

   @Override
   public void remove() {
      throw new UnsupportedOperationException("you can't remove tiles from a MapWindow");
   }
}
