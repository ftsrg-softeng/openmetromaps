package de.topobyte.swing.util;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.Icon;

public class BufferedImageIcon implements Icon {
   private BufferedImage bi;

   public BufferedImageIcon(BufferedImage bi) {
      this.bi = bi;
   }

   @Override
   public void paintIcon(Component c, Graphics g, int x, int y) {
      g.drawImage(this.bi, x, y, null);
   }

   @Override
   public int getIconWidth() {
      return this.bi.getWidth();
   }

   @Override
   public int getIconHeight() {
      return this.bi.getHeight();
   }
}
