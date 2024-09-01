package de.topobyte.jeography.tiles.cache;

import de.topobyte.adt.misc.uniquedeque.UniqueLinkedList;
import java.util.HashMap;
import java.util.Map;

public class MemoryCachePlus<K, V> {
   private int size;
   private UniqueLinkedList<K> keys = new UniqueLinkedList();
   private Map<K, V> map = new HashMap<>();

   public MemoryCachePlus(int size) {
      this.size = size;
   }

   public synchronized int getSize() {
      return this.size;
   }

   public synchronized void setSize(int size) {
      this.size = size;

      while (this.keys.size() > size) {
         K removed = (K)this.keys.removeLast();
         this.map.remove(removed);
      }
   }

   public synchronized K put(K key, V value) {
      if (this.map.containsKey(key)) {
         this.map.put(key, value);
      } else {
         this.map.put(key, value);
         this.keys.addFirst(key);
         if (this.keys.size() > this.size) {
            K removed = (K)this.keys.removeLast();
            this.map.remove(removed);
            return removed;
         }
      }

      return null;
   }

   public synchronized V get(K key) {
      return this.map.containsKey(key) ? this.map.get(key) : null;
   }

   public synchronized V remove(K key) {
      return this.map.containsKey(key) ? this.map.remove(key) : null;
   }

   public synchronized void refresh(K key) {
      if (this.map.containsKey(key)) {
         this.keys.moveToFront(key);
      }
   }

   public synchronized void clear() {
      this.keys.clear();
      this.map.clear();
   }
}
