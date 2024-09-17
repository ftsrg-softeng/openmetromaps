package de.topobyte.swing.util.combobox;

import javax.swing.AbstractListModel;
import javax.swing.ComboBoxModel;

public class BooleanComboBoxModel extends AbstractListModel<Boolean> implements ComboBoxModel<Boolean> {
   private static final long serialVersionUID = 1380002002136339388L;
   private boolean selected = false;

   public BooleanComboBoxModel(boolean selected) {
      this.selected = selected;
   }

   @Override
   public int getSize() {
      return 2;
   }

   public Boolean getElementAt(int index) {
      return index == 0;
   }

   @Override
   public void setSelectedItem(Object anItem) {
      if (anItem instanceof Boolean) {
         boolean s = (Boolean)anItem;
         this.selected = s;
      }
   }

   @Override
   public Object getSelectedItem() {
      return this.selected;
   }
}
