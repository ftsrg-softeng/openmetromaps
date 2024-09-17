package de.topobyte.osm4j.tbo.data;

public class StringPoolEntry implements Comparable<StringPoolEntry> {
   public String key;
   public int value;

   public StringPoolEntry(String key, int value) {
      this.key = key;
      this.value = value;
   }

   public int compareTo(StringPoolEntry o) {
      return o.value - this.value;
   }

   @Override
   public String toString() {
      return String.format("%d: %s", this.value, this.key);
   }
}
