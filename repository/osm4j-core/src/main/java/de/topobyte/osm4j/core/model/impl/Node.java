package de.topobyte.osm4j.core.model.impl;

import de.topobyte.osm4j.core.model.iface.OsmMetadata;
import de.topobyte.osm4j.core.model.iface.OsmNode;
import de.topobyte.osm4j.core.model.iface.OsmTag;
import java.util.List;

public class Node extends Entity implements OsmNode {
   private final double lon;
   private final double lat;

   public Node(long id, double lon, double lat) {
      super(id, null);
      this.lon = lon;
      this.lat = lat;
   }

   public Node(long id, double lon, double lat, OsmMetadata metadata) {
      super(id, metadata);
      this.lon = lon;
      this.lat = lat;
   }

   public Node(long id, double lon, double lat, List<? extends OsmTag> tags) {
      this(id, lon, lat, tags, null);
   }

   public Node(long id, double lon, double lat, List<? extends OsmTag> tags, OsmMetadata metadata) {
      super(id, tags, metadata);
      this.lon = lon;
      this.lat = lat;
   }

   @Override
   public double getLongitude() {
      return this.lon;
   }

   @Override
   public double getLatitude() {
      return this.lat;
   }
}
