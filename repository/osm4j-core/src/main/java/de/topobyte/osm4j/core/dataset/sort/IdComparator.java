package de.topobyte.osm4j.core.dataset.sort;

import de.topobyte.osm4j.core.model.iface.OsmEntity;
import java.util.Comparator;

public class IdComparator implements Comparator<OsmEntity> {
   public int compare(OsmEntity o1, OsmEntity o2) {
      return Long.compare(o1.getId(), o2.getId());
   }
}
