package de.topobyte.osm4j.testing.model;

import de.topobyte.osm4j.core.model.iface.OsmNode;
import java.util.List;

public class TestNode extends TestEntity implements OsmNode {
   private double lon;
   private double lat;

   public TestNode(long id, double lon, double lat) {
      super(id, null);
      this.lon = lon;
      this.lat = lat;
   }

   public TestNode(long id, double lon, double lat, TestMetadata metadata) {
      super(id, metadata);
      this.lon = lon;
      this.lat = lat;
   }

   public TestNode(long id, double lon, double lat, List<TestTag> tags) {
      this(id, lon, lat, tags, null);
   }

   public TestNode(long id, double lon, double lat, List<TestTag> tags, TestMetadata metadata) {
      super(id, tags, metadata);
      this.lon = lon;
      this.lat = lat;
   }

   public double getLongitude() {
      return this.lon;
   }

   public double getLatitude() {
      return this.lat;
   }

   public void setLongitude(double lon) {
      this.lon = lon;
   }

   public void setLatitude(double lat) {
      this.lat = lat;
   }
}
