package de.topobyte.swing.util;

import java.awt.Component;
import java.awt.Graphics;
import javax.swing.Icon;

public class EmptyIcon implements Icon {
   private int width;
   private int height;

   public EmptyIcon(int size) {
      this(size, size);
   }

   public EmptyIcon(int width, int height) {
      this.width = width;
      this.height = height;
   }

   @Override
   public int getIconWidth() {
      return this.width;
   }

   @Override
   public int getIconHeight() {
      return this.height;
   }

   @Override
   public void paintIcon(Component c, Graphics g, int x, int y) {
   }
}
