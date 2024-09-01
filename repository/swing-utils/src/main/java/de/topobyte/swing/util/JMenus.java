package de.topobyte.swing.util;

import javax.swing.Action;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

public class JMenus {
   public static void addItem(JMenu menu, Action action) {
      menu.add(new JMenuItem(action));
   }

   public static void addItem(JMenu menu, Action action, KeyStroke keyStroke) {
      JMenuItem item = new JMenuItem(action);
      item.setAccelerator(keyStroke);
      menu.add(item);
   }

   public static void addItem(JMenu menu, Action action, int modifiers, int keyCode) {
      KeyStroke keyStroke = KeyStroke.getKeyStroke(keyCode, modifiers);
      addItem(menu, action, keyStroke);
   }

   public static void addCheckbox(JMenu menu, Action action) {
      menu.add((JMenuItem)(new JCheckBoxMenuItem(action)));
   }

   public static void addCheckbox(JMenu menu, Action action, KeyStroke keyStroke) {
      JCheckBoxMenuItem item = new JCheckBoxMenuItem(action);
      item.setAccelerator(keyStroke);
      menu.add((JMenuItem)item);
   }

   public static void addCheckbox(JMenu menu, Action action, int keyCode) {
      KeyStroke keyStroke = KeyStroke.getKeyStroke(keyCode, 0);
      addCheckbox(menu, action, keyStroke);
   }

   public static void addCheckbox(JMenu menu, Action action, int modifiers, int keyCode) {
      KeyStroke keyStroke = KeyStroke.getKeyStroke(keyCode, modifiers);
      addCheckbox(menu, action, keyStroke);
   }
}
