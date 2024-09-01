package de.topobyte.adt.multicollections;

import java.util.Collection;

public interface MultiValMap<K, V> {
   void put(K var1, V var2);

   void put(K var1, Collection<V> var2);

   void remove(K var1, V var2);

   void remove(K var1, Collection<V> var2);

   void removeAll(K var1);

   Collection<V> get(K var1);

   boolean containsKey(K var1);

   Collection<K> keys();

   int size();
}
