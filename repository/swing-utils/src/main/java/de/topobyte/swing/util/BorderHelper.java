package de.topobyte.swing.util;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;

public class BorderHelper {
   public static void addEmptyBorder(JComponent c, int s) {
      addEmptyBorder(c, s, s, s, s);
   }

   public static void addEmptyBorder(JComponent c, int top, int left, int bottom, int right) {
      Border oldBorder = c.getBorder();
      Border newBorder = BorderFactory.createEmptyBorder(top, left, bottom, right);
      CompoundBorder compound = BorderFactory.createCompoundBorder(newBorder, oldBorder);
      c.setBorder(compound);
   }
}
