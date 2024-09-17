package de.topobyte.jts.utils;

import com.slimjars.dist.gnu.trove.list.TDoubleList;
import com.vividsolutions.jts.geom.CoordinateSequence;
import com.vividsolutions.jts.geom.CoordinateSequenceFactory;
import com.vividsolutions.jts.geom.Envelope;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryCollection;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.LineString;
import com.vividsolutions.jts.geom.LinearRing;
import com.vividsolutions.jts.geom.Polygon;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class JtsHelper {
   public static LineString toLineString(List<Double> xs, List<Double> ys) {
      GeometryFactory factory = new GeometryFactory();
      CoordinateSequenceFactory csf = factory.getCoordinateSequenceFactory();
      int len = xs.size();
      CoordinateSequence coords = csf.create(len, 2);

      for (int i = 0; i < xs.size(); i++) {
         coords.setOrdinate(i, 0, xs.get(i));
         coords.setOrdinate(i, 1, ys.get(i));
      }

      return factory.createLineString(coords);
   }

   public static LineString toLineString(TDoubleList xs, TDoubleList ys) {
      GeometryFactory factory = new GeometryFactory();
      CoordinateSequenceFactory csf = factory.getCoordinateSequenceFactory();
      int len = xs.size();
      CoordinateSequence coords = csf.create(len, 2);

      for (int i = 0; i < xs.size(); i++) {
         coords.setOrdinate(i, 0, xs.get(i));
         coords.setOrdinate(i, 1, ys.get(i));
      }

      return factory.createLineString(coords);
   }

   public static LinearRing toLinearRing(List<Double> xs, List<Double> ys, boolean doublePoint) {
      GeometryFactory factory = new GeometryFactory();
      CoordinateSequenceFactory csf = factory.getCoordinateSequenceFactory();
      int len = xs.size();
      if (!doublePoint) {
         len++;
      }

      if (len > 0 && len < 4) {
         System.out.println("skipping");
         return null;
      } else {
         CoordinateSequence coords = csf.create(len, 2);

         for (int i = 0; i < xs.size(); i++) {
            coords.setOrdinate(i, 0, xs.get(i));
            coords.setOrdinate(i, 1, ys.get(i));
         }

         if (!doublePoint) {
            coords.setOrdinate(len - 1, 0, xs.get(0));
            coords.setOrdinate(len - 1, 1, ys.get(0));
         }

         return factory.createLinearRing(coords);
      }
   }

   public static LinearRing toLinearRing(TDoubleList xs, TDoubleList ys, boolean doublePoint) {
      GeometryFactory factory = new GeometryFactory();
      CoordinateSequenceFactory csf = factory.getCoordinateSequenceFactory();
      int len = xs.size();
      if (!doublePoint) {
         len++;
      }

      if (len > 0 && len < 4) {
         System.out.println("skipping");
         return null;
      } else {
         CoordinateSequence coords = csf.create(len, 2);

         for (int i = 0; i < xs.size(); i++) {
            coords.setOrdinate(i, 0, xs.get(i));
            coords.setOrdinate(i, 1, ys.get(i));
         }

         if (!doublePoint) {
            coords.setOrdinate(len - 1, 0, xs.get(0));
            coords.setOrdinate(len - 1, 1, ys.get(0));
         }

         return factory.createLinearRing(coords);
      }
   }

   public static Polygon toGeometry(Envelope envelope) {
      List<Double> xs = new ArrayList<>(4);
      List<Double> ys = new ArrayList<>(4);
      xs.add(envelope.getMinX());
      xs.add(envelope.getMaxX());
      xs.add(envelope.getMaxX());
      xs.add(envelope.getMinX());
      ys.add(envelope.getMinY());
      ys.add(envelope.getMinY());
      ys.add(envelope.getMaxY());
      ys.add(envelope.getMaxY());
      LinearRing ring = toLinearRing(xs, ys, false);
      return ring.getFactory().createPolygon(ring, null);
   }

   public static Envelope expandBy(Envelope envelope, double ratio) {
      Envelope copy = new Envelope(envelope);
      copy.expandBy(copy.getWidth() * ratio, copy.getHeight() * ratio);
      return copy;
   }

   public static Envelope getEnvelope(Collection<Geometry> geometries) {
      Envelope bboxEnvelope = new Envelope();

      for (Geometry geom : geometries) {
         Envelope envelope = geom.getEnvelopeInternal();
         bboxEnvelope.expandToInclude(envelope);
      }

      return bboxEnvelope;
   }

   public static GeometryCollection collection(List<Geometry> geometries) {
      return new GeometryFactory().createGeometryCollection(geometries.toArray(new Geometry[geometries.size()]));
   }
}
