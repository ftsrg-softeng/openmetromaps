package de.topobyte.swing.util.combobox;

import de.topobyte.swing.util.DefaultElementWrapper;
import de.topobyte.swing.util.ElementWrapper;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ComboBoxModel;
import javax.swing.event.ListDataListener;

public abstract class ListComboBoxModel<T> implements ComboBoxModel<ElementWrapper<T>> {
   private final List<ListComboBoxModel<T>.ElementWrapperImpl> list;
   private int selectedIndex = 0;
   private List<ListDataListener> listeners = new ArrayList<>();

   public abstract String toString(T var1);

   public ListComboBoxModel(List<T> list) {
      this.list = new ArrayList<>();

      for (T element : list) {
         ListComboBoxModel<T>.ElementWrapperImpl wrapper = new ListComboBoxModel.ElementWrapperImpl(element);
         this.list.add(wrapper);
      }
   }

   @Override
   public int getSize() {
      return this.list.size();
   }

   public ElementWrapper<T> getElementAt(int index) {
      return (ElementWrapper<T>)this.list.get(index);
   }

   @Override
   public void addListDataListener(ListDataListener l) {
      this.listeners.add(l);
   }

   @Override
   public void removeListDataListener(ListDataListener l) {
      this.listeners.remove(l);
   }

   @Override
   public void setSelectedItem(Object anItem) {
      for (int i = 0; i < this.list.size(); i++) {
         ListComboBoxModel<T>.ElementWrapperImpl wrapper = this.list.get(i);
         if (wrapper.equals(anItem)) {
            this.selectedIndex = i;
            break;
         }
      }
   }

   @Override
   public Object getSelectedItem() {
      return this.list.get(this.selectedIndex);
   }

   public T getSelectedElement() {
      return this.list.get(this.selectedIndex).getElement();
   }

   private class ElementWrapperImpl extends DefaultElementWrapper<T> {
      ElementWrapperImpl(T element) {
         super(element);
      }

      @Override
      public String toString() {
         return ListComboBoxModel.this.toString(this.element);
      }
   }
}
