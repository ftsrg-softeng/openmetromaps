package de.topobyte.osm4j.extra.datatree;

import com.vividsolutions.jts.geom.Envelope;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryCollection;
import com.vividsolutions.jts.geom.GeometryFactory;
import java.util.List;

public class BoxUtil {
   public static Envelope WORLD_BOUNDS = new Envelope(-180.0, 180.0, -86.0, 86.0);

   public static GeometryCollection createBoxesGeometry(DataTree tree, Envelope bound) {
      return createBoxesGeometry(tree.getLeafs(), bound);
   }

   public static GeometryCollection createBoxesGeometry(List<Node> leafs, Envelope bound) {
      GeometryFactory factory = new GeometryFactory();
      Geometry[] boxes = new Geometry[leafs.size()];

      for (int i = 0; i < leafs.size(); i++) {
         Node leaf = leafs.get(i);
         Envelope envelope = leaf.getEnvelope().intersection(bound);
         Geometry g = factory.toGeometry(envelope);
         boxes[i] = g;
      }

      return factory.createGeometryCollection(boxes);
   }
}
