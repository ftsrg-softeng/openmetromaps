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

public class EntityFinderIgnoreMissing extends AbstractEntityFinder {
   private OsmEntityProvider entityProvider;

   public EntityFinderIgnoreMissing(OsmEntityProvider entityProvider) {
      this.entityProvider = entityProvider;
   }

   @Override
   public List<OsmNode> findNodes(TLongCollection ids) throws EntityNotFoundException {
      List<OsmNode> nodes = new ArrayList<>();
      TLongIterator idIterator = ids.iterator();

      while (idIterator.hasNext()) {
         try {
            nodes.add(this.entityProvider.getNode(idIterator.next()));
         } catch (EntityNotFoundException var5) {
         }
      }

      return nodes;
   }

   @Override
   public List<OsmWay> findWays(TLongCollection ids) throws EntityNotFoundException {
      List<OsmWay> ways = new ArrayList<>();
      TLongIterator idIterator = ids.iterator();

      while (idIterator.hasNext()) {
         try {
            ways.add(this.entityProvider.getWay(idIterator.next()));
         } catch (EntityNotFoundException var5) {
         }
      }

      return ways;
   }

   @Override
   public List<OsmRelation> findRelations(TLongCollection ids) {
      List<OsmRelation> relations = new ArrayList<>();
      TLongIterator idIterator = ids.iterator();

      while (idIterator.hasNext()) {
         try {
            relations.add(this.entityProvider.getRelation(idIterator.next()));
         } catch (EntityNotFoundException var5) {
         }
      }

      return relations;
   }

   @Override
   public void findWayNodes(OsmWay way, Collection<OsmNode> outNodes) {
      for (int i = 0; i < way.getNumberOfNodes(); i++) {
         try {
            outNodes.add(this.entityProvider.getNode(way.getNodeId(i)));
         } catch (EntityNotFoundException var5) {
         }
      }
   }

   @Override
   public void findMemberNodes(OsmRelation relation, Set<OsmNode> outNodes) {
      for (OsmRelationMember member : OsmModelUtil.membersAsList(relation)) {
         try {
            this.addMember(member, outNodes, null, null, this.entityProvider);
         } catch (EntityNotFoundException var6) {
         }
      }
   }

   @Override
   public void findMemberWays(OsmRelation relation, Set<OsmWay> outWays) {
      for (OsmRelationMember member : OsmModelUtil.membersAsList(relation)) {
         try {
            this.addMember(member, null, outWays, null, this.entityProvider);
         } catch (EntityNotFoundException var6) {
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
