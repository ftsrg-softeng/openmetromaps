package de.topobyte.interactiveview;

public interface Zoomable {
   boolean canZoomIn();

   boolean canZoomOut();

   void zoomIn();

   void zoomOut();
}
