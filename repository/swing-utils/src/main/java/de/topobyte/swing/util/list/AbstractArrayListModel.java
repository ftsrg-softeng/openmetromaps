package de.topobyte.swing.util.list;

import de.topobyte.swing.util.DefaultElementWrapper;
import de.topobyte.swing.util.ElementWrapper;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.swing.AbstractListModel;

public abstract class AbstractArrayListModel<T> extends AbstractListModel<ElementWrapper<T>> {
   private static final long serialVersionUID = -6068825149839399789L;
   private List<AbstractArrayListModel<T>.ElementWrapperImpl> elements = new ArrayList<>();

   public abstract String getValue(T var1);

   public void add(T element, int index) {
      this.elements.add(index, new AbstractArrayListModel.ElementWrapperImpl(element));
      this.fireIntervalAdded(this, index, index);
   }

   public void remove(int index) {
      this.elements.remove(index);
      this.fireIntervalRemoved(this, index, index);
   }

   public void addAll(Collection<T> insElements, int index) {
      int i = index;

      for (T element : insElements) {
         this.elements.add(i++, new AbstractArrayListModel.ElementWrapperImpl(element));
      }

      this.fireContentsChanged(this, 0, 0);
   }

   @Override
   public int getSize() {
      return this.elements.size();
   }

   public ElementWrapper<T> getElementAt(int index) {
      return (ElementWrapper<T>)this.elements.get(index);
   }

   public T getRealElementAt(int index) {
      return this.getElementAt(index).getElement();
   }

   private class ElementWrapperImpl extends DefaultElementWrapper<T> {
      public ElementWrapperImpl(T element) {
         super(element);
      }

      @Override
      public String toString() {
         return AbstractArrayListModel.this.getValue(this.element);
      }
   }
}
