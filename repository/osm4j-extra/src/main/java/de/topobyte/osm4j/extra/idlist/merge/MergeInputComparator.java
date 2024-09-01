package de.topobyte.osm4j.extra.idlist.merge;

import java.util.Comparator;

class MergeInputComparator implements Comparator<MergeInput> {
   public int compare(MergeInput input1, MergeInput input2) {
      return Long.compare(input1.getNext(), input2.getNext());
   }
}
