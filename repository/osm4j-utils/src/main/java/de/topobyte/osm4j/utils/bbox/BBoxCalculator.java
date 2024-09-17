package de.topobyte.osm4j.utils.bbox;

import com.vividsolutions.jts.geom.Envelope;
import de.topobyte.adt.geo.BBox;
import de.topobyte.osm4j.core.access.OsmIterator;
import de.topobyte.osm4j.core.model.iface.EntityContainer;
import de.topobyte.osm4j.core.model.iface.OsmNode;

public class BBoxCalculator {
   private OsmIterator iterator;

   public BBoxCalculator(OsmIterator iterator) {
      this.iterator = iterator;
   }

   public BBox execute() {
      Envelope envelope = new Envelope();

      while (this.iterator.hasNext()) {
         EntityContainer entityContainer = (EntityContainer)this.iterator.next();
         switch (entityContainer.getType()) {
            case Node:
               OsmNode node = (OsmNode)entityContainer.getEntity();
               envelope.expandToInclude(node.getLongitude(), node.getLatitude());
               break;
            case Way:
            case Relation:
               return new BBox(envelope);
         }
      }

      return new BBox(envelope);
   }
}
