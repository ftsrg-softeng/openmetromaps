package de.topobyte.osm4j.testing.model;

import de.topobyte.osm4j.core.model.iface.EntityType;
import de.topobyte.osm4j.core.model.iface.OsmRelationMember;

public class TestRelationMember implements OsmRelationMember {
   private long id;
   private EntityType type;
   private String role;

   public TestRelationMember(long id, EntityType type, String role) {
      this.id = id;
      this.type = type;
      this.role = role;
   }

   public long getId() {
      return this.id;
   }

   public EntityType getType() {
      return this.type;
   }

   public String getRole() {
      return this.role;
   }

   public void setId(long id) {
      this.id = id;
   }

   public void setType(EntityType type) {
      this.type = type;
   }

   public void setRole(String role) {
      this.role = role;
   }
}
