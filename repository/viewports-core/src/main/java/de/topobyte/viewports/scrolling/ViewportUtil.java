package de.topobyte.viewports.scrolling;

import de.topobyte.viewports.geometry.Coordinate;

public class ViewportUtil {
   public static double getRealX(Viewport viewport, double x) {
      return x / viewport.getZoom() - viewport.getPositionX();
   }

   public static double getRealY(Viewport viewport, double y) {
      return y / viewport.getZoom() - viewport.getPositionY();
   }

   public static double getViewX(Viewport viewport, double x) {
      return (x + viewport.getPositionX()) * viewport.getZoom();
   }

   public static double getViewY(Viewport viewport, double y) {
      return (y + viewport.getPositionY()) * viewport.getZoom();
   }

   public static void zoomFixed(Viewport viewport, Coordinate point, boolean in, double zoomStep) {
      double frx = getRealX(viewport, point.getX());
      double fry = getRealY(viewport, point.getY());
      if (in) {
         viewport.setZoom(viewport.getZoom() * (1.0 + zoomStep));
      } else {
         viewport.setZoom(viewport.getZoom() / (1.0 + zoomStep));
      }

      double fx = getViewX(viewport, frx);
      double fy = getViewY(viewport, fry);
      double dx = fx - point.getX();
      double dy = fy - point.getY();
      viewport.setPositionX(viewport.getPositionX() - dx / viewport.getZoom());
      viewport.setPositionY(viewport.getPositionY() - dy / viewport.getZoom());
   }
}
