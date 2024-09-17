package de.topobyte.osm4j.tbo.batching;

public class ElementCountBatchBuilder<T> implements BatchBuilder<T> {
   private int maxElements;
   private int counter = 0;

   public ElementCountBatchBuilder(int maxElements) {
      this.maxElements = maxElements;
   }

   @Override
   public void add(T element) {
      this.counter++;
   }

   @Override
   public boolean full() {
      return this.counter >= this.maxElements;
   }

   @Override
   public boolean fits(T element) {
      return this.counter < this.maxElements;
   }

   @Override
   public void clear() {
      this.counter = 0;
   }

   @Override
   public int bufferSizeHint() {
      return this.maxElements;
   }
}
