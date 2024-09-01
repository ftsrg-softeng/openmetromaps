package de.topobyte.lightgeom.curves.spline;

import de.topobyte.lightgeom.lina.Point;

public class CubicSplineA implements CubicSpline {
   private Point p1;
   private Point c1;
   private Point c2;
   private Point p2;

   public CubicSplineA(Point p1, Point c1, Point c2, Point p2) {
      this.p1 = p1;
      this.c1 = c1;
      this.c2 = c2;
      this.p2 = p2;
   }

   @Override
   public void set(double p1x, double p1y, double c1x, double c1y, double c2x, double c2y, double p2x, double p2y) {
      this.p1.set(p1x, p1y);
      this.p2.set(p2x, p2y);
      this.c1.set(c1x, c1y);
      this.c2.set(c2x, c2y);
   }

   @Override
   public Point getP1() {
      return this.p1;
   }

   @Override
   public Point getC1() {
      return this.c1;
   }

   @Override
   public Point getC2() {
      return this.c2;
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
   public double getC1X() {
      return this.c1.x;
   }

   @Override
   public double getC1Y() {
      return this.c1.y;
   }

   @Override
   public double getC2X() {
      return this.c2.x;
   }

   @Override
   public double getC2Y() {
      return this.c2.y;
   }
}
