package de.topobyte.adt.graph;

import de.topobyte.adt.graph.factories.HashMapFactory;
import de.topobyte.adt.graph.factories.HashSetFactory;
import de.topobyte.adt.graph.factories.MapFactory;
import de.topobyte.adt.graph.factories.SetFactory;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Graph<T> {
   private SetFactory<T> setFactory;
   private MapFactory<T, Set<T>> mapFactory;
   private Set<T> nodes;
   private Map<T, Set<T>> edgesOut;
   private Map<T, Set<T>> edgesIn;

   public Graph() {
      this.setDefaultFactories();
      this.initNodesAndEdges();
   }

   public Graph(SetFactory<T> setFactory, MapFactory<T, Set<T>> mapFactory) {
      this.setFactories(setFactory, mapFactory);
      this.initNodesAndEdges();
   }

   private Graph(SetFactory<T> setFactory, MapFactory<T, Set<T>> mapFactory, Set<T> nodes, Map<T, Set<T>> edgesOut, Map<T, Set<T>> edgesIn) {
      this.setFactories(setFactory, mapFactory);
      this.nodes = nodes;
      this.edgesOut = edgesOut;
      this.edgesIn = edgesIn;
   }

   private void setDefaultFactories() {
      this.setFactories(new HashSetFactory<>(), new HashMapFactory<>());
   }

   private void setFactories(SetFactory<T> setFactory, MapFactory<T, Set<T>> mapFactory) {
      this.setFactory = setFactory;
      this.mapFactory = mapFactory;
   }

   private void initNodesAndEdges() {
      this.nodes = this.setFactory.create();
      this.edgesOut = this.mapFactory.create();
      this.edgesIn = this.mapFactory.create();
   }

   public void addNode(T node) {
      if (!this.nodes.contains(node)) {
         this.nodes.add(node);
         this.edgesOut.put(node, this.setFactory.create());
         this.edgesIn.put(node, this.setFactory.create());
      }
   }

   public boolean containsNode(T node) {
      return this.nodes.contains(node);
   }

   public void removeNode(T node) {
      this.nodes.remove(node);
      Set<T> in = this.edgesIn.get(node);
      Set<T> out = this.edgesOut.get(node);
      this.edgesIn.remove(node);
      this.edgesOut.remove(node);

      for (T other : out) {
         Set<T> edges = this.edgesIn.get(other);
         if (edges != null) {
            edges.remove(node);
         }
      }

      for (T otherx : in) {
         Set<T> edges = this.edgesOut.get(otherx);
         if (edges != null) {
            edges.remove(node);
         }
      }
   }

   public void removeAllNodes(Collection<T> toRemove) {
      for (T node : toRemove) {
         this.removeNode(node);
      }
   }

   public void addNodes(Collection<T> nodesToAdd) {
      for (T node : nodesToAdd) {
         this.addNode(node);
      }
   }

   public void addEdge(T a, T b) {
      this.edgesOut.get(a).add(b);
      this.edgesIn.get(b).add(a);
   }

   public boolean containsEdge(T a, T b) {
      Set<T> out = this.edgesOut.get(a);
      return out.contains(b);
   }

   public void removeEdge(T a, T b) {
      this.edgesOut.get(a).remove(b);
      this.edgesIn.get(b).remove(a);
   }

   public Collection<T> getNodes() {
      return this.nodes;
   }

   public Set<T> getEdgesOut(T node) {
      return this.edgesOut.get(node);
   }

   public Set<T> getEdgesIn(T node) {
      return this.edgesIn.get(node);
   }

   public int degreeIn(T node) {
      return this.getEdgesIn(node).size();
   }

   public int degreeOut(T node) {
      return this.getEdgesOut(node).size();
   }

   public Set<Set<T>> getPartition() {
      Set<Set<T>> partition = new HashSet<>();
      Set<T> left = new HashSet<>();
      left.addAll(this.nodes);

      while (!left.isEmpty()) {
         T next = left.iterator().next();
         left.remove(next);
         Set<T> connected = new HashSet<>();
         connected.add(next);

         for (T o : this.getReachable(next)) {
            connected.add(o);
            left.remove(o);
         }

         partition.add(connected);
      }

      return partition;
   }

   public Set<T> getReachable(T node) {
      Set<T> reachable = new HashSet<>();
      Set<T> check = new HashSet<>();
      check.add(node);

      while (!check.isEmpty()) {
         T next = check.iterator().next();
         check.remove(next);
         reachable.add(next);

         for (T o : this.getEdgesOut(next)) {
            if (!reachable.contains(o) && !check.contains(o)) {
               check.add(o);
            }
         }
      }

      return reachable;
   }

   public Graph<T> reversed() {
      return new Graph<>(this.setFactory, this.mapFactory, this.nodes, this.edgesIn, this.edgesOut);
   }
}
