package de.topobyte.osm4j.tbo.writerhelper;

import de.topobyte.osm4j.core.model.iface.EntityType;

public class EntityTypeHelper {
   public static EntityType getType(int typeByte) {
      switch (typeByte) {
         case 1:
         case 3:
         default:
            return EntityType.Node;
         case 2:
            return EntityType.Way;
         case 4:
            return EntityType.Relation;
      }
   }

   public static int getByte(EntityType type) {
      if (type == EntityType.Node) {
         return 1;
      } else {
         return type == EntityType.Way ? 2 : 4;
      }
   }
}
