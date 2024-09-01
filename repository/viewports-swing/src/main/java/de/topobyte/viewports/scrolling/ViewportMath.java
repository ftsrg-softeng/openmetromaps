package de.topobyte.viewports.scrolling;

import javax.swing.JComponent;

public class ViewportMath<T extends JComponent & Viewport & HasScene & HasMargin> {
   private T view;

   public ViewportMath(T view) {
      this.view = view;
   }

   public double getMinimumOffset(boolean horizontal) {
      return horizontal
         ? 0.0 - this.view.getScene().getWidth() - this.view.getMargin() + (double)this.view.getWidth() / this.view.getZoom()
         : 0.0 - this.view.getScene().getHeight() - this.view.getMargin() + (double)this.view.getHeight() / this.view.getZoom();
   }

   public double getMaximumOffset() {
      return this.view.getMargin();
   }

   public int getRangeMinimum() {
      return (int)Math.round(0.0 - this.view.getMargin() * this.view.getZoom());
   }

   public int getRangeMaximum(boolean horizontal) {
      return horizontal
         ? (int)Math.round((this.view.getScene().getWidth() + this.view.getMargin()) * this.view.getZoom())
         : (int)Math.round((this.view.getScene().getHeight() + this.view.getMargin()) * this.view.getZoom());
   }

   public int getRangeExtent(boolean horizontal) {
      return horizontal ? Math.round((float)this.view.getWidth()) : Math.round((float)this.view.getHeight());
   }

   public int getRangeValue(boolean horizontal) {
      return horizontal ? (int)Math.round(-this.view.getPositionX() * this.view.getZoom()) : (int)Math.round(-this.view.getPositionY() * this.view.getZoom());
   }

   public double getViewportOffset(int rangeValue, boolean horizontal) {
      rangeValue = Math.min(rangeValue, this.getRangeMaximum(horizontal) - this.getRangeExtent(horizontal));
      rangeValue = Math.max(rangeValue, this.getRangeMinimum());
      return (double)(-rangeValue) / this.view.getZoom();
   }
}
