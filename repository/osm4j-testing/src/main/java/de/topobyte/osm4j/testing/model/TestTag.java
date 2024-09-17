package de.topobyte.osm4j.testing.model;

import de.topobyte.osm4j.core.model.iface.OsmTag;

public class TestTag implements OsmTag {
   private final String key;
   private final String value;

   public TestTag(String key, String value) {
      this.key = key;
      this.value = value;
   }

   public String getKey() {
      return this.key;
   }

   public String getValue() {
      return this.value;
   }

   @Override
   public String toString() {
      return String.format("%s=%s", this.key, this.value);
   }
}
