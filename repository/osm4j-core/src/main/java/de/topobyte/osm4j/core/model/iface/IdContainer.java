package de.topobyte.osm4j.core.model.iface;

public class IdContainer {
   private EntityType type;
   private long id;

   public IdContainer(EntityType type, long id) {
      this.type = type;
      this.id = id;
   }

   public EntityType getType() {
      return this.type;
   }

   public long getId() {
      return this.id;
   }

   public void setType(EntityType type) {
      this.type = type;
   }

   public void setId(long id) {
      this.id = id;
   }
}
