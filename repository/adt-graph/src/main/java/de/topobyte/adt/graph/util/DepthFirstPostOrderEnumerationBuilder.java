package de.topobyte.adt.graph.util;

import de.topobyte.adt.graph.Graph;
import de.topobyte.adt.graph.factories.HashSetFactory;
import de.topobyte.adt.graph.factories.SetFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class DepthFirstPostOrderEnumerationBuilder<T> implements EnumerationBuilder<T> {
   SetFactory<T> setFactory;
   Graph<T> graph;
   List<T> enumeration;
   Set<T> available;

   public DepthFirstPostOrderEnumerationBuilder(Graph<T> graph) {
      this(graph, new HashSetFactory<>());
   }

   public DepthFirstPostOrderEnumerationBuilder(Graph<T> graph, SetFactory<T> setFactory) {
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
         this.visit(this.graph, n);
      }
   }

   private void visit(Graph<T> graph, T n) {
      this.available.remove(n);

      for (T out : graph.getEdgesOut(n)) {
         if (this.available.contains(out)) {
            this.visit(graph, out);
         }
      }

      this.enumeration.add(n);
   }
}
