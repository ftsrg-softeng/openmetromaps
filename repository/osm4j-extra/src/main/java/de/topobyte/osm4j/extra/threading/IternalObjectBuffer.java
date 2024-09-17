package de.topobyte.osm4j.extra.threading;

class IternalObjectBuffer<T> {
   private int position = 0;
   private int size = 0;
   private Object[] elements;

   public IternalObjectBuffer(int n) {
      this.elements = new Object[n];
   }

   public void add(T e) {
      this.elements[this.size++] = e;
   }

   public boolean isEmpty() {
      return this.position == this.size;
   }

   public int size() {
      return this.size - this.position;
   }

   public T remove() {
      T element = (T)this.elements[this.position];
      this.elements[this.position++] = null;
      return element;
   }

   public void clear() {
      this.position = 0;
      this.size = 0;
   }
}
