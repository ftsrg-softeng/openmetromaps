package de.topobyte.adt.misc.uniquedeque;

class ListEntry<T> {
   private T element;
   private ListEntry<T> prev = null;
   private ListEntry<T> next = null;

   public ListEntry(T element) {
      this.element = element;
   }

   public T getElement() {
      return this.element;
   }

   public ListEntry<T> getNext() {
      return this.next;
   }

   public ListEntry<T> getPrevious() {
      return this.prev;
   }

   public void setElement(T element) {
      this.element = element;
   }

   public void setNext(ListEntry<T> next) {
      this.next = next;
   }

   public void setPrevious(ListEntry<T> prev) {
      this.prev = prev;
   }
}
