package de.topobyte.adt.multicollections;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class HashMultiValMap<K, V> implements MultiValMap<K, V>, Serializable {
   private static final long serialVersionUID = -6028202660344028763L;
   Map<K, Set<V>> data = new HashMap<>();

   @Override
   public boolean containsKey(K key) {
      return this.data.containsKey(key);
   }

   @Override
   public Collection<V> get(K key) {
      return this.data.get(key);
   }

   @Override
   public void put(K key, V value) {
      Set<V> values;
      if (this.data.containsKey(key)) {
         values = this.data.get(key);
      } else {
         values = new HashSet<>();
         this.data.put(key, values);
      }

      values.add(value);
   }

   @Override
   public void put(K key, Collection<V> vs) {
      Set<V> values;
      if (this.data.containsKey(key)) {
         values = this.data.get(key);
      } else {
         values = new HashSet<>();
         this.data.put(key, values);
      }

      values.addAll(vs);
   }

   @Override
   public void remove(K key, V value) {
      if (this.data.containsKey(key)) {
         Set<V> values = this.data.get(key);
         values.remove(value);
         if (values.size() == 0) {
            this.data.remove(key);
         }
      }
   }

   @Override
   public void remove(K key, Collection<V> vs) {
      if (this.data.containsKey(key)) {
         Set<V> values = this.data.get(key);
         values.removeAll(vs);
         if (values.size() == 0) {
            this.data.remove(key);
         }
      }
   }

   @Override
   public void removeAll(K key) {
      if (this.data.containsKey(key)) {
         Set<V> values = this.data.get(key);
         values.clear();
         this.data.remove(key);
      }
   }

   @Override
   public Collection<K> keys() {
      return this.data.keySet();
   }

   @Override
   public int size() {
      return this.data.size();
   }
}
