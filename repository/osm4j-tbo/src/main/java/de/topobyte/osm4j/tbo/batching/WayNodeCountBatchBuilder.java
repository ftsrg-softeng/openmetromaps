package de.topobyte.osm4j.tbo.batching;

import de.topobyte.osm4j.core.model.iface.OsmWay;

public class WayNodeCountBatchBuilder implements BatchBuilder<OsmWay> {
   private int maxReferences;
   private int counter = 0;

   public WayNodeCountBatchBuilder(int maxReferences) {
      this.maxReferences = maxReferences;
   }

   public void add(OsmWay element) {
      this.counter = this.counter + element.getNumberOfNodes();
   }

   @Override
   public boolean full() {
      return this.counter >= this.maxReferences;
   }

   public boolean fits(OsmWay element) {
      return this.counter + element.getNumberOfNodes() <= this.maxReferences;
   }

   @Override
   public void clear() {
      this.counter = 0;
   }

   @Override
   public int bufferSizeHint() {
      return this.maxReferences / 10;
   }
}
