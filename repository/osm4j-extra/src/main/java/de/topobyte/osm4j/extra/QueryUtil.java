package de.topobyte.osm4j.extra;

import com.slimjars.dist.gnu.trove.map.TLongObjectMap;
import com.slimjars.dist.gnu.trove.set.TLongSet;
import de.topobyte.osm4j.core.access.OsmOutputStream;
import de.topobyte.osm4j.core.model.iface.EntityType;
import de.topobyte.osm4j.core.model.iface.OsmNode;
import de.topobyte.osm4j.core.model.iface.OsmRelation;
import de.topobyte.osm4j.core.model.iface.OsmRelationMember;
import de.topobyte.osm4j.core.model.iface.OsmWay;
import de.topobyte.osm4j.core.resolve.EntityNotFoundException;
import de.topobyte.osm4j.core.resolve.OsmEntityProvider;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;

public class QueryUtil {
   public static int putNodes(OsmWay way, TLongObjectMap<OsmNode> nodes, OsmEntityProvider entityProvider) {
      int nMissing = 0;

      for (int i = 0; i < way.getNumberOfNodes(); i++) {
         long nodeId = way.getNodeId(i);

         try {
            nodes.put(nodeId, entityProvider.getNode(nodeId));
         } catch (EntityNotFoundException var8) {
            nMissing++;
         }
      }

      return nMissing;
   }

   public static void putNodes(OsmWay way, TLongObjectMap<OsmNode> nodes, OsmEntityProvider entityProvider, TLongSet nodeIds) throws EntityNotFoundException {
      for (int i = 0; i < way.getNumberOfNodes(); i++) {
         long nodeId = way.getNodeId(i);
         if (!nodeIds.contains(nodeId)) {
            nodes.put(nodeId, entityProvider.getNode(nodeId));
            nodeIds.add(nodeId);
         }
      }
   }

   public static void putNodes(OsmRelation relation, TLongObjectMap<OsmNode> nodes, OsmEntityProvider entityProvider) throws EntityNotFoundException {
      for (int i = 0; i < relation.getNumberOfMembers(); i++) {
         OsmRelationMember member = relation.getMember(i);
         if (member.getType() == EntityType.Node) {
            long nodeId = member.getId();
            nodes.put(nodeId, entityProvider.getNode(nodeId));
         }
      }
   }

   public static void putNodes(
      OsmRelation relation, TLongObjectMap<OsmNode> nodes, OsmEntityProvider entityProvider, TLongSet nodeIds, MissingEntityCounter counter
   ) {
      int nMissing = 0;

      for (int i = 0; i < relation.getNumberOfMembers(); i++) {
         OsmRelationMember member = relation.getMember(i);
         if (member.getType() == EntityType.Node) {
            long nodeId = member.getId();
            if (!nodeIds.contains(nodeId)) {
               try {
                  nodes.put(nodeId, entityProvider.getNode(nodeId));
               } catch (EntityNotFoundException var11) {
                  nMissing++;
               }
            }
         }
      }

      counter.addNodes(nMissing);
   }

   public static void putWays(OsmRelation relation, TLongObjectMap<OsmWay> ways, OsmEntityProvider entityProvider, TLongSet wayIds) throws EntityNotFoundException {
      for (int i = 0; i < relation.getNumberOfMembers(); i++) {
         OsmRelationMember member = relation.getMember(i);
         if (member.getType() == EntityType.Way) {
            long wayId = member.getId();
            if (!wayIds.contains(wayId)) {
               ways.put(wayId, entityProvider.getWay(wayId));
            }
         }
      }
   }

   public static void putWaysAndWayNodes(OsmRelation relation, TLongObjectMap<OsmNode> nodes, TLongObjectMap<OsmWay> ways, OsmEntityProvider entityProvider) throws EntityNotFoundException {
      for (int i = 0; i < relation.getNumberOfMembers(); i++) {
         OsmRelationMember member = relation.getMember(i);
         if (member.getType() == EntityType.Way) {
            long wayId = member.getId();
            if (!ways.containsKey(wayId)) {
               OsmWay way = entityProvider.getWay(wayId);
               ways.put(wayId, way);
               putNodes(way, nodes, entityProvider);
            }
         }
      }
   }

   public static void putWaysAndWayNodes(
      OsmRelation relation,
      TLongObjectMap<OsmNode> nodes,
      TLongObjectMap<OsmWay> ways,
      OsmEntityProvider entityProvider,
      TLongSet wayIds,
      MissingEntityCounter counter
   ) {
      int nMissingWays = 0;
      int nMissingWayNodes = 0;

      for (int i = 0; i < relation.getNumberOfMembers(); i++) {
         OsmRelationMember member = relation.getMember(i);
         if (member.getType() == EntityType.Way) {
            long wayId = member.getId();
            if (!wayIds.contains(wayId)) {
               try {
                  OsmWay way = entityProvider.getWay(wayId);
                  ways.put(wayId, way);
                  nMissingWayNodes += putNodes(way, nodes, entityProvider);
               } catch (EntityNotFoundException var14) {
                  nMissingWays++;
               }
            }
         }
      }

      counter.addWays(nMissingWays);
      counter.addWayNodes(nMissingWayNodes);
   }

   public static void writeNodes(TLongObjectMap<OsmNode> map, OsmOutputStream osmOutput) throws IOException {
      long[] ids = map.keys();
      Arrays.sort(ids);

      for (long id : ids) {
         OsmNode node = (OsmNode)map.get(id);
         osmOutput.write(node);
      }
   }

   public static void writeWays(TLongObjectMap<OsmWay> map, OsmOutputStream osmOutput) throws IOException {
      long[] ids = map.keys();
      Arrays.sort(ids);

      for (long id : ids) {
         OsmWay way = (OsmWay)map.get(id);
         osmOutput.write(way);
      }
   }

   public static void writeRelations(TLongObjectMap<OsmRelation> map, OsmOutputStream osmOutput) throws IOException {
      long[] ids = map.keys();
      Arrays.sort(ids);

      for (long id : ids) {
         OsmRelation relation = (OsmRelation)map.get(id);
         osmOutput.write(relation);
      }
   }

   public static boolean anyNodeContainedIn(OsmWay way, TLongSet nodeIds) {
      for (int i = 0; i < way.getNumberOfNodes(); i++) {
         if (nodeIds.contains(way.getNodeId(i))) {
            return true;
         }
      }

      return false;
   }

   public static boolean anyMemberContainedIn(OsmRelation relation, TLongSet nodeIds, TLongSet wayIds) {
      for (int i = 0; i < relation.getNumberOfMembers(); i++) {
         OsmRelationMember member = relation.getMember(i);
         if (member.getType() == EntityType.Node && nodeIds.contains(member.getId()) || member.getType() == EntityType.Way && wayIds.contains(member.getId())) {
            return true;
         }
      }

      return false;
   }

   public static boolean anyMemberContainedIn(Collection<OsmRelation> relations, TLongSet nodeIds, TLongSet wayIds) {
      for (OsmRelation relation : relations) {
         if (anyMemberContainedIn(relation, nodeIds, wayIds)) {
            return true;
         }
      }

      return false;
   }
}
