package de.topobyte.osm4j.utils.merge;

import com.vividsolutions.jts.geom.Envelope;
import de.topobyte.osm4j.core.access.OsmIdIterator;
import de.topobyte.osm4j.core.model.iface.OsmBounds;
import de.topobyte.osm4j.core.model.impl.Bounds;
import java.util.Collection;

public class AbstractIdMerge {
   protected Collection<OsmIdIterator> inputs;
   protected boolean hasBounds;
   protected Bounds bounds;

   public AbstractIdMerge(Collection<OsmIdIterator> inputs) {
      this.inputs = inputs;
      this.initBounds();
   }

   protected void initBounds() {
      this.hasBounds = false;
      Envelope envelope = new Envelope();

      for (OsmIdIterator iterator : this.inputs) {
         if (iterator.hasBounds()) {
            this.hasBounds = true;
            OsmBounds bounds = iterator.getBounds();
            Envelope e = new Envelope(bounds.getLeft(), bounds.getRight(), bounds.getBottom(), bounds.getTop());
            envelope.expandToInclude(e);
         }
      }

      this.bounds = !this.hasBounds ? null : new Bounds(envelope.getMinX(), envelope.getMaxX(), envelope.getMaxY(), envelope.getMinY());
   }
}
