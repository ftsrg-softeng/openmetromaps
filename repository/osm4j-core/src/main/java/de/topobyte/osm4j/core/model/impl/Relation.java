package de.topobyte.osm4j.core.model.impl;

import de.topobyte.osm4j.core.model.iface.OsmMetadata;
import de.topobyte.osm4j.core.model.iface.OsmRelation;
import de.topobyte.osm4j.core.model.iface.OsmRelationMember;
import de.topobyte.osm4j.core.model.iface.OsmTag;
import java.util.List;

public class Relation extends Entity implements OsmRelation {
   private final List<? extends OsmRelationMember> members;

   public Relation(long id, List<? extends OsmRelationMember> members) {
      super(id, null);
      this.members = members;
   }

   public Relation(long id, List<? extends OsmRelationMember> members, OsmMetadata metadata) {
      super(id, metadata);
      this.members = members;
   }

   public Relation(long id, List<? extends OsmRelationMember> members, List<? extends OsmTag> tags) {
      this(id, members, tags, null);
   }

   public Relation(long id, List<? extends OsmRelationMember> members, List<? extends OsmTag> tags, OsmMetadata metadata) {
      super(id, tags, metadata);
      this.members = members;
   }

   public List<? extends OsmRelationMember> getMembers() {
      return this.members;
   }

   @Override
   public int getNumberOfMembers() {
      return this.members.size();
   }

   @Override
   public OsmRelationMember getMember(int n) {
      return this.members.get(n);
   }
}
