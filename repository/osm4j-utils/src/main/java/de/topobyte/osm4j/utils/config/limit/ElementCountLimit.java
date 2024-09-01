package de.topobyte.osm4j.utils.config.limit;

public class ElementCountLimit implements NodeLimit, WayLimit, RelationLimit {
   private int maxElements;

   public ElementCountLimit(int maxElements) {
      this.maxElements = maxElements;
   }

   public int getMaxElements() {
      return this.maxElements;
   }
}
