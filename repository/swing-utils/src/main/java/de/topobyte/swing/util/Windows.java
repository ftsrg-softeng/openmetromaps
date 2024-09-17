package de.topobyte.swing.util;

import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import javax.swing.AbstractAction;
import javax.swing.JRootPane;
import javax.swing.KeyStroke;
import javax.swing.RootPaneContainer;

public class Windows {
   public static <T extends Window & RootPaneContainer> void setCloseOnEscape(final T window) {
      JRootPane root = window.getRootPane();
      root.getInputMap(2).put(KeyStroke.getKeyStroke(27, 0), "escape");
      root.getActionMap().put("escape", new AbstractAction() {
         private static final long serialVersionUID = 1L;

         @Override
         public void actionPerformed(ActionEvent event) {
            window.dispatchEvent(new WindowEvent(window, 201));
         }
      });
   }
}
