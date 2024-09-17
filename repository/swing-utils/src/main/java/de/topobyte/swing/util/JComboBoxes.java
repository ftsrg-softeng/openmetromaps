package de.topobyte.swing.util;

import javax.swing.JComboBox;

public class JComboBoxes {
   public static <E> E getSelectedItem(JComboBox<E> comboBox) {
      int index = comboBox.getSelectedIndex();
      return comboBox.getItemAt(index);
   }
}
