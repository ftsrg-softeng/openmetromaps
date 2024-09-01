package de.topobyte.lightgeom.curves.spline;

import de.topobyte.lightgeom.lina.Point;

public class QuadraticSplineB implements QuadraticSpline {
   private double p1x;
   private double p1y;
   private double cx;
   private double cy;
   private double p2x;
   private double p2y;

   public QuadraticSplineB(Point p1, Point c, Point p2) {
      this.p1x = p1.x;
      this.p1y = p1.y;
      this.cx = c.x;
      this.cy = c.y;
      this.p2x = p2.x;
      this.p2y = p2.y;
   }

   public QuadraticSplineB(double p1x, double p1y, double cx, double cy, double p2x, double p2y) {
      this.p1x = p1x;
      this.p1y = p1y;
      this.cx = cx;
      this.cy = cy;
      this.p2x = p2x;
      this.p2y = p2y;
   }

   @Override
   public void set(double p1x, double p1y, double cx, double cy, double p2x, double p2y) {
      this.p1x = p1x;
      this.p1y = p1y;
      this.cx = cx;
      this.cy = cy;
      this.p2x = p2x;
      this.p2y = p2y;
   }

   @Override
   public Point getP1() {
      return new Point(this.p1x, this.p1y);
   }

   @Override
   public Point getC() {
      return new Point(this.cx, this.cy);
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
   public double getCX() {
      return this.cx;
   }

   @Override
   public double getCY() {
      return this.cy;
   }
}
