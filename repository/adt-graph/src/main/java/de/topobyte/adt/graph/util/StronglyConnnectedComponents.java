package de.topobyte.adt.graph.util;

import de.topobyte.adt.graph.Graph;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class StronglyConnnectedComponents {
   public static <T> Set<Set<T>> find(Graph<T> graph) {
      return findKosaraju(graph);
   }

   public static <T> Set<Set<T>> findKosaraju(Graph<T> graph) {
      Set<Set<T>> components = new HashSet<>();
      EnumerationBuilder<T> enumerationBuilder = new DepthFirstPostOrderEnumerationBuilder<>(graph);
      List<T> list = enumerationBuilder.buildEnumeration();
      Collections.reverse(list);
      Set<T> assigned = new HashSet<>();

      for (T element : list) {
         if (!assigned.contains(element)) {
            Set<T> current = new HashSet<>();
            components.add(current);
            assign(graph, assigned, current, element);
         }
      }

      return components;
   }

   private static <T> void assign(Graph<T> graph, Set<T> assigned, Set<T> current, T element) {
      current.add(element);
      assigned.add(element);

      for (T in : graph.getEdgesIn(element)) {
         if (!assigned.contains(in)) {
            assign(graph, assigned, current, in);
         }
      }
   }
}
