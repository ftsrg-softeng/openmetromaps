package de.topobyte.viewports.scrolling;

public interface Viewport {
   double getPositionX();

   double getPositionY();

   double getViewportWidth();

   double getViewportHeight();

   double getZoom();

   void setPositionX(double var1);

   void setPositionY(double var1);

   void setZoom(double var1);
}
