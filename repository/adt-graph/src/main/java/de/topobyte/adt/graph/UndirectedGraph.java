package de.topobyte.adt.graph;

import de.topobyte.adt.graph.factories.MapFactory;
import de.topobyte.adt.graph.factories.SetFactory;
import java.util.Set;

public class UndirectedGraph<T> extends Graph<T> {
   public UndirectedGraph() {
   }

   public UndirectedGraph(SetFactory<T> setFactory, MapFactory<T, Set<T>> mapFactory) {
      super(setFactory, mapFactory);
   }

   @Override
   public void addEdge(T a, T b) {
      super.addEdge(a, b);
      super.addEdge(b, a);
   }

   @Override
   public void removeEdge(T a, T b) {
      super.removeEdge(a, b);
      super.removeEdge(b, a);
   }
}
