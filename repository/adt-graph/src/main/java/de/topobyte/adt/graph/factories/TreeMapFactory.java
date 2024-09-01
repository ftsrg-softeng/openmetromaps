package de.topobyte.adt.graph.factories;

import java.util.Map;
import java.util.TreeMap;

public class TreeMapFactory<K, V> implements MapFactory<K, V> {
   @Override
   public Map<K, V> create() {
      return new TreeMap<>();
   }
}
