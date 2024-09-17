package de.topobyte.awt.util;

import java.awt.Graphics2D;
import java.awt.RenderingHints;

public class GraphicsUtil {
   public static void useAntialiasing(Graphics2D g, boolean value) {
      if (value) {
         g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
      } else {
         g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
      }
   }
}
