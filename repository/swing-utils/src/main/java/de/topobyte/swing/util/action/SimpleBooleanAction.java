package de.topobyte.swing.util.action;

import java.awt.event.ActionEvent;
import javax.swing.Icon;

public abstract class SimpleBooleanAction extends SimpleAction {
   private static final long serialVersionUID = 7648212308884524179L;

   public abstract boolean getState();

   public abstract void toggleState();

   public SimpleBooleanAction(String name, String description) {
      super(name, description);
   }

   public SimpleBooleanAction(String name, String description, Icon icon) {
      super(name, description, icon);
   }

   public SimpleBooleanAction(String name, String description, String resourceIcon) {
      super(name, description, resourceIcon);
   }

   @Override
   public Object getValue(String key) {
      return key.equals("SwingSelectedKey") ? this.getState() : super.getValue(key);
   }

   @Override
   public void actionPerformed(ActionEvent e) {
      this.toggleState();
      this.firePropertyChange("SwingSelectedKey", null, null);
   }
}
