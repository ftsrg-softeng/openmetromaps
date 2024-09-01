package de.topobyte.osm4j.core.model.iface;

public class EntityContainer {
   private EntityType type;
   private OsmEntity entity;

   public EntityContainer(EntityType type, OsmEntity entity) {
      this.type = type;
      this.entity = entity;
   }

   public EntityType getType() {
      return this.type;
   }

   public OsmEntity getEntity() {
      return this.entity;
   }

   public void setType(EntityType type) {
      this.type = type;
   }

   public void setEntity(OsmEntity entity) {
      this.entity = entity;
   }
}
