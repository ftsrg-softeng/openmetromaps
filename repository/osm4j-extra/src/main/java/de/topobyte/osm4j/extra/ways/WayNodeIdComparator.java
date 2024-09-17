package de.topobyte.osm4j.extra.ways;

import de.topobyte.osm4j.core.model.iface.OsmWay;
import java.util.Comparator;

public class WayNodeIdComparator implements Comparator<OsmWay> {
   public int compare(OsmWay o1, OsmWay o2) {
      long id1 = o1.getNodeId(0);
      long id2 = o2.getNodeId(0);
      int cmp = Long.compare(id1, id2);
      return cmp != 0 ? cmp : Long.compare(o1.getId(), o2.getId());
   }
}
