package de.topobyte.adt.graph.util;

import de.topobyte.adt.graph.Graph;
import de.topobyte.adt.graph.factories.SetFactory;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class BreadthFirstEnumerationBuilder<T> extends AbstractEnumerationBuilder<T> {
   public BreadthFirstEnumerationBuilder(Graph<T> graph) {
      super(graph);
   }

   public BreadthFirstEnumerationBuilder(Graph<T> graph, SetFactory<T> setFactory) {
      super(graph, setFactory);
   }

   @Override
   protected T chooseNext(List<T> neighbours, Set<T> neighbourSet) {
      T next = neighbours.remove(0);
      neighbourSet.remove(next);
      return next;
   }

   @Override
   protected List<T> createList() {
      return new LinkedList<>();
   }
}
