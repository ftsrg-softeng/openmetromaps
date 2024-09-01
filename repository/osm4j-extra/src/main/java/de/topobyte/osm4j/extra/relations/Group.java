package de.topobyte.osm4j.extra.relations;

import com.slimjars.dist.gnu.trove.set.TLongSet;

public class Group {
   private long start;
   private TLongSet relationIds;
   private int numRelations;
   private int numMembers;

   public Group(long start, TLongSet relationIds) {
      this.start = start;
      this.relationIds = relationIds;
      this.numRelations = relationIds.size();
   }

   public long getStart() {
      return this.start;
   }

   public TLongSet getRelationIds() {
      return this.relationIds;
   }

   public int getNumRelations() {
      return this.numRelations;
   }

   public int getNumMembers() {
      return this.numMembers;
   }

   public void setNumMembers(int numMembers) {
      this.numMembers = numMembers;
   }
}
