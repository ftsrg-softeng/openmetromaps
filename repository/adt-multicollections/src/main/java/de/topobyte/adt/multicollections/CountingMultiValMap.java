package de.topobyte.adt.multicollections;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CountingMultiValMap<K, L> {
   static final Logger logger = LoggerFactory.getLogger(CountingMultiValMap.class);
   Map<K, Map<L, Integer>> storage = new HashMap<K, Map<L, Integer>>();

   public void add(K key1, L key2) {
      Map<L, Integer> keyStore;
      if (this.storage.containsKey(key1)) {
         keyStore = this.storage.get(key1);
      } else {
         keyStore = (Map<L, Integer>)(new HashMap<L, Integer>());
         this.storage.put(key1, keyStore);
      }

      int count = 1;
      if (keyStore.containsKey(key2)) {
         count = keyStore.get(key2) + 1;
      }

      keyStore.put(key2, count);
   }

   public Set<L> getForKey(K key) {
      return this.storage.get(key).keySet();
   }

   public Map<L, Integer> get(K key) {
      return this.storage.get(key);
   }

   public void remove(K key1, L key2) {
      if (!this.storage.containsKey(key1)) {
         logger.debug("unable to remove 1");
      } else {
         Map<L, Integer> keyStore = this.storage.get(key1);
         if (!keyStore.containsKey(key2)) {
            logger.debug("unable to remove 2");
         } else {
            int count = keyStore.get(key2) - 1;
            if (count == 0) {
               keyStore.remove(key2);
            } else {
               keyStore.put(key2, count);
            }
         }
      }
   }

   public Collection<L> values() {
      HashSet<L> values = new HashSet<>();

      for (Entry<K, Map<L, Integer>> entry : this.storage.entrySet()) {
         Map<L, Integer> map = entry.getValue();
         Set<L> newValues = map.keySet();
         values.addAll(newValues);
      }

      return values;
   }
}
