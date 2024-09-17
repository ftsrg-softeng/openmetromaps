package de.topobyte.osm4j.geometry;

import com.vividsolutions.jts.geom.LineString;
import com.vividsolutions.jts.geom.MultiPolygon;
import com.vividsolutions.jts.geom.Point;
import java.util.Map;

public interface OsmGeometryHandler {
   void processNode(Point var1, Map<String, String> var2);

   void processWayString(LineString var1, Map<String, String> var2);

   void processMultipolygon(MultiPolygon var1, Map<String, String> var2, Point var3);
}
