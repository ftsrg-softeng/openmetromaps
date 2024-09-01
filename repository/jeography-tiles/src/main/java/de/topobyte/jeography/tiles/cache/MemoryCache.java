package de.topobyte.jeography.tiles.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MemoryCache<K, V> {
   private int size;
   private Map<K, V> map = new HashMap<>();
   private List<K> keys = new ArrayList<>();

   public MemoryCache(int size) {
      this.size = size;
   }

   public synchronized int getSize() {
      return this.size;
   }

   public synchronized void setSize(int size) {
      this.size = size;

      while (this.keys.size() > size) {
         K removed = this.keys.remove(0);
         this.map.remove(removed);
      }
   }

   public synchronized void put(K key, V value) {
      if (this.map.containsKey(key)) {
         this.map.put(key, value);
      } else {
         this.map.put(key, value);
         this.keys.add(key);
         if (this.keys.size() > this.size) {
            K removed = this.keys.remove(0);
            this.map.remove(removed);
         }
      }
   }

   public synchronized V get(K key) {
      return this.map.containsKey(key) ? this.map.get(key) : null;
   }

   public synchronized V remove(K key) {
      return this.map.containsKey(key) ? this.map.remove(key) : null;
   }

   public synchronized void clear() {
      this.keys.clear();
      this.map.clear();
   }
}
