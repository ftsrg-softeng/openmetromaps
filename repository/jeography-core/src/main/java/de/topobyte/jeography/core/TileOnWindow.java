package de.topobyte.jeography.core;

public class TileOnWindow extends Tile {
   private static final long serialVersionUID = 4122776265368269212L;
   public int dx;
   public int dy;

   public TileOnWindow(int zoom, int tx, int ty, int dx, int dy) {
      super(zoom, tx, ty);
      this.dx = dx;
      this.dy = dy;
   }

   public int getDX() {
      return this.dx;
   }

   public int getDY() {
      return this.dy;
   }
}
