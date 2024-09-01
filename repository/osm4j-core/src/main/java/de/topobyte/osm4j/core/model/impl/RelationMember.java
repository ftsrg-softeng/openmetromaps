package de.topobyte.osm4j.core.model.impl;

import de.topobyte.osm4j.core.model.iface.EntityType;
import de.topobyte.osm4j.core.model.iface.OsmRelationMember;

public class RelationMember implements OsmRelationMember {
   private final long id;
   private final EntityType type;
   private final String role;

   public RelationMember(long id, EntityType type, String role) {
      this.id = id;
      this.type = type;
      this.role = role;
   }

   @Override
   public long getId() {
      return this.id;
   }

   @Override
   public EntityType getType() {
      return this.type;
   }

   @Override
   public String getRole() {
      return this.role;
   }
}
