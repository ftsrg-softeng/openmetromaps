package de.topobyte.osm4j.tbo.data;

import com.slimjars.dist.gnu.trove.map.TMap;
import java.util.List;

public class StringPool {
   private final List<String> pool;
   private final TMap<String, StringPoolEntry> map;

   public StringPool(List<String> pool, TMap<String, StringPoolEntry> map) {
      this.pool = pool;
      this.map = map;
   }

   public int size() {
      return this.pool.size();
   }

   public String getString(int id) {
      return this.pool.get(id);
   }

   public int getId(String string) {
      StringPoolEntry entry = (StringPoolEntry)this.map.get(string);
      return entry.value;
   }
}
