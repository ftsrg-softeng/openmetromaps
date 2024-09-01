package de.topobyte.jts.utils;

import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryCollection;
import com.vividsolutions.jts.geom.GeometryFactory;

public class GeometryGroup extends GeometryCollection {
   private static final long serialVersionUID = 20100485742596114L;

   public GeometryGroup(GeometryFactory factory, Geometry... geometries) {
      super(geometries, factory);
   }

   public boolean intersects(Geometry g) {
      for (Geometry geometry : this.geometries) {
         if (g.intersects(geometry)) {
            return true;
         }
      }

      return false;
   }
}
