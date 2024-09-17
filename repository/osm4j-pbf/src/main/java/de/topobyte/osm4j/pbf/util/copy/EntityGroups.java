package de.topobyte.osm4j.pbf.util.copy;

import de.topobyte.osm4j.core.model.iface.EntityType;
import de.topobyte.osm4j.pbf.protobuf.Osmformat;
import java.util.ArrayList;
import java.util.List;

public class EntityGroups {
   private List<Osmformat.PrimitiveGroup> nodeGroups = new ArrayList<>();
   private List<Osmformat.PrimitiveGroup> wayGroups = new ArrayList<>();
   private List<Osmformat.PrimitiveGroup> relationGroups = new ArrayList<>();

   public static EntityGroups splitEntities(Osmformat.PrimitiveBlock primBlock) {
      EntityGroups groups = new EntityGroups();

      for (int i = 0; i < primBlock.getPrimitivegroupCount(); i++) {
         Osmformat.PrimitiveGroup group = primBlock.getPrimitivegroup(i);
         if (group.getNodesCount() > 0 || group.hasDense()) {
            groups.nodeGroups.add(copyNodesIntoGroup(group));
         }

         if (group.getWaysCount() > 0) {
            groups.wayGroups.add(copyWaysIntoGroup(group));
         }

         if (group.getRelationsCount() > 0) {
            groups.relationGroups.add(copyRelationsIntoGroup(group));
         }
      }

      return groups;
   }

   private static Osmformat.PrimitiveGroup copyNodesIntoGroup(Osmformat.PrimitiveGroup group) {
      Osmformat.PrimitiveGroup.Builder builder = Osmformat.PrimitiveGroup.newBuilder();
      if (group.getNodesCount() > 0) {
         builder.addAllNodes(group.getNodesList());
      }

      if (group.hasDense()) {
         builder.setDense(group.getDense());
      }

      return builder.build();
   }

   private static Osmformat.PrimitiveGroup copyWaysIntoGroup(Osmformat.PrimitiveGroup group) {
      Osmformat.PrimitiveGroup.Builder builder = Osmformat.PrimitiveGroup.newBuilder();
      builder.addAllWays(group.getWaysList());
      return builder.build();
   }

   private static Osmformat.PrimitiveGroup copyRelationsIntoGroup(Osmformat.PrimitiveGroup group) {
      Osmformat.PrimitiveGroup.Builder builder = Osmformat.PrimitiveGroup.newBuilder();
      builder.addAllRelations(group.getRelationsList());
      return builder.build();
   }

   public List<Osmformat.PrimitiveGroup> getNodeGroups() {
      return this.nodeGroups;
   }

   public List<Osmformat.PrimitiveGroup> getWayGroups() {
      return this.wayGroups;
   }

   public List<Osmformat.PrimitiveGroup> getRelationGroups() {
      return this.relationGroups;
   }

   public List<Osmformat.PrimitiveGroup> getGroups(EntityType type) {
      switch (type) {
         case Node:
            return this.nodeGroups;
         case Way:
            return this.wayGroups;
         case Relation:
            return this.relationGroups;
         default:
            return null;
      }
   }
}
