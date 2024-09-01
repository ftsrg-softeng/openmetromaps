package de.topobyte.jts.utils;

import com.vividsolutions.jts.geom.LineSegment;

public class Segments {
   public static boolean connected(LineSegment a, LineSegment b) {
      return a.p0.equals(b.p0) || a.p0.equals(b.p1) || a.p1.equals(b.p0) || a.p1.equals(b.p1);
   }
}
