package de.topobyte.viewports.scrolling;

import de.topobyte.viewports.geometry.Coordinate;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ViewportMouseListener extends MouseAdapter {
   private Viewport viewport;

   public ViewportMouseListener(Viewport viewport) {
      this.viewport = viewport;
   }

   protected double getX(MouseEvent e) {
      return (double)e.getX() / this.viewport.getZoom() - this.viewport.getPositionX();
   }

   protected double getY(MouseEvent e) {
      return (double)e.getY() / this.viewport.getZoom() - this.viewport.getPositionY();
   }

   protected Coordinate getCoordinate(MouseEvent e) {
      double posX = this.viewport.getPositionX();
      double posY = this.viewport.getPositionY();
      return new Coordinate((double)e.getX() / this.viewport.getZoom() - posX, (double)e.getY() / this.viewport.getZoom() - posY);
   }
}
