package de.topobyte.jeography.core;

import java.io.Serializable;

public class Tile implements Serializable {
   private static final long serialVersionUID = -2268865981792560407L;
   public static int SIZE = 256;
   int zoom;
   int tx;
   int ty;

   public Tile(int zoom, int tx, int ty) {
      this.zoom = zoom;
      this.tx = tx;
      this.ty = ty;
   }

   @Override
   public int hashCode() {
      return this.zoom * this.tx * this.ty;
   }

   @Override
   public boolean equals(Object o) {
      if (!(o instanceof Tile)) {
         return false;
      } else {
         Tile other = (Tile)o;
         return other.zoom == this.zoom && other.tx == this.tx && other.ty == this.ty;
      }
   }

   public int getZoom() {
      return this.zoom;
   }

   public int getTx() {
      return this.tx;
   }

   public int getTy() {
      return this.ty;
   }
}
