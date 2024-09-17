package de.topobyte.viewports.geometry;

public class Coordinate {
   double x;
   double y;

   public Coordinate(double x, double y) {
      this.x = x;
      this.y = y;
   }

   public Coordinate(Coordinate c) {
      this.x = c.x;
      this.y = c.y;
   }

   public double getX() {
      return this.x;
   }

   public double getY() {
      return this.y;
   }

   public void setX(double x) {
      this.x = x;
   }

   public void setY(double y) {
      this.y = y;
   }

   public double distance(Coordinate c) {
      double a = this.x - c.x;
      double b = this.y - c.y;
      return Math.sqrt(a * a + b * b);
   }

   @Override
   public String toString() {
      return this.x + ", " + this.y;
   }

   @Override
   public int hashCode() {
      long bitsX = Double.doubleToLongBits(this.x);
      long bitsY = Double.doubleToLongBits(this.x);
      long bits = bitsX + bitsY;
      return (int)(bits ^ bits >>> 32);
   }

   @Override
   public boolean equals(Object other) {
      if (!(other instanceof Coordinate)) {
         return false;
      } else {
         Coordinate o = (Coordinate)other;
         return o.getX() == this.getX() && o.getY() == this.getY();
      }
   }
}
