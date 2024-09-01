package de.topobyte.swing.util.dnd.panel;

import javax.swing.JComponent;
import javax.swing.JLabel;

public abstract class StringDndPanel<T> extends DndPanel<T> {
   private static final long serialVersionUID = -3746515359425504010L;
   private JLabel label;

   public StringDndPanel(T data, boolean isSource, boolean isDestination) {
      super(data, isSource, isDestination);
   }

   @Override
   public JComponent getComponent() {
      return this.label;
   }

   @Override
   public void setup(T data) {
      this.setData(data);
      if (this.label == null) {
         this.label = new JLabel();
      }

      this.label.setText(this.getLabelString(data));
   }

   public abstract String getLabelString(T var1);
}
