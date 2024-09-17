package de.topobyte.osm4j.tbo.data;

import com.slimjars.dist.gnu.trove.map.TMap;
import com.slimjars.dist.gnu.trove.map.hash.THashMap;
import com.slimjars.dist.gnu.trove.procedure.TObjectProcedure;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class StringPoolBuilder {
   private TMap<String, StringPoolEntry> map = new THashMap();

   public void add(String string) {
      StringPoolEntry entry = (StringPoolEntry)this.map.get(string);
      if (entry == null) {
         this.map.put(string, new StringPoolEntry(string, 1));
      } else {
         entry.value++;
      }
   }

   public StringPool buildStringPool() {
      int size = this.map.size();
      final List<StringPoolEntry> list = new ArrayList<>(size);
      this.map.forEachValue(new TObjectProcedure<StringPoolEntry>() {
         public boolean execute(StringPoolEntry entry) {
            list.add(entry);
            return true;
         }
      });
      Collections.sort(list);
      List<String> strings = new ArrayList<>(size);
      int i = 0;

      for (StringPoolEntry entry : list) {
         strings.add(entry.key);
         entry.value = i++;
      }

      return new StringPool(strings, this.map);
   }
}
