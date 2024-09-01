package de.topobyte.adt.multicollections;

import java.util.Collection;
import java.util.Set;

public interface MultiSet<T> extends Iterable<T> {
   void add(T var1);

   void add(T var1, int var2);

   void addAll(Collection<T> var1);

   void addAll(Collection<T> var1, int var2);

   void remove(T var1);

   void removeAll(Collection<? extends T> var1);

   void removeN(T var1, int var2);

   void removeAllN(Collection<? extends T> var1, int var2);

   void removeOccurences(T var1);

   void removeAllOccurences(Collection<? extends T> var1);

   int occurences(T var1);

   boolean contains(T var1);

   Set<T> keySet();
}
