package de.topobyte.swing.util;

import java.awt.Component;
import java.awt.Container;
import java.awt.Window;
import javax.swing.JDialog;
import javax.swing.JFrame;

public class Components {
   public static Window getContainingWindow(Component component) {
      Container parent = component.getParent();

      while (parent != null && !(parent instanceof Window)) {
         parent = parent.getParent();
      }

      return (Window)parent;
   }

   public static JFrame getContainingFrame(Component component) {
      Container parent = component.getParent();

      while (parent != null && !(parent instanceof JFrame)) {
         parent = parent.getParent();
      }

      return (JFrame)parent;
   }

   public static JDialog getContainingDialog(Component component) {
      Container parent = component.getParent();

      while (parent != null && !(parent instanceof JDialog)) {
         parent = parent.getParent();
      }

      return (JDialog)parent;
   }
}
