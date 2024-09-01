package de.topobyte.swing.util;

import java.awt.GridLayout;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JPanel;

public class ButtonPane extends JPanel {
   private static final long serialVersionUID = -3827513568340197064L;
   private JPanel panel = new JPanel();

   public ButtonPane(List<JButton> buttons) {
      this.add(this.panel);
      this.panel.setLayout(new GridLayout());

      for (JButton button : buttons) {
         this.panel.add(button);
      }
   }
}
