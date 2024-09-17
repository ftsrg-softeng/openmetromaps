package de.topobyte.osm4j.extra.batch;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class BatchBuilder<T> {
   private Batch<T> batch;
   private List<List<T>> results = new ArrayList<>();

   public BatchBuilder(Batch<T> batch) {
      this.batch = batch;
   }

   public void add(T element) {
      if (this.batch.fits(element)) {
         this.batch.add(element);
      } else {
         List<T> elements = new ArrayList<>(this.batch.getElements());
         this.results.add(elements);
         this.batch.clear();
         this.batch.add(element);
      }
   }

   public void addAll(Collection<T> elements) {
      for (T element : elements) {
         this.add(element);
      }
   }

   public void finish() {
      if (!this.batch.getElements().isEmpty()) {
         List<T> elements = new ArrayList<>(this.batch.getElements());
         this.results.add(elements);
         this.batch.clear();
      }
   }

   public List<List<T>> getResults() {
      return this.results;
   }
}
