package de.topobyte.adt.misc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class Stack<T> implements Iterable<T> {
   private List<T> list = new ArrayList<>();

   public void push(T element) {
      this.list.add(element);
   }

   public T peek() {
      return this.list.get(this.list.size() - 1);
   }

   public T pop() {
      return this.list.remove(this.list.size() - 1);
   }

   public void clear() {
      this.list.clear();
   }

   public boolean isEmpty() {
      return this.list.isEmpty();
   }

   public int size() {
      return this.list.size();
   }

   public List<T> asList() {
      return Collections.unmodifiableList(this.list);
   }

   @Override
   public Iterator<T> iterator() {
      return this.asList().iterator();
   }
}
