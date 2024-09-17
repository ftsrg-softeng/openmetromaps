package de.topobyte.collections.util;

import java.util.AbstractList;
import java.util.List;

class ListWithAppendedElement<T> extends AbstractList<T> {
   private final T last;
   private final List<T> list;

   ListWithAppendedElement(List<T> list, T last) {
      this.last = last;
      this.list = list;
   }

   @Override
   public int size() {
      return this.list.size() + 1;
   }

   @Override
   public T get(int index) {
      return index == this.list.size() ? this.last : this.list.get(index);
   }
}
