package de.topobyte.adt.graph.factories;

import java.util.Map;

public interface MapFactory<K, V> {
   Map<K, V> create();
}
