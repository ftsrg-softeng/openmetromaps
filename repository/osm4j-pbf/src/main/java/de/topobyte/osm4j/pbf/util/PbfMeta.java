package de.topobyte.osm4j.pbf.util;

import de.topobyte.osm4j.core.model.iface.EntityType;
import de.topobyte.osm4j.pbf.protobuf.Osmformat;
import java.util.HashSet;
import java.util.Set;

public class PbfMeta {
   public static boolean hasMixedContent(Osmformat.PrimitiveBlock block) {
      int count = block.getPrimitivegroupCount();
      if (count <= 1) {
         return false;
      } else {
         Set<EntityType> types = getContentTypes(block);
         return types.size() > 1;
      }
   }

   public static Set<EntityType> getContentTypes(Osmformat.PrimitiveBlock block) {
      int count = block.getPrimitivegroupCount();
      Set<EntityType> types = new HashSet<>();

      for (int i = 0; i < count; i++) {
         Osmformat.PrimitiveGroup group = block.getPrimitivegroup(i);
         EntityType type = getEntityType(group);
         if (type != null) {
            types.add(type);
         }
      }

      return types;
   }

   public static EntityType getEntityType(Osmformat.PrimitiveGroup group) {
      if (group.hasDense() || group.getNodesCount() > 0) {
         return EntityType.Node;
      } else if (group.getWaysCount() > 0) {
         return EntityType.Way;
      } else {
         return group.getRelationsCount() > 0 ? EntityType.Relation : null;
      }
   }
}
