package de.topobyte.lightgeom.curves.spline;

import de.topobyte.lightgeom.lina.Point;
import de.topobyte.lightgeom.lina.Vector2;

public class SplineUtil {
   public static CubicSpline spline(Point p1, Point p2, Vector2 d1, Vector2 d2, double f, boolean normalized) {
      Vector2 d = new Vector2(p1, p2);
      double l = d.length() * f;
      Point cp1;
      if (d1 == null) {
         cp1 = p1;
      } else {
         if (!normalized) {
            d1.normalize();
         }

         Vector2 c1 = new Vector2(p1);
         d1.mult(l);
         c1.add(d1);
         cp1 = new Point(c1.getX(), c1.getY());
      }

      Point cp2;
      if (d2 == null) {
         cp2 = p2;
      } else {
         if (!normalized) {
            d2.normalize();
         }

         Vector2 c2 = new Vector2(p2);
         d2.mult(l);
         c2.add(d2);
         cp2 = new Point(c2.getX(), c2.getY());
      }

      return new CubicSplineB(p1, cp1, cp2, p2);
   }

   public static CubicSpline spline(double p1x, double p1y, double p2x, double p2y, Vector2 d1, Vector2 d2, double f, boolean normalized) {
      CubicSplineB spline = new CubicSplineB(0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0);
      spline(spline, p1x, p1y, p2x, p2y, d1, d2, f, normalized);
      return spline;
   }

   public static void spline(CubicSpline spline, double p1x, double p1y, double p2x, double p2y, Vector2 d1, Vector2 d2, double f, boolean normalized) {
      double dx = p2x - p1x;
      double dy = p2y - p1y;
      double dl = Math.sqrt(dx * dx + dy * dy);
      double l = dl * f;
      double cp1x;
      double cp1y;
      if (d1 == null) {
         cp1x = p1x;
         cp1y = p1y;
      } else {
         if (!normalized) {
            d1.normalize();
         }

         d1.mult(l);
         cp1x = p1x + d1.getX();
         cp1y = p1y + d1.getY();
      }

      double cp2x;
      double cp2y;
      if (d2 == null) {
         cp2x = p2x;
         cp2y = p2y;
      } else {
         if (!normalized) {
            d2.normalize();
         }

         d2.mult(l);
         cp2x = p2x + d2.getX();
         cp2y = p2y + d2.getY();
      }

      spline.set(p1x, p1y, cp1x, cp1y, cp2x, cp2y, p2x, p2y);
   }
}
