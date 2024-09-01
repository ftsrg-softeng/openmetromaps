package de.topobyte.adt.graph.factories;

import java.util.HashSet;
import java.util.Set;

public class HashSetFactory<T> implements SetFactory<T> {
   @Override
   public Set<T> create() {
      return new HashSet<>();
   }
}
