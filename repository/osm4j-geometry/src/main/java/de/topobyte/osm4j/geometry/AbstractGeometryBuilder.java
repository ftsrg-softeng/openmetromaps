package de.topobyte.osm4j.geometry;

import com.vividsolutions.jts.geom.GeometryFactory;

public class AbstractGeometryBuilder {
   protected GeometryFactory factory;

   public AbstractGeometryBuilder(GeometryFactory factory) {
      this.factory = factory;
   }

   public GeometryFactory getGeometryFactory() {
      return this.factory;
   }
}
