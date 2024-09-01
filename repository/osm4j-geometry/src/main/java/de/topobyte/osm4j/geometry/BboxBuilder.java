package de.topobyte.osm4j.geometry;

import com.vividsolutions.jts.geom.Envelope;
import de.topobyte.osm4j.core.model.iface.OsmNode;
import java.util.Collection;

public class BboxBuilder {
   public static Envelope box(Collection<OsmNode> nodes) {
      Envelope env = new Envelope();

      for (OsmNode node : nodes) {
         env.expandToInclude(node.getLongitude(), node.getLatitude());
      }

      return env;
   }
}
