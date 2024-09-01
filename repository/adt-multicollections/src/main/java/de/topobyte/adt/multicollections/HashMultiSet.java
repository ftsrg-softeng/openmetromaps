package de.topobyte.adt.multicollections;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class HashMultiSet<T> implements MultiSet<T> {
   Map<T, Integer> map = new HashMap<>();

   public HashMultiSet() {
   }

   public HashMultiSet(MultiSet<T> prototype) {
      for (T object : prototype.keySet()) {
         this.map.put(object, prototype.occurences(object));
      }
   }

   @Override
   public boolean contains(T key) {
      return this.occurences(key) > 0;
   }

   @Override
   public int occurences(T key) {
      return !this.map.containsKey(key) ? 0 : this.map.get(key);
   }

   @Override
   public void add(T key) {
      if (!this.map.containsKey(key)) {
         this.map.put(key, 1);
      } else {
         this.map.put(key, this.map.get(key) + 1);
      }
   }

   @Override
   public void add(T key, int howOften) {
      if (!this.map.containsKey(key)) {
         this.map.put(key, howOften);
      } else {
         this.map.put(key, this.map.get(key) + howOften);
      }
   }

   @Override
   public void addAll(Collection<T> keys) {
      for (T key : keys) {
         this.add(key);
      }
   }

   @Override
   public void addAll(Collection<T> keys, int howOften) {
      for (T key : keys) {
         this.add(key, howOften);
      }
   }

   @Override
   public void remove(T key) {
      if (this.map.containsKey(key)) {
         int count = this.map.get(key) - 1;
         if (count == 0) {
            this.map.remove(key);
         } else {
            this.map.put(key, count);
         }
      }
   }

   @Override
   public void removeAll(Collection<? extends T> c) {
      for (T key : c) {
         this.remove(key);
      }
   }

   @Override
   public void removeOccurences(T key) {
      if (this.map.containsKey(key)) {
         this.map.remove(key);
      }
   }

   @Override
   public void removeAllOccurences(Collection<? extends T> c) {
      for (T key : c) {
         this.removeOccurences(key);
      }
   }

   @Override
   public void removeN(T key, int n) {
      if (this.map.containsKey(key)) {
         int count = this.map.get(key) - n;
         if (count <= 0) {
            this.map.remove(key);
         } else {
            this.map.put(key, count);
         }
      }
   }

   @Override
   public void removeAllN(Collection<? extends T> c, int n) {
      for (T key : c) {
         this.removeN(key, n);
      }
   }

   @Override
   public Set<T> keySet() {
      return this.map.keySet();
   }

   @Override
   public Iterator<T> iterator() {
      return new HashMultiSet.HashMultiSetIterator(this);
   }

   private class HashMultiSetIterator<K> implements Iterator<K> {
      private HashMultiSet<K> hms;
      private int leftForThis = 0;
      private Set<K> keys;
      private K current;

      public HashMultiSetIterator(HashMultiSet<K> hms) {
         this.hms = hms;
         this.keys = new HashSet<>();

         for (K key : hms.keySet()) {
            this.keys.add(key);
         }
      }

      @Override
      public boolean hasNext() {
         return this.leftForThis == 0 ? !this.keys.isEmpty() : true;
      }

      @Override
      public K next() {
         if (this.leftForThis == 0) {
            this.current = this.keys.iterator().next();
            this.leftForThis = this.hms.occurences(this.current);
            this.keys.remove(this.current);
         }

         this.leftForThis--;
         return this.current;
      }

      @Override
      public void remove() {
         throw new UnsupportedOperationException("HashMultiSet iterators don't provide removal method");
      }
   }
}
