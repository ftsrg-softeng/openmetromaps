package de.topobyte.lightgeom.curves.spline;

import de.topobyte.lightgeom.lina.Point;

public class QuadraticSplineA implements QuadraticSpline {
   private Point p1;
   private Point c;
   private Point p2;

   public QuadraticSplineA(Point p1, Point c, Point p2) {
      this.p1 = p1;
      this.c = c;
      this.p2 = p2;
   }

   @Override
   public void set(double p1x, double p1y, double cx, double cy, double p2x, double p2y) {
      this.p1.set(p1x, p1y);
      this.p2.set(p2x, p2y);
      this.c.set(cx, cy);
   }

   @Override
   public Point getP1() {
      return this.p1;
   }

   @Override
   public Point getC() {
      return this.c;
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

   @Override
   public double getCX() {
      return this.c.x;
   }

   @Override
   public double getCY() {
      return this.c.y;
   }
}
