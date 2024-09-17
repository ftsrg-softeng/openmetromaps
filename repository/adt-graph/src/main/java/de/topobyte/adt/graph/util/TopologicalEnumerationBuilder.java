package de.topobyte.adt.graph.util;

import de.topobyte.adt.graph.Graph;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TopologicalEnumerationBuilder<T> implements EnumerationBuilder<T> {
   Graph<T> graph;
   Set<T> enumerated = new HashSet<>();
   List<T> enumeration = new ArrayList<>();
   Set<T> available = new HashSet<>();
   private Set<T> mark = new HashSet<>();

   @Override
   public List<T> buildEnumeration() {
      this.build();
      return this.getEnumeration();
   }

   public TopologicalEnumerationBuilder(Graph<T> graph) {
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
         this.available.remove(n);
         this.visit(n);
      }

      Collections.reverse(this.enumeration);
   }

   private void visit(T n) {
      if (this.mark.contains(n)) {
         throw new RuntimeException("This graph is not acyclic");
      } else if (!this.enumerated.contains(n)) {
         this.mark.add(n);

         for (T neighbor : this.graph.getEdgesOut(n)) {
            this.visit(neighbor);
         }

         this.enumeration.add(n);
         this.enumerated.add(n);
         this.mark.remove(n);
      }
   }
}
