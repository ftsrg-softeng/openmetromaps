package de.topobyte.jsijts;

import com.infomatiq.jsi.Rectangle;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Envelope;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.LineSegment;
import com.vividsolutions.jts.geom.LinearRing;
import com.vividsolutions.jts.geom.Polygon;
import de.topobyte.jts.utils.JtsHelper;
import java.util.ArrayList;
import java.util.List;

public class JsiAndJts {
   public static Rectangle toRectangle(Envelope envelope) {
      return new Rectangle((float)envelope.getMinX(), (float)envelope.getMinY(), (float)envelope.getMaxX(), (float)envelope.getMaxY());
   }

   public static Envelope toEnvelope(Rectangle rectangle) {
      return new Envelope((double)rectangle.minX, (double)rectangle.maxX, (double)rectangle.minY, (double)rectangle.maxY);
   }

   public static Rectangle toRectangle(Geometry geometry) {
      Envelope envelope = geometry.getEnvelopeInternal();
      return new Rectangle((float)envelope.getMinX(), (float)envelope.getMinY(), (float)envelope.getMaxX(), (float)envelope.getMaxY());
   }

   public static Rectangle toRectangle(LineSegment segment) {
      Coordinate p = segment.p0;
      Coordinate q = segment.p1;
      double minX = p.x < q.x ? p.x : q.x;
      double maxX = p.x > q.x ? p.x : q.x;
      double minY = p.y < q.y ? p.y : q.y;
      double maxY = p.y > q.y ? p.y : q.y;
      return new Rectangle((float)minX, (float)minY, (float)maxX, (float)maxY);
   }

   public static Polygon toGeometry(Rectangle rectangle) {
      List<Double> xs = new ArrayList<>(4);
      List<Double> ys = new ArrayList<>(4);
      xs.add((double)rectangle.minX);
      xs.add((double)rectangle.maxX);
      xs.add((double)rectangle.maxX);
      xs.add((double)rectangle.minX);
      ys.add((double)rectangle.minY);
      ys.add((double)rectangle.minY);
      ys.add((double)rectangle.maxY);
      ys.add((double)rectangle.maxY);
      LinearRing ring = JtsHelper.toLinearRing(xs, ys, false);
      return ring.getFactory().createPolygon(ring, null);
   }
}
