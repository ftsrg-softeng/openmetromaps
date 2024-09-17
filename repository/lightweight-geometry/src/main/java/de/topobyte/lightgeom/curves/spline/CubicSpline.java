package de.topobyte.lightgeom.curves.spline;

import de.topobyte.lightgeom.lina.Point;

public interface CubicSpline {
   Point getP1();

   Point getC1();

   Point getC2();

   Point getP2();

   double getP1X();

   double getP1Y();

   double getP2X();

   double getP2Y();

   double getC1X();

   double getC1Y();

   double getC2X();

   double getC2Y();

   void set(double var1, double var3, double var5, double var7, double var9, double var11, double var13, double var15);
}
