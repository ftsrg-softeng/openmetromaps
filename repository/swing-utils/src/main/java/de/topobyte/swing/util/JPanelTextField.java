package de.topobyte.swing.util;

import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class JPanelTextField extends JPanel {
   private static final long serialVersionUID = -6427281494288257975L;
   private JTextField textField;

   public JPanelTextField() {
      this("");
   }

   public JPanelTextField(String text) {
      this(text, "Center");
   }

   public JPanelTextField(String text, String constraint) {
      this.setLayout(new BorderLayout());
      this.textField = new JTextField(text);
      this.add(this.textField, constraint);
   }

   public JTextField getTextField() {
      return this.textField;
   }

   public String getText() {
      return this.textField.getText();
   }

   public void setText(String text) {
      this.textField.setText(text);
   }
}
