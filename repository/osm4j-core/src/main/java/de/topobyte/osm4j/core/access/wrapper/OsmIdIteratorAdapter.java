package de.topobyte.osm4j.core.access.wrapper;

import de.topobyte.osm4j.core.access.OsmIdIterator;
import de.topobyte.osm4j.core.access.OsmIterator;
import de.topobyte.osm4j.core.model.iface.EntityContainer;
import de.topobyte.osm4j.core.model.iface.IdContainer;
import de.topobyte.osm4j.core.model.iface.OsmBounds;
import java.util.Iterator;

public class OsmIdIteratorAdapter implements OsmIdIterator {
   private OsmIterator iterator;

   public OsmIdIteratorAdapter(OsmIterator iterator) {
      this.iterator = iterator;
   }

   @Override
   public Iterator<IdContainer> iterator() {
      return this;
   }

   @Override
   public boolean hasNext() {
      return this.iterator.hasNext();
   }

   public IdContainer next() {
      EntityContainer container = this.iterator.next();
      return new IdContainer(container.getType(), container.getEntity().getId());
   }

   @Override
   public void remove() {
      this.iterator.remove();
   }

   @Override
   public boolean hasBounds() {
      return this.iterator.hasBounds();
   }

   @Override
   public OsmBounds getBounds() {
      return this.iterator.getBounds();
   }
}
