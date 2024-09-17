package de.topobyte.adt.graph.factories;

import java.util.HashMap;
import java.util.Map;

public class HashMapFactory<K, V> implements MapFactory<K, V> {
   @Override
   public Map<K, V> create() {
      return new HashMap<>();
   }
}
