package de.topobyte.osm4j.geometry;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Point;
import de.topobyte.osm4j.core.model.iface.OsmNode;

public class NodeBuilder {
   private GeometryFactory factory;

   public NodeBuilder() {
      this(new GeometryFactory());
   }

   public NodeBuilder(GeometryFactory factory) {
      this.factory = factory;
   }

   public Coordinate buildCoordinate(OsmNode node) {
      double lon = node.getLongitude();
      double lat = node.getLatitude();
      return new Coordinate(lon, lat);
   }

   public Point build(OsmNode node) {
      return this.factory.createPoint(this.buildCoordinate(node));
   }
}
