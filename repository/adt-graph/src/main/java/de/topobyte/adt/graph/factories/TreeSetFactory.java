package de.topobyte.adt.graph.factories;

import java.util.Set;
import java.util.TreeSet;

public class TreeSetFactory<T> implements SetFactory<T> {
   @Override
   public Set<T> create() {
      return new TreeSet<>();
   }
}
