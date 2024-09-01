package de.topobyte.osm4j.utils.config.limit;

public class RelationMemberLimit implements RelationLimit {
   private int maxMembers;

   public RelationMemberLimit(int maxMembers) {
      this.maxMembers = maxMembers;
   }

   public int getMaxMembers() {
      return this.maxMembers;
   }
}
