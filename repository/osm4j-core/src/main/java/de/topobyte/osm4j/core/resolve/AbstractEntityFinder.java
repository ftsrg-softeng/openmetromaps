package de.topobyte.osm4j.core.resolve;

import de.topobyte.adt.multicollections.MultiSet;
import de.topobyte.osm4j.core.model.iface.EntityType;
import de.topobyte.osm4j.core.model.iface.OsmNode;
import de.topobyte.osm4j.core.model.iface.OsmRelation;
import de.topobyte.osm4j.core.model.iface.OsmRelationMember;
import de.topobyte.osm4j.core.model.iface.OsmWay;
import java.util.Collection;
import java.util.Set;

public abstract class AbstractEntityFinder implements EntityFinder {
   @Override
   public void findWayNodes(Collection<OsmWay> ways, Collection<OsmNode> outNodes) throws EntityNotFoundException {
      for (OsmWay way : ways) {
         this.findWayNodes(way, outNodes);
      }
   }

   @Override
   public void findMemberNodes(Collection<OsmRelation> relations, Set<OsmNode> outNodes) throws EntityNotFoundException {
      for (OsmRelation relation : relations) {
         this.findMemberNodes(relation, outNodes);
      }
   }

   @Override
   public void findMemberWays(Collection<OsmRelation> relations, Set<OsmWay> outWays) throws EntityNotFoundException {
      for (OsmRelation relation : relations) {
         this.findMemberWays(relation, outWays);
      }
   }

   @Override
   public void findMemberWays(Collection<OsmRelation> relations, MultiSet<OsmWay> outWays) throws EntityNotFoundException {
      for (OsmRelation relation : relations) {
         this.findMemberWays(relation, outWays);
      }
   }

   @Override
   public void findMemberRelations(Collection<OsmRelation> relations, Set<OsmRelation> outRelations) throws EntityNotFoundException {
      for (OsmRelation relation : relations) {
         this.findMemberRelations(relation, outRelations);
      }
   }

   @Override
   public void findMemberNodesAndWays(Collection<OsmRelation> relations, Set<OsmNode> outNodes, Set<OsmWay> outWays) throws EntityNotFoundException {
      for (OsmRelation relation : relations) {
         this.findMemberNodesAndWays(relation, outNodes, outWays);
      }
   }

   @Override
   public void findMemberNodesAndWayNodes(Collection<OsmRelation> relations, Set<OsmNode> outNodes) throws EntityNotFoundException {
      for (OsmRelation relation : relations) {
         this.findMemberNodesAndWayNodes(relation, outNodes);
      }
   }

   protected void addMember(
      OsmRelationMember member,
      Collection<OsmNode> outNodes,
      Collection<OsmWay> outWays,
      Collection<OsmRelation> outRelations,
      OsmEntityProvider entityProvider
   ) throws EntityNotFoundException {
      if (member.getType() == EntityType.Node) {
         if (outNodes != null) {
            outNodes.add(entityProvider.getNode(member.getId()));
         }
      } else if (member.getType() == EntityType.Way) {
         if (outWays != null) {
            outWays.add(entityProvider.getWay(member.getId()));
         }
      } else if (member.getType() == EntityType.Relation && outRelations != null) {
         outRelations.add(entityProvider.getRelation(member.getId()));
      }
   }
}
