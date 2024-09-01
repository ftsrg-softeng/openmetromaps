package de.topobyte.viewports.scrolling;

import de.topobyte.viewports.geometry.Coordinate;

public class DragInfo {
   private Coordinate start;
   private Coordinate last;
   private Coordinate current;

   public DragInfo(double x, double y) {
      this.start = new Coordinate(x, y);
      this.last = this.start;
      this.current = this.start;
   }

   public void update(double x, double y) {
      this.last = this.current;
      this.current = new Coordinate(x, y);
   }

   public Coordinate getDeltaToLast() {
      return this.getDeltaTo(this.last);
   }

   public Coordinate getDeltaToStart() {
      return this.getDeltaTo(this.start);
   }

   private Coordinate getDeltaTo(Coordinate other) {
      double dx = this.current.getX() - other.getX();
      double dy = this.current.getY() - other.getY();
      return new Coordinate(dx, dy);
   }
}
