package de.topobyte.lightgeom.curves.spline;

import de.topobyte.lightgeom.lina.Point;

public class LineA implements Line {
   private Point p1;
   private Point p2;

   public LineA(Point p1, Point p2) {
      this.p1 = p1;
      this.p2 = p2;
   }

   @Override
   public void set(double p1x, double p1y, double p2x, double p2y) {
      this.p1.set(p1x, p1y);
      this.p2.set(p2x, p2y);
   }

   @Override
   public Point getP1() {
      return this.p1;
   }

   @Override
   public Point getP2() {
      return this.p2;
   }

   @Override
   public double getP1X() {
      return this.p1.x;
   }

   @Override
   public double getP1Y() {
      return this.p1.y;
   }

   @Override
   public double getP2X() {
      return this.p2.x;
   }

   @Override
   public double getP2Y() {
      return this.p2.y;
   }
}
