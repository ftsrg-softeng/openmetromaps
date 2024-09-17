package de.topobyte.jts.utils.predicate;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Envelope;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Point;

public class PredicateEvaluatorJts extends AbstractPredicateEvaluator {
   private GeometryFactory factory = new GeometryFactory();
   private Geometry geometry;

   public PredicateEvaluatorJts(Geometry geometry) {
      this.geometry = geometry;
   }

   @Override
   public boolean covers(Coordinate coordinate) {
      return this.covers(this.factory.createPoint(coordinate));
   }

   @Override
   public boolean contains(Coordinate coordinate) {
      return this.contains(this.factory.createPoint(coordinate));
   }

   @Override
   public boolean covers(Point point) {
      return this.geometry.covers(point);
   }

   @Override
   public boolean contains(Point point) {
      return this.geometry.contains(point);
   }

   @Override
   public boolean covers(Envelope envelope) {
      return this.geometry.covers(this.factory.toGeometry(envelope));
   }

   @Override
   public boolean contains(Envelope envelope) {
      return this.geometry.contains(this.factory.toGeometry(envelope));
   }

   @Override
   public boolean coversNonCollection(Geometry geometry) {
      return this.geometry.covers(geometry);
   }

   @Override
   public boolean containsNonCollection(Geometry geometry) {
      return this.geometry.contains(geometry);
   }

   @Override
   public boolean intersects(Envelope envelope) {
      return this.geometry.intersects(this.factory.toGeometry(envelope));
   }

   @Override
   public boolean intersectsNonCollection(Geometry geometry) {
      return geometry.intersects(geometry);
   }
}
