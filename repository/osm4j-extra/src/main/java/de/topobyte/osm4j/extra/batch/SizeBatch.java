package de.topobyte.osm4j.extra.batch;

public abstract class SizeBatch<T> extends AbstractBatch<T> {
   private int maxSize;
   private int size = 0;

   public SizeBatch(int maxSize) {
      this.maxSize = maxSize;
   }

   protected abstract int size(T var1);

   @Override
   public void clear() {
      super.clear();
      this.size = 0;
   }

   @Override
   public boolean fits(T element) {
      return this.elements.isEmpty() ? true : this.size + this.size(element) <= this.maxSize;
   }

   @Override
   public void add(T element) {
      super.add(element);
      this.size = this.size + this.size(element);
   }

   public int getSize() {
      return this.size;
   }

   @Override
   public boolean isFull() {
      return this.size == this.maxSize;
   }
}
