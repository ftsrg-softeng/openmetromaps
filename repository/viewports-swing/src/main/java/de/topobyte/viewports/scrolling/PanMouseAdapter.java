package de.topobyte.viewports.scrolling;

import de.topobyte.viewports.geometry.Coordinate;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JComponent;

public class PanMouseAdapter<T extends JComponent & Viewport & HasScene & HasMargin> extends MouseAdapter {
   private boolean pressed = false;
   private DragInfo dragInfo = null;
   private T view;
   private ViewportMath<T> calculator;

   public PanMouseAdapter(T view) {
      this.view = view;
      this.calculator = new ViewportMath<>(view);
   }

   @Override
   public void mousePressed(MouseEvent e) {
      if (e.getButton() == 3) {
         this.pressed = true;
         this.dragInfo = new DragInfo((double)e.getX(), (double)e.getY());
      }
   }

   @Override
   public void mouseReleased(MouseEvent e) {
      if (e.getButton() == 3) {
         this.pressed = false;
         this.dragInfo = null;
      }
   }

   @Override
   public void mouseDragged(MouseEvent e) {
      if (this.pressed) {
         this.dragInfo.update((double)e.getX(), (double)e.getY());
         Coordinate delta = this.dragInfo.getDeltaToLast();
         double dx = delta.getX() / this.view.getZoom();
         double dy = delta.getY() / this.view.getZoom();
         double nx = this.view.getPositionX() + dx;
         double ny = this.view.getPositionY() + dy;
         nx = Math.max(nx, this.calculator.getMinimumOffset(true));
         nx = Math.min(nx, this.calculator.getMaximumOffset());
         ny = Math.max(ny, this.calculator.getMinimumOffset(false));
         ny = Math.min(ny, this.calculator.getMaximumOffset());
         this.view.setPositionX(nx);
         this.view.setPositionY(ny);
         this.view.repaint();
      }
   }
}
