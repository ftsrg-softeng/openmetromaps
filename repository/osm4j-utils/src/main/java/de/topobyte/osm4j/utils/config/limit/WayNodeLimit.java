package de.topobyte.osm4j.utils.config.limit;

public class WayNodeLimit implements WayLimit {
   private int maxWayNodes;

   public WayNodeLimit(int maxWayNodes) {
      this.maxWayNodes = maxWayNodes;
   }

   public int getMaxWayNodes() {
      return this.maxWayNodes;
   }
}
