package de.topobyte.adt.graph.util;

import de.topobyte.adt.graph.Graph;
import de.topobyte.adt.graph.factories.SetFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class DepthFirstEnumerationBuilder<T> extends AbstractEnumerationBuilder<T> {
   public DepthFirstEnumerationBuilder(Graph<T> graph) {
      super(graph);
   }

   public DepthFirstEnumerationBuilder(Graph<T> graph, SetFactory<T> setFactory) {
      super(graph, setFactory);
   }

   @Override
   protected T chooseNext(List<T> neighbours, Set<T> neighbourSet) {
      T next = neighbours.remove(neighbours.size() - 1);
      neighbourSet.remove(next);
      return next;
   }

   @Override
   protected List<T> createList() {
      return new ArrayList<>();
   }
}
