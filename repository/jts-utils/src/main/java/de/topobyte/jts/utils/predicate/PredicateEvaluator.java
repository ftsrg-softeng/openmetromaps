package de.topobyte.jts.utils.predicate;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Envelope;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.Point;

public interface PredicateEvaluator {
   boolean covers(Coordinate var1);

   boolean contains(Coordinate var1);

   boolean covers(Point var1);

   boolean contains(Point var1);

   boolean covers(Envelope var1);

   boolean contains(Envelope var1);

   boolean covers(Geometry var1);

   boolean contains(Geometry var1);

   boolean intersects(Geometry var1);

   boolean intersects(Envelope var1);
}
