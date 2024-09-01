package de.topobyte.osm4j.core.resolve;

import com.slimjars.dist.gnu.trove.TLongCollection;
import com.slimjars.dist.gnu.trove.iterator.TLongIterator;
import com.slimjars.dist.gnu.trove.set.TLongSet;
import com.slimjars.dist.gnu.trove.set.hash.TLongHashSet;
import de.topobyte.adt.multicollections.MultiSet;
import de.topobyte.osm4j.core.model.iface.EntityType;
import de.topobyte.osm4j.core.model.iface.OsmNode;
import de.topobyte.osm4j.core.model.iface.OsmRelation;
import de.topobyte.osm4j.core.model.iface.OsmRelationMember;
import de.topobyte.osm4j.core.model.iface.OsmWay;
import de.topobyte.osm4j.core.model.util.OsmModelUtil;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Deque;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class EntityFinderLogMissing extends AbstractEntityFinder {
   static final Logger logger = LoggerFactory.getLogger(EntityFinderLogMissing.class);
   private OsmEntityProvider entityProvider;

   public EntityFinderLogMissing(OsmEntityProvider entityProvider) {
      this.entityProvider = entityProvider;
   }

   protected abstract void log(String var1);

   private void logNodeNotFound(long id) {
      String message = String.format("Unable to find node with id %d", id);
      this.log(message);
   }

   private void logWayNotFound(long id) {
      String message = String.format("Unable to find way with id %d", id);
      this.log(message);
   }

   private void logRelationNotFound(long id) {
      String message = String.format("Unable to find relation with id %d", id);
      this.log(message);
   }

   private void logWayNodeNotFound(OsmWay way, long nodeId) {
      String message = String.format("Unable to find way node: way id %d, node id %d", way.getId(), nodeId);
      this.log(message);
   }

   private void logMemberNotFound(OsmRelation relation, OsmRelationMember member) {
      String message = String.format("Unable to find member: relation id %d, member %s:%d", relation.getId(), member.getType().toString(), member.getId());
      this.log(message);
   }

   @Override
   public List<OsmNode> findNodes(TLongCollection ids) throws EntityNotFoundException {
      List<OsmNode> nodes = new ArrayList<>();
      TLongIterator idIterator = ids.iterator();

      while (idIterator.hasNext()) {
         long id = idIterator.next();

         try {
            nodes.add(this.entityProvider.getNode(id));
         } catch (EntityNotFoundException var7) {
            this.logNodeNotFound(id);
         }
      }

      return nodes;
   }

   @Override
   public List<OsmWay> findWays(TLongCollection ids) throws EntityNotFoundException {
      List<OsmWay> ways = new ArrayList<>();
      TLongIterator idIterator = ids.iterator();

      while (idIterator.hasNext()) {
         long id = idIterator.next();

         try {
            ways.add(this.entityProvider.getWay(id));
         } catch (EntityNotFoundException var7) {
            this.logWayNotFound(id);
         }
      }

      return ways;
   }

   @Override
   public List<OsmRelation> findRelations(TLongCollection ids) {
      List<OsmRelation> relations = new ArrayList<>();
      TLongIterator idIterator = ids.iterator();

      while (idIterator.hasNext()) {
         long id = idIterator.next();

         try {
            relations.add(this.entityProvider.getRelation(id));
         } catch (EntityNotFoundException var7) {
            this.logRelationNotFound(id);
         }
      }

      return relations;
   }

   @Override
   public void findWayNodes(OsmWay way, Collection<OsmNode> outNodes) {
      for (int i = 0; i < way.getNumberOfNodes(); i++) {
         long nodeId = way.getNodeId(i);

         try {
            outNodes.add(this.entityProvider.getNode(nodeId));
         } catch (EntityNotFoundException var7) {
            this.logWayNodeNotFound(way, nodeId);
         }
      }
   }

   @Override
   public void findMemberNodes(OsmRelation relation, Set<OsmNode> outNodes) {
      for (OsmRelationMember member : OsmModelUtil.membersAsList(relation)) {
         try {
            this.addMember(member, outNodes, null, null, this.entityProvider);
         } catch (EntityNotFoundException var6) {
            this.logMemberNotFound(relation, member);
         }
      }
   }

   @Override
   public void findMemberWays(OsmRelation relation, Set<OsmWay> outWays) {
      for (OsmRelationMember member : OsmModelUtil.membersAsList(relation)) {
         try {
            this.addMember(member, null, outWays, null, this.entityProvider);
         } catch (EntityNotFoundException var6) {
            this.logMemberNotFound(relation, member);
         }
      }
   }

   @Override
   public void findMemberWays(OsmRelation relation, MultiSet<OsmWay> outWays) throws EntityNotFoundException {
      for (OsmRelationMember member : OsmModelUtil.membersAsList(relation)) {
         if (member.getType() == EntityType.Way) {
            try {
               outWays.add(this.entityProvider.getWay(member.getId()));
            } catch (EntityNotFoundException var6) {
               this.logMemberNotFound(relation, member);
            }
         }
      }
   }

   @Override
   public void findMemberRelations(OsmRelation relation, Set<OsmRelation> outRelations) throws EntityNotFoundException {
      for (OsmRelationMember member : OsmModelUtil.membersAsList(relation)) {
         try {
            this.addMember(member, null, null, outRelations, this.entityProvider);
         } catch (EntityNotFoundException var6) {
            this.logMemberNotFound(relation, member);
         }
      }
   }

   @Override
   public void findMemberRelationsRecursively(OsmRelation relation, Set<OsmRelation> outRelations) {
      Deque<OsmRelation> queue = new LinkedList<>();
      queue.add(relation);
      this.findMemberRelationsRecursively(queue, outRelations);
   }

   @Override
   public void findMemberRelationsRecursively(Collection<OsmRelation> relations, Set<OsmRelation> outRelations) {
      Deque<OsmRelation> queue = new LinkedList<>();
      queue.addAll(relations);
      this.findMemberRelationsRecursively(queue, outRelations);
   }

   private void findMemberRelationsRecursively(Deque<OsmRelation> queue, Set<OsmRelation> outRelations) {
      TLongSet ids = new TLongHashSet();

      while (!queue.isEmpty()) {
         OsmRelation relation = queue.remove();

         for (OsmRelationMember member : OsmModelUtil.membersAsList(relation)) {
            if (member.getType() == EntityType.Relation) {
               long id = member.getId();
               if (!ids.contains(id)) {
                  ids.add(id);

                  try {
                     OsmRelation child = this.entityProvider.getRelation(id);
                     outRelations.add(child);
                     queue.add(child);
                  } catch (EntityNotFoundException var10) {
                     this.logRelationNotFound(id);
                  }
               }
            }
         }
      }
   }

   @Override
   public void findMemberNodesAndWays(OsmRelation relation, Set<OsmNode> outNodes, Set<OsmWay> outWays) {
      for (OsmRelationMember member : OsmModelUtil.membersAsList(relation)) {
         try {
            this.addMember(member, outNodes, outWays, null, this.entityProvider);
         } catch (EntityNotFoundException var7) {
            this.logMemberNotFound(relation, member);
         }
      }
   }

   @Override
   public void findMemberNodesAndWayNodes(OsmRelation relation, Set<OsmNode> outNodes) throws EntityNotFoundException {
      Set<OsmWay> ways = new HashSet<>();
      this.findMemberNodesAndWays(relation, outNodes, ways);
      this.findWayNodes(ways, outNodes);
   }
}
