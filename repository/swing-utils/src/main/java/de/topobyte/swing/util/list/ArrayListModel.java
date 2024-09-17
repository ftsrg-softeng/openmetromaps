package de.topobyte.swing.util.list;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.swing.AbstractListModel;

public class ArrayListModel<T> extends AbstractListModel<T> {
   private static final long serialVersionUID = -6068825149839399789L;
   private List<T> elements = new ArrayList<>();

   public void add(T element, int index) {
      this.elements.add(index, element);
      this.fireIntervalAdded(this, index, index);
   }

   public void addAll(Collection<? extends T> insElements, int index) {
      int i = index;

      for (T element : insElements) {
         this.elements.add(i++, element);
      }

      this.fireContentsChanged(this, 0, 0);
   }

   public void remove(int index) {
      this.elements.remove(index);
      this.fireIntervalRemoved(this, index, index);
   }

   @Override
   public int getSize() {
      return this.elements.size();
   }

   @Override
   public T getElementAt(int index) {
      return this.elements.get(index);
   }
}
