package de.topobyte.osm4j.core.model.impl;

import de.topobyte.osm4j.core.model.iface.OsmTag;

public class Tag implements OsmTag {
   private final String key;
   private final String value;

   public Tag(String key, String value) {
      this.key = key;
      this.value = value;
   }

   @Override
   public String getKey() {
      return this.key;
   }

   @Override
   public String getValue() {
      return this.value;
   }

   @Override
   public String toString() {
      return String.format("%s=%s", this.key, this.value);
   }
}
