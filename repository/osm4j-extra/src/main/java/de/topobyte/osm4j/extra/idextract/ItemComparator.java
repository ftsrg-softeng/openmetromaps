package de.topobyte.osm4j.extra.idextract;

import java.util.Comparator;

class ItemComparator implements Comparator<Item> {
   public int compare(Item item1, Item item2) {
      return Long.compare(item1.getNext(), item2.getNext());
   }
}
