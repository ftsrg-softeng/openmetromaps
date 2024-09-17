package de.topobyte.osm4j.geometry.relation;

import de.topobyte.osm4j.core.model.iface.OsmWay;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WaySegment {
   static final Logger logger = LoggerFactory.getLogger(WaySegment.class);
   private OsmWay way;
   private boolean reverse;

   public WaySegment(OsmWay way, boolean reverse) {
      this.way = way;
      this.reverse = reverse;
   }

   public OsmWay getWay() {
      return this.way;
   }

   public boolean isReverse() {
      return this.reverse;
   }

   @Override
   public boolean equals(Object o) {
      if (o instanceof WaySegment) {
         WaySegment other = (WaySegment)o;
         return other.getWay().equals(this.way);
      } else {
         return false;
      }
   }

   @Override
   public int hashCode() {
      return (int)this.way.getId();
   }

   public int getNumberOfNodes() {
      return this.way.getNumberOfNodes();
   }

   public long getNodeId(int n) {
      return !this.reverse ? this.way.getNodeId(n) : this.way.getNodeId(this.way.getNumberOfNodes() - 1 - n);
   }
}
