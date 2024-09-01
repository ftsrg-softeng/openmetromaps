package de.topobyte.adt.graph.util;

import de.topobyte.adt.graph.Graph;
import de.topobyte.adt.graph.factories.HashSetFactory;
import de.topobyte.adt.graph.factories.SetFactory;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

abstract class AbstractEnumerationBuilder<T> implements EnumerationBuilder<T> {
   SetFactory<T> setFactory;
   Graph<T> graph;
   List<T> enumeration;
   Set<T> available;
   protected boolean invertOrderOfNewNeighbors = false;

   public AbstractEnumerationBuilder(Graph<T> graph) {
      this(graph, new HashSetFactory<>());
   }

   public AbstractEnumerationBuilder(Graph<T> graph, SetFactory<T> setFactory) {
      this.graph = graph;
      this.setFactory = setFactory;
      this.init();
   }

   private void init() {
      this.enumeration = new ArrayList<>();
      this.available = this.setFactory.create();
   }

   @Override
   public List<T> buildEnumeration() {
      this.build();
      return this.getEnumeration();
   }

   @Override
   public List<T> getEnumeration() {
      return this.enumeration;
   }

   private void build() {
      for (T node : this.graph.getNodes()) {
         this.available.add(node);
      }

      while (!this.available.isEmpty()) {
         T n = this.available.iterator().next();
         this.enumerate(n);
         List<T> neighbours = this.createList();
         Set<T> neighbourSet = this.setFactory.create();
         this.addNeighbours(neighbours, neighbourSet, n);

         while (!neighbourSet.isEmpty()) {
            T next = this.chooseNext(neighbours, neighbourSet);
            this.enumerate(next);
            this.addNeighbours(neighbours, neighbourSet, next);
         }
      }
   }

   protected abstract List<T> createList();

   protected abstract T chooseNext(List<T> var1, Set<T> var2);

   private void addNeighbours(List<T> neighbours, Set<T> neighbourSet, T n) {
      if (!this.invertOrderOfNewNeighbors) {
         this.addNeighboursNormal(neighbours, neighbourSet, n);
      } else {
         this.addNeighboursReverse(neighbours, neighbourSet, n);
      }
   }

   private void addNeighboursNormal(List<T> neighbours, Set<T> neighbourSet, T n) {
      for (T neighbour : this.graph.getEdgesOut(n)) {
         if (this.available.contains(neighbour) && !neighbourSet.contains(neighbour)) {
            neighbours.add(neighbour);
            neighbourSet.add(neighbour);
         }
      }
   }

   private void addNeighboursReverse(List<T> neighbours, Set<T> neighbourSet, T n) {
      List<T> list = new ArrayList<>(this.graph.getEdgesOut(n));
      Collections.reverse(list);

      for (T neighbour : list) {
         if (this.available.contains(neighbour) && !neighbourSet.contains(neighbour)) {
            neighbours.add(neighbour);
            neighbourSet.add(neighbour);
         }
      }
   }

   private void enumerate(T n) {
      this.available.remove(n);
      this.enumeration.add(n);
   }
}
