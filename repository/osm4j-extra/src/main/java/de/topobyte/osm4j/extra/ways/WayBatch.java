package de.topobyte.osm4j.extra.ways;

import de.topobyte.osm4j.core.model.iface.OsmWay;
import de.topobyte.osm4j.extra.batch.AbstractBatch;

public class WayBatch extends AbstractBatch<OsmWay> {
   private int maxWays;
   private int maxWayNodes;
   private int wayNodes = 0;

   public WayBatch(int maxWays, int maxWayNodes) {
      this.maxWays = maxWays;
      this.maxWayNodes = maxWayNodes;
   }

   @Override
   public void clear() {
      super.clear();
      this.wayNodes = 0;
   }

   public boolean fits(OsmWay way) {
      return this.elements.isEmpty() ? true : this.elements.size() < this.maxWays && this.wayNodes + way.getNumberOfNodes() <= this.maxWayNodes;
   }

   public void add(OsmWay way) {
      super.add(way);
      this.wayNodes = this.wayNodes + way.getNumberOfNodes();
   }

   @Override
   public boolean isFull() {
      return this.elements.size() == this.maxWays || this.wayNodes == this.maxWayNodes;
   }
}
