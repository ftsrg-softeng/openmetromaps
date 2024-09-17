package de.topobyte.osm4j.tbo.batching;

import de.topobyte.osm4j.core.model.iface.OsmRelation;

public class MemberCountBatchBuilder implements BatchBuilder<OsmRelation> {
   private int maxReferences;
   private int counter = 0;

   public MemberCountBatchBuilder(int maxReferences) {
      this.maxReferences = maxReferences;
   }

   public void add(OsmRelation element) {
      this.counter = this.counter + element.getNumberOfMembers();
   }

   @Override
   public boolean full() {
      return this.counter >= this.maxReferences;
   }

   public boolean fits(OsmRelation element) {
      return this.counter + element.getNumberOfMembers() <= this.maxReferences;
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
