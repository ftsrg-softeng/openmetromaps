package de.topobyte.collections.util;

import java.util.AbstractList;
import java.util.List;

class ListWithPrependedElement<T> extends AbstractList<T> {
   private final T first;
   private final List<T> list;

   ListWithPrependedElement(T first, List<T> list) {
      this.first = first;
      this.list = list;
   }

   @Override
   public int size() {
      return this.list.size() + 1;
   }

   @Override
   public T get(int index) {
      return index == 0 ? this.first : this.list.get(index - 1);
   }
}
