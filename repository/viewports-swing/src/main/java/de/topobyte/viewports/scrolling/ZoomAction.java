package de.topobyte.viewports.scrolling;

import de.topobyte.swing.util.action.SimpleAction;
import java.awt.event.ActionEvent;
import javax.swing.JComponent;

public class ZoomAction<T extends JComponent & Viewport> extends SimpleAction {
   private static final long serialVersionUID = -3260994363917453585L;
   private T component;
   private ZoomAction.Type type;

   public ZoomAction(T component, ZoomAction.Type type) {
      super(title(type), description(type), icon(type));
      this.component = component;
      this.type = type;
   }

   private static String icon(ZoomAction.Type type) {
      switch (type) {
         case IDENTITY:
         default:
            return "res/images/24x24/zoom-original.png";
         case IN:
            return "res/images/24x24/zoom-in.png";
         case OUT:
            return "res/images/24x24/zoom-out.png";
      }
   }

   private static String title(ZoomAction.Type type) {
      switch (type) {
         case IDENTITY:
         default:
            return "Zoom 100%";
         case IN:
            return "Zoom in";
         case OUT:
            return "Zoom out";
      }
   }

   private static String description(ZoomAction.Type type) {
      switch (type) {
         case IDENTITY:
         default:
            return "Zoom the scene to 100%";
         case IN:
            return "Zoom in the scene";
         case OUT:
            return "Zoom out the scene";
      }
   }

   public void actionPerformed(ActionEvent e) {
      double zoom = this.component.getZoom();
      switch (this.type) {
         case IDENTITY:
         default:
            zoom = 1.0;
            break;
         case IN:
            zoom *= 1.2;
            break;
         case OUT:
            zoom /= 1.2;
      }

      this.component.setZoom(zoom);
      this.component.repaint();
   }

   public static enum Type {
      IN,
      OUT,
      IDENTITY;
   }
}
