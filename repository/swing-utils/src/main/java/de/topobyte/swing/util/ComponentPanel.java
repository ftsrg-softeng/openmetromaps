package de.topobyte.swing.util;

import java.awt.BorderLayout;
import java.awt.Component;
import javax.swing.JPanel;

public class ComponentPanel<T extends Component> extends JPanel {
   private static final long serialVersionUID = -4783027097378277287L;
   private T component;

   public ComponentPanel(T component) {
      super(new BorderLayout());
      this.component = component;
      this.add(component, "Center");
   }

   public T getComponent() {
      return this.component;
   }

   @Override
   public void setEnabled(boolean enabled) {
      super.setEnabled(enabled);
      this.component.setEnabled(enabled);
   }
}
