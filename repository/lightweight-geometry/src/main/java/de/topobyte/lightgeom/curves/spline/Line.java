package de.topobyte.lightgeom.curves.spline;

import de.topobyte.lightgeom.lina.Point;

public interface Line {
   Point getP1();

   Point getP2();

   double getP1X();

   double getP1Y();

   double getP2X();

   double getP2Y();

   void set(double var1, double var3, double var5, double var7);
}
