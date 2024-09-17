package de.topobyte.osm4j.extra.relations;

import com.slimjars.dist.gnu.trove.iterator.TLongIterator;
import com.slimjars.dist.gnu.trove.list.TLongList;
import com.slimjars.dist.gnu.trove.list.array.TLongArrayList;
import com.slimjars.dist.gnu.trove.set.TLongSet;
import com.slimjars.dist.gnu.trove.set.hash.TLongHashSet;
import de.topobyte.adt.graph.Graph;
import de.topobyte.adt.graph.UndirectedGraph;
import de.topobyte.osm4j.core.access.OsmIterator;
import de.topobyte.osm4j.core.model.iface.EntityContainer;
import de.topobyte.osm4j.core.model.iface.EntityType;
import de.topobyte.osm4j.core.model.iface.OsmRelation;
import de.topobyte.osm4j.core.model.iface.OsmRelationMember;
import de.topobyte.osm4j.core.model.util.OsmModelUtil;
import de.topobyte.osm4j.core.util.IdUtil;
import java.io.IOException;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RelationGraph {
   static final Logger logger = LoggerFactory.getLogger(RelationGraph.class);
   private Graph<Long> graph;
   private TLongSet idsHasChildRelations = new TLongHashSet();
   private TLongSet idsIsChildRelation = new TLongHashSet();
   private TLongSet idsSimpleRelations = new TLongHashSet();
   private int numNoChildren = 0;
   private boolean storeSimpleRelations;
   private boolean undirected;

   public RelationGraph(boolean storeSimpleRelations, boolean undirected) {
      this.storeSimpleRelations = storeSimpleRelations;
      this.undirected = undirected;
   }

   public void build(OsmIterator iterator) throws IOException {
      this.graph = (Graph<Long>)(this.undirected ? new UndirectedGraph() : new Graph());

      for (EntityContainer container : iterator) {
         if (container.getType() == EntityType.Relation) {
            OsmRelation relation = (OsmRelation)container.getEntity();
            this.process(relation);
         }
      }
   }

   public void build(Collection<OsmRelation> relations) throws IOException {
      this.graph = (Graph<Long>)(this.undirected ? new UndirectedGraph() : new Graph());

      for (OsmRelation relation : relations) {
         this.process(relation);
      }
   }

   private void process(OsmRelation relation) {
      boolean hasChildRelations = false;
      TLongList childRelationMembers = new TLongArrayList();

      for (OsmRelationMember member : OsmModelUtil.membersAsList(relation)) {
         if (member.getType() == EntityType.Relation) {
            hasChildRelations = true;
            this.idsIsChildRelation.add(member.getId());
            childRelationMembers.add(member.getId());
            if (this.storeSimpleRelations) {
               this.idsSimpleRelations.remove(member.getId());
            }
         }
      }

      long id = relation.getId();
      if (hasChildRelations) {
         this.idsHasChildRelations.add(id);
         if (!this.graph.getNodes().contains(id)) {
            this.graph.addNode(id);
         }

         TLongIterator iterator = childRelationMembers.iterator();

         while (iterator.hasNext()) {
            long memberx = iterator.next();
            if (!this.graph.getNodes().contains(memberx)) {
               this.graph.addNode(memberx);
            }

            this.graph.addEdge(id, memberx);
         }
      } else {
         if (this.storeSimpleRelations && !this.idsIsChildRelation.contains(id)) {
            this.idsSimpleRelations.add(id);
         }

         this.numNoChildren++;
      }
   }

   public int getNumNoChildren() {
      return this.numNoChildren;
   }

   public Graph<Long> getGraph() {
      return this.graph;
   }

   public TLongSet getIdsHasChildRelations() {
      return this.idsHasChildRelations;
   }

   public TLongSet getIdsIsChildRelation() {
      return this.idsIsChildRelation;
   }

   public TLongSet getIdsSimpleRelations() {
      return this.idsSimpleRelations;
   }

   public List<Group> buildGroups() {
      return this.undirected ? this.buildGroupsUndirected() : this.buildGroupsDirected();
   }

   public List<Group> buildGroupsUndirected() {
      List<Group> groups = new LinkedList<>();
      TLongSet nodes = new TLongHashSet(this.graph.getNodes());

      while (!nodes.isEmpty()) {
         long id = this.any(nodes);
         TLongSet reachable = this.reachable(this.graph, id);
         nodes.removeAll(reachable);
         groups.add(new Group(id, reachable));
      }

      return groups;
   }

   private TLongSet reachable(Graph<Long> graph, long id) {
      TLongSet reached = new TLongHashSet();
      TLongSet queue = new TLongHashSet();
      queue.add(id);

      while (!queue.isEmpty()) {
         long current = this.any(queue);
         reached.add(current);

         for (long next : graph.getEdgesOut(current)) {
            if (!reached.contains(next)) {
               queue.add(next);
            }
         }
      }

      return reached;
   }

   private long any(TLongSet nodes) {
      TLongIterator iterator = nodes.iterator();
      long id = iterator.next();
      iterator.remove();
      return id;
   }

   public List<Group> buildGroupsDirected() {
      List<Group> groups = new LinkedList<>();
      TLongSet starts = new TLongHashSet();
      Collection<Long> ids = this.graph.getNodes();

      for (long id : ids) {
         if (this.graph.getEdgesIn(id).isEmpty()) {
            starts.add(id);
         }
      }

      logger.debug("Number of start relations: " + starts.size());

      for (long start : starts.toArray()) {
         groups.add(this.build(start));
      }

      TLongSet remaining = new TLongHashSet();
      remaining.addAll(ids);

      for (Group group : groups) {
         remaining.removeAll(group.getRelationIds());
      }

      if (remaining.size() > 0) {
         logger.debug("remaining: " + remaining.size());

         while (!remaining.isEmpty()) {
            long idx = this.any(remaining);
            TLongSet reachable = this.reachable(this.graph, idx);
            remaining.removeAll(reachable);
            long lowest = IdUtil.lowestId(reachable);
            groups.add(new Group(lowest, reachable));
         }
      }

      return groups;
   }

   private Group build(long start) {
      TLongSet group = new TLongHashSet();
      group.add(start);
      TLongSet left = new TLongHashSet();
      left.addAll(this.graph.getEdgesOut(start));

      while (!left.isEmpty()) {
         long next = this.any(left);
         if (!group.contains(next)) {
            group.add(next);
            Set<Long> out = this.graph.getEdgesOut(next);
            left.addAll(out);
         }
      }

      return new Group(start, group);
   }
}
