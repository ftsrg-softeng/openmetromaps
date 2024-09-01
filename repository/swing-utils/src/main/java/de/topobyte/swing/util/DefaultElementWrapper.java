package de.topobyte.swing.util;

public class DefaultElementWrapper<T> implements ElementWrapper<T> {
   protected T element;

   public DefaultElementWrapper(T element) {
      this.element = element;
   }

   @Override
   public T getElement() {
      return this.element;
   }

   @Override
   public String toString() {
      return this.element.toString();
   }

   @Override
   public int hashCode() {
      return this.element.hashCode();
   }

   @Override
   public boolean equals(Object o) {
      if (!this.getClass().getName().equals(o.getClass().getName())) {
         return false;
      } else {
         DefaultElementWrapper<T> other = (DefaultElementWrapper<T>)o;
         return this.element.equals(other.element);
      }
   }
}
