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

public class EntityFinderThrowMissing extends AbstractEntityFinder {
   private OsmEntityProvider entityProvider;

   public EntityFinderThrowMissing(OsmEntityProvider entityProvider) {
      this.entityProvider = entityProvider;
   }

   @Override
   public List<OsmNode> findNodes(TLongCollection ids) throws EntityNotFoundException {
      List<OsmNode> nodes = new ArrayList<>();
      TLongIterator idIterator = ids.iterator();

      while (idIterator.hasNext()) {
         nodes.add(this.entityProvider.getNode(idIterator.next()));
      }

      return nodes;
   }

   @Override
   public List<OsmWay> findWays(TLongCollection ids) throws EntityNotFoundException {
      List<OsmWay> ways = new ArrayList<>();
      TLongIterator idIterator = ids.iterator();

      while (idIterator.hasNext()) {
         ways.add(this.entityProvider.getWay(idIterator.next()));
      }

      return ways;
   }

   @Override
   public List<OsmRelation> findRelations(TLongCollection ids) throws EntityNotFoundException {
      List<OsmRelation> relations = new ArrayList<>();
      TLongIterator idIterator = ids.iterator();

      while (idIterator.hasNext()) {
         relations.add(this.entityProvider.getRelation(idIterator.next()));
      }

      return relations;
   }

   @Override
   public void findWayNodes(OsmWay way, Collection<OsmNode> outNodes) throws EntityNotFoundException {
      for (int i = 0; i < way.getNumberOfNodes(); i++) {
         outNodes.add(this.entityProvider.getNode(way.getNodeId(i)));
      }
   }

   @Override
   public void findMemberNodes(OsmRelation relation, Set<OsmNode> outNodes) throws EntityNotFoundException {
      for (OsmRelationMember member : OsmModelUtil.membersAsList(relation)) {
         this.addMember(member, outNodes, null, null, this.entityProvider);
      }
   }

   @Override
   public void findMemberWays(OsmRelation relation, Set<OsmWay> outWays) throws EntityNotFoundException {
      for (OsmRelationMember member : OsmModelUtil.membersAsList(relation)) {
         this.addMember(member, null, outWays, null, this.entityProvider);
      }
   }

   @Override
   public void findMemberWays(OsmRelation relation, MultiSet<OsmWay> outWays) throws EntityNotFoundException {
      for (OsmRelationMember member : OsmModelUtil.membersAsList(relation)) {
         if (member.getType() == EntityType.Way) {
            outWays.add(this.entityProvider.getWay(member.getId()));
         }
      }
   }

   @Override
   public void findMemberRelations(OsmRelation relation, Set<OsmRelation> outRelations) throws EntityNotFoundException {
      for (OsmRelationMember member : OsmModelUtil.membersAsList(relation)) {
         this.addMember(member, null, null, outRelations, this.entityProvider);
      }
   }

   @Override
   public void findMemberRelationsRecursively(OsmRelation relation, Set<OsmRelation> outRelations) throws EntityNotFoundException {
      Deque<OsmRelation> queue = new LinkedList<>();
      queue.add(relation);
      this.findMemberRelationsRecursively(queue, outRelations);
   }

   @Override
   public void findMemberRelationsRecursively(Collection<OsmRelation> relations, Set<OsmRelation> outRelations) throws EntityNotFoundException {
      Deque<OsmRelation> queue = new LinkedList<>();
      queue.addAll(relations);
      this.findMemberRelationsRecursively(queue, outRelations);
   }

   private void findMemberRelationsRecursively(Deque<OsmRelation> queue, Set<OsmRelation> outRelations) throws EntityNotFoundException {
      TLongSet ids = new TLongHashSet();

      while (!queue.isEmpty()) {
         OsmRelation relation = queue.remove();

         for (OsmRelationMember member : OsmModelUtil.membersAsList(relation)) {
            if (member.getType() == EntityType.Relation) {
               long id = member.getId();
               if (!ids.contains(id)) {
                  ids.add(id);
                  OsmRelation child = this.entityProvider.getRelation(id);
                  outRelations.add(child);
                  queue.add(child);
               }
            }
         }
      }
   }

   @Override
   public void findMemberNodesAndWays(OsmRelation relation, Set<OsmNode> outNodes, Set<OsmWay> outWays) throws EntityNotFoundException {
      for (OsmRelationMember member : OsmModelUtil.membersAsList(relation)) {
         this.addMember(member, outNodes, outWays, null, this.entityProvider);
      }
   }

   @Override
   public void findMemberNodesAndWayNodes(OsmRelation relation, Set<OsmNode> outNodes) throws EntityNotFoundException {
      Set<OsmWay> ways = new HashSet<>();
      this.findMemberNodesAndWays(relation, outNodes, ways);
      this.findWayNodes(ways, outNodes);
   }
}
