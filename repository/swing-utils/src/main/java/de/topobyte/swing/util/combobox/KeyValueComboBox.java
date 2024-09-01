package de.topobyte.swing.util.combobox;

import java.awt.Component;
import java.awt.Dimension;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.plaf.basic.BasicComboBoxRenderer;

public class KeyValueComboBox<K, V> extends JComboBox<KeyValueComboBox.Data<K, V>> {
   private static final long serialVersionUID = -1L;

   public KeyValueComboBox(K[] names, V[] values, Integer selectedValue) {
      super(buildValues(names, values));
      this.setRenderer(new KeyValueComboBox.Renderer());
      this.setEditable(false);
      this.setSelectedIndex(-1);

      for (int i = 0; i < values.length; i++) {
         if (values[i] == selectedValue) {
            this.setSelectedIndex(i);
            break;
         }
      }
   }

   public void setMinPreferredWidth(int minWidth) {
      Dimension ps = this.getPreferredSize();
      if (ps.width < minWidth) {
         this.setPreferredSize(new Dimension(minWidth, ps.height));
      }
   }

   private static <K, V> KeyValueComboBox.Data<K, V>[] buildValues(K[] names, V[] values) {
      KeyValueComboBox.Data<K, V>[] data = new KeyValueComboBox.Data[names.length];

      for (int i = 0; i < names.length; i++) {
         data[i] = new KeyValueComboBox.Data<>(names[i], values[i]);
      }

      return data;
   }

   public V getSelectedValue() {
      int index = this.getSelectedIndex();
      if (index < 0) {
         return null;
      } else {
         KeyValueComboBox.Data<K, V> data = this.getItemAt(index);
         return data == null ? null : data.value;
      }
   }

   public static class Data<K, V> {
      K name;
      V value;

      public Data(K name, V value) {
         this.name = name;
         this.value = value;
      }

      public K getName() {
         return this.name;
      }

      public V getValue() {
         return this.value;
      }
   }

   private class Renderer extends BasicComboBoxRenderer {
      private static final long serialVersionUID = 1L;

      private Renderer() {
      }

      @Override
      public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
         super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
         if (value != null) {
            KeyValueComboBox.Data<K, V> item = (KeyValueComboBox.Data<K, V>)value;
            this.setText(item.name.toString());
         } else {
            this.setText("default");
         }

         return this;
      }
   }
}
