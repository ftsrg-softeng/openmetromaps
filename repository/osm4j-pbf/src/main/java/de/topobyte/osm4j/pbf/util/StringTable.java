package de.topobyte.osm4j.pbf.util;

import com.google.protobuf.ByteString;
import com.slimjars.dist.gnu.trove.map.TObjectIntMap;
import com.slimjars.dist.gnu.trove.map.hash.TObjectIntHashMap;
import de.topobyte.osm4j.pbf.protobuf.Osmformat;
import java.util.Arrays;
import java.util.Comparator;

public class StringTable {
   private TObjectIntMap<String> counts = new TObjectIntHashMap(100);
   private TObjectIntMap<String> stringMap;
   private String[] set;

   public void incr(String s) {
      if (this.counts.containsKey(s)) {
         this.counts.put(s, this.counts.get(s) + 1);
      } else {
         this.counts.put(s, 1);
      }
   }

   public int getIndex(String s) {
      return this.stringMap.get(s);
   }

   public void finish() {
      Comparator<String> comparator = new Comparator<String>() {
         public int compare(String s1, String s2) {
            return StringTable.this.counts.get(s2) - StringTable.this.counts.get(s1);
         }
      };
      this.set = this.counts.keySet().toArray(new String[0]);
      if (this.set.length > 0) {
         Arrays.sort(this.set, comparator);
         Arrays.sort((Object[])this.set, Math.min(128, this.set.length - 1), Math.min(16384, this.set.length - 1));
         Arrays.sort(this.set, Math.min(16384, this.set.length - 1), Math.min(2097152, this.set.length - 1), comparator);
      }

      this.stringMap = new TObjectIntHashMap(2 * this.set.length);

      for (int i = 0; i < this.set.length; i++) {
         this.stringMap.put(this.set[i], new Integer(i + 1));
      }

      this.counts = null;
   }

   public void clear() {
      this.counts = new TObjectIntHashMap(100);
      this.stringMap = null;
      this.set = null;
   }

   public Osmformat.StringTable.Builder serialize() {
      Osmformat.StringTable.Builder builder = Osmformat.StringTable.newBuilder();
      builder.addS(ByteString.copyFromUtf8(""));

      for (int i = 0; i < this.set.length; i++) {
         builder.addS(ByteString.copyFromUtf8(this.set[i]));
      }

      return builder;
   }
}
