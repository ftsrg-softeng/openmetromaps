package de.topobyte.osm4j.extra.batch;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractBatch<T> implements Batch<T> {
   protected List<T> elements = new ArrayList<>();

   @Override
   public void clear() {
      this.elements.clear();
   }

   @Override
   public void add(T element) {
      this.elements.add(element);
   }

   @Override
   public List<T> getElements() {
      return this.elements;
   }
}
