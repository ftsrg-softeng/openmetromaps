package de.topobyte.swing.util;

import java.awt.Dimension;
import javax.swing.JComponent;
import javax.swing.JFrame;

public class FrameHelper {
   public static JFrame showFrameWithComponent(String title, JComponent component, int width, int height, int x, int y, boolean show) {
      JFrame frame = new JFrame(title);
      frame.setSize(new Dimension(width, height));
      frame.setLocation(x, y);
      frame.setContentPane(component);
      if (show) {
         frame.setVisible(true);
      }

      return frame;
   }
}
