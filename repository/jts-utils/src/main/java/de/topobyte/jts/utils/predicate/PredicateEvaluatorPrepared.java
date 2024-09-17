package de.topobyte.jts.utils.predicate;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Envelope;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.geom.Polygonal;
import com.vividsolutions.jts.geom.prep.PreparedPolygon;

public class PredicateEvaluatorPrepared extends AbstractPredicateEvaluator {
   private GeometryFactory factory = new GeometryFactory();
   private PreparedPolygon preparedPolygon;

   public PredicateEvaluatorPrepared(Geometry geometry) {
      this.preparedPolygon = new PreparedPolygon((Polygonal)geometry);
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
      return this.preparedPolygon.covers(point);
   }

   @Override
   public boolean contains(Point point) {
      return this.preparedPolygon.contains(point);
   }

   @Override
   public boolean covers(Envelope envelope) {
      return this.preparedPolygon.covers(this.factory.toGeometry(envelope));
   }

   @Override
   public boolean contains(Envelope envelope) {
      return this.preparedPolygon.contains(this.factory.toGeometry(envelope));
   }

   @Override
   public boolean coversNonCollection(Geometry geometry) {
      return this.preparedPolygon.covers(geometry);
   }

   @Override
   public boolean containsNonCollection(Geometry geometry) {
      return this.preparedPolygon.contains(geometry);
   }

   @Override
   public boolean intersects(Envelope envelope) {
      return this.preparedPolygon.intersects(this.factory.toGeometry(envelope));
   }

   @Override
   public boolean intersectsNonCollection(Geometry geometry) {
      return this.preparedPolygon.intersects(geometry);
   }
}
