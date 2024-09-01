package de.topobyte.osm4j.extra.io.ra;

import java.util.LinkedHashMap;
import java.util.Map.Entry;

public class LruHashMap<K, V> extends LinkedHashMap<K, V> {
   private static final long serialVersionUID = -8433087029941897864L;
   private final int size;

   public LruHashMap(int size) {
      super(size + 1, 0.75F, true);
      this.size = size;
   }

   @Override
   protected boolean removeEldestEntry(Entry<K, V> eldest) {
      return this.size() > this.size;
   }
}
