package de.topobyte.osm4j.testing.model;

import de.topobyte.osm4j.core.model.iface.OsmRelation;
import java.util.List;

public class TestRelation extends TestEntity implements OsmRelation {
   private final List<TestRelationMember> members;

   public TestRelation(long id, List<TestRelationMember> members) {
      super(id, null);
      this.members = members;
   }

   public TestRelation(long id, List<TestRelationMember> members, TestMetadata metadata) {
      super(id, metadata);
      this.members = members;
   }

   public TestRelation(long id, List<TestRelationMember> members, List<TestTag> tags) {
      this(id, members, tags, null);
   }

   public TestRelation(long id, List<TestRelationMember> members, List<TestTag> tags, TestMetadata metadata) {
      super(id, tags, metadata);
      this.members = members;
   }

   public List<TestRelationMember> getMembers() {
      return this.members;
   }

   public int getNumberOfMembers() {
      return this.members.size();
   }

   public TestRelationMember getMember(int n) {
      return this.members.get(n);
   }
}
