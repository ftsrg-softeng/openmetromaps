package de.topobyte.viewports.scrolling;

public interface ViewportWithSignals extends Viewport {
   void addViewportListener(ViewportListener var1);

   void removeViewportListener(ViewportListener var1);
}
