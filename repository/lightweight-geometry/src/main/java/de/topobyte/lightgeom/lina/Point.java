package de.topobyte.lightgeom.lina;

public class Point {
   public double x;
   public double y;

   public Point(double x, double y) {
      this.x = x;
      this.y = y;
   }

   public Point set(double x, double y) {
      this.x = x;
      this.y = y;
      return this;
   }

   public double getX() {
      return this.x;
   }

   public double getY() {
      return this.y;
   }

   public Point setX(double x) {
      this.x = x;
      return this;
   }

   public Point setY(double y) {
      this.y = y;
      return this;
   }
}
