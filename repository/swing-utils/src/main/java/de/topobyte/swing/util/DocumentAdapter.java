package de.topobyte.swing.util;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public abstract class DocumentAdapter implements DocumentListener {
   public abstract void update(DocumentEvent var1);

   @Override
   public void changedUpdate(DocumentEvent e) {
      this.update(e);
   }

   @Override
   public void insertUpdate(DocumentEvent e) {
      this.update(e);
   }

   @Override
   public void removeUpdate(DocumentEvent e) {
      this.update(e);
   }
}
