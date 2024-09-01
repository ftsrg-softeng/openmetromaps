package de.topobyte.lightgeom.curves.spline.awt;

import de.topobyte.lightgeom.curves.spline.CubicSpline;
import de.topobyte.lightgeom.curves.spline.Line;
import de.topobyte.lightgeom.curves.spline.QuadraticSpline;
import de.topobyte.lightgeom.lina.Point;
import java.awt.geom.CubicCurve2D;
import java.awt.geom.Line2D;
import java.awt.geom.QuadCurve2D;
import java.awt.geom.Line2D.Double;

public class LightGeomAwt {
   public static Line2D connect(Point c1, Point c2) {
      return new Double(c1.x, c1.y, c2.x, c2.y);
   }

   public static Line2D convert(Line line) {
      Point p1 = line.getP1();
      Point p2 = line.getP2();
      return new Double(p1.x, p1.y, p2.x, p2.y);
   }

   public static QuadCurve2D convert(QuadraticSpline spline) {
      Point p1 = spline.getP1();
      Point p2 = spline.getP2();
      Point c = spline.getC();
      return new java.awt.geom.QuadCurve2D.Double(p1.x, p1.y, c.x, c.y, p2.x, p2.y);
   }

   public static CubicCurve2D convert(CubicSpline spline) {
      Point p1 = spline.getP1();
      Point p2 = spline.getP2();
      Point c1 = spline.getC1();
      Point c2 = spline.getC2();
      return new java.awt.geom.CubicCurve2D.Double(p1.x, p1.y, c1.x, c1.y, c2.x, c2.y, p2.x, p2.y);
   }
}
