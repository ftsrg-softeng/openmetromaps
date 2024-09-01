package de.topobyte.lightgeom.curves.spline;

import de.topobyte.lightgeom.lina.Point;
import de.topobyte.lightgeom.lina.Vector2;
import java.util.ArrayList;
import java.util.List;

public class SplineString {
   private List<CubicSpline> splines = new ArrayList<>();

   public SplineString(List<Point> Points, double f) {
      Point p1 = Points.get(0);
      Point p2 = Points.get(1);
      Point p3 = Points.get(2);
      Vector2 d31 = new Vector2(p1);
      d31.sub(new Vector2(p3));
      d31.normalize();
      CubicSpline spline = SplineUtil.spline(p1, p2, null, d31, f, true);
      this.splines.add(spline);

      for (int i = 1; i < Points.size() - 2; i++) {
         p2 = Points.get(i - 1);
         p3 = Points.get(i);
         Point p2x = Points.get(i + 1);
         Point p3x = Points.get(i + 2);
         Vector2 d02 = new Vector2(p2x);
         d02.sub(new Vector2(p2));
         d02.normalize();
         Vector2 d31x = new Vector2(p3);
         d31x.sub(new Vector2(p3x));
         d31x.normalize();
         CubicSpline splinex = SplineUtil.spline(p3, p2x, d02, d31x, f, true);
         this.splines.add(splinex);
      }

      p1 = Points.get(Points.size() - 3);
      p2 = Points.get(Points.size() - 2);
      p3 = Points.get(Points.size() - 1);
      d31 = new Vector2(p3);
      d31.sub(new Vector2(p1));
      d31.normalize();
      spline = SplineUtil.spline(p2, p3, d31, null, f, true);
      this.splines.add(spline);
   }

   public List<CubicSpline> getSplines() {
      return this.splines;
   }
}
