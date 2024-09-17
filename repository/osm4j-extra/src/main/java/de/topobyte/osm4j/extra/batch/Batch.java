package de.topobyte.osm4j.extra.batch;

import java.util.List;

public interface Batch<T> {
   void clear();

   boolean fits(T var1);

   void add(T var1);

   List<T> getElements();

   boolean isFull();
}
