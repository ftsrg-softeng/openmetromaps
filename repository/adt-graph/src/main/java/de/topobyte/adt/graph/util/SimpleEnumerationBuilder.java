package de.topobyte.adt.graph.util;

import de.topobyte.adt.graph.Graph;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SimpleEnumerationBuilder<T> implements EnumerationBuilder<T> {
   Graph<T> graph;
   List<T> enumeration = new ArrayList<>();
   Set<T> available = new HashSet<>();

   @Override
   public List<T> buildEnumeration() {
      this.build();
      return this.getEnumeration();
   }

   public SimpleEnumerationBuilder(Graph<T> graph) {
      this.graph = graph;
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
         Set<T> neighbours = new HashSet<>();
         this.addNeighbours(neighbours, n);

         while (!neighbours.isEmpty()) {
            T next = this.chooseNext(neighbours);
            neighbours.remove(next);
            this.enumerate(next);
            this.addNeighbours(neighbours, next);
         }
      }
   }

   private T chooseNext(Set<T> neighbours) {
      return neighbours.iterator().next();
   }

   private void addNeighbours(Set<T> neighbours, T n) {
      for (T neighbour : this.graph.getEdgesOut(n)) {
         if (this.available.contains(neighbour) && !neighbours.contains(neighbour)) {
            neighbours.add(neighbour);
         }
      }
   }

   private void enumerate(T n) {
      this.available.remove(n);
      this.enumeration.add(n);
   }
}
