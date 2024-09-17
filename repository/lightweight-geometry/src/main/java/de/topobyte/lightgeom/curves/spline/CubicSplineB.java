package de.topobyte.lightgeom.curves.spline;

import de.topobyte.lightgeom.lina.Point;

public class CubicSplineB implements CubicSpline {
   private double p1x;
   private double p1y;
   private double c1x;
   private double c1y;
   private double c2x;
   private double c2y;
   private double p2x;
   private double p2y;

   public CubicSplineB(Point p1, Point c1, Point c2, Point p2) {
      this.p1x = p1.x;
      this.p1y = p1.y;
      this.c1x = c1.x;
      this.c1y = c1.y;
      this.c2x = c2.x;
      this.c2y = c2.y;
      this.p2x = p2.x;
      this.p2y = p2.y;
   }

   public CubicSplineB(double p1x, double p1y, double c1x, double c1y, double c2x, double c2y, double p2x, double p2y) {
      this.p1x = p1x;
      this.p1y = p1y;
      this.c1x = c1x;
      this.c1y = c1y;
      this.c2x = c2x;
      this.c2y = c2y;
      this.p2x = p2x;
      this.p2y = p2y;
   }

   @Override
   public void set(double p1x, double p1y, double c1x, double c1y, double c2x, double c2y, double p2x, double p2y) {
      this.p1x = p1x;
      this.p1y = p1y;
      this.c1x = c1x;
      this.c1y = c1y;
      this.c2x = c2x;
      this.c2y = c2y;
      this.p2x = p2x;
      this.p2y = p2y;
   }

   @Override
   public Point getP1() {
      return new Point(this.p1x, this.p1y);
   }

   @Override
   public Point getC1() {
      return new Point(this.c1x, this.c1y);
   }

   @Override
   public Point getC2() {
      return new Point(this.c2x, this.c2y);
   }

   @Override
   public Point getP2() {
      return new Point(this.p2x, this.p2y);
   }

   @Override
   public double getP1X() {
      return this.p1x;
   }

   @Override
   public double getP1Y() {
      return this.p1y;
   }

   @Override
   public double getP2X() {
      return this.p2x;
   }

   @Override
   public double getP2Y() {
      return this.p2y;
   }

   @Override
   public double getC1X() {
      return this.c1x;
   }

   @Override
   public double getC1Y() {
      return this.c1y;
   }

   @Override
   public double getC2X() {
      return this.c2x;
   }

   @Override
   public double getC2Y() {
      return this.c2y;
   }
}
