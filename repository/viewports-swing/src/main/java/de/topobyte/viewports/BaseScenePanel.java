package de.topobyte.viewports;

import de.topobyte.viewports.geometry.Rectangle;
import de.topobyte.viewports.scrolling.HasMargin;
import de.topobyte.viewports.scrolling.HasScene;
import de.topobyte.viewports.scrolling.ViewportListener;
import de.topobyte.viewports.scrolling.ViewportWithSignals;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BaseScenePanel extends JPanel implements ViewportWithSignals, HasScene, HasMargin {
   private static final long serialVersionUID = -1230821197563599750L;
   static final Logger logger = LoggerFactory.getLogger(BaseScenePanel.class);
   protected Rectangle scene;
   protected int margin = 150;
   protected double positionX = 0.0;
   protected double positionY = 0.0;
   protected double zoom = 1.0;
   private List<ViewportListener> viewportListeners = new ArrayList<>();

   public BaseScenePanel(Rectangle scene) {
      this.scene = scene;
      this.addComponentListener(new ComponentAdapter() {
         @Override
         public void componentResized(ComponentEvent e) {
            BaseScenePanel.this.checkBounds();
         }
      });
   }

   public Rectangle getScene() {
      return this.scene;
   }

   public double getMargin() {
      return (double)this.margin;
   }

   public double getPositionX() {
      return this.positionX;
   }

   public double getPositionY() {
      return this.positionY;
   }

   public double getViewportWidth() {
      return (double)this.getWidth();
   }

   public double getViewportHeight() {
      return (double)this.getHeight();
   }

   public double getZoom() {
      return this.zoom;
   }

   protected void internalSetZoom(double value) {
      this.zoom = value;
   }

   protected void internalSetPositionX(double value) {
      this.positionX = value;
   }

   protected void internalSetPositionY(double value) {
      this.positionY = value;
   }

   public void setPositionX(double value) {
      this.internalSetPositionX(value);
      this.fireViewportListenersViewportChanged();
   }

   public void setPositionY(double value) {
      this.internalSetPositionY(value);
      this.fireViewportListenersViewportChanged();
   }

   public void setZoom(double zoom) {
      this.setZoomCentered(zoom);
   }

   public void setZoomCentered(double zoom) {
      double mx = -this.positionX + (double)this.getWidth() / this.zoom / 2.0;
      double my = -this.positionY + (double)this.getHeight() / this.zoom / 2.0;
      this.internalSetZoom(zoom);
      this.internalSetPositionX((double)this.getWidth() / zoom / 2.0 - mx);
      this.internalSetPositionY((double)this.getHeight() / zoom / 2.0 - my);
      this.checkBounds();
      this.fireViewportListenersZoomChanged();
      this.repaint();
   }

   protected void checkBounds() {
      boolean update = false;
      if (-this.positionX + (double)this.getWidth() / this.zoom > this.getScene().getWidth() + (double)this.margin) {
         logger.debug("Moved out of viewport at right");
         this.internalSetPositionX((double)this.getWidth() / this.zoom - this.getScene().getWidth() - (double)this.margin);
         update = true;
      }

      if (this.positionX > (double)this.margin) {
         logger.debug("Scrolled too much to the left");
         this.internalSetPositionX((double)this.margin);
         update = true;
      }

      if (-this.positionY + (double)this.getHeight() / this.zoom > this.getScene().getHeight() + (double)this.margin) {
         logger.debug("Moved out of viewport at bottom");
         this.internalSetPositionY((double)this.getHeight() / this.zoom - this.getScene().getHeight() - (double)this.margin);
         update = true;
      }

      if (this.positionY > (double)this.margin) {
         logger.debug("Scrolled too much to the top");
         this.internalSetPositionY((double)this.margin);
         update = true;
      }

      if (update) {
         this.repaint();
      }

      this.fireViewportListenersViewportChanged();
   }

   public void addViewportListener(ViewportListener listener) {
      this.viewportListeners.add(listener);
   }

   public void removeViewportListener(ViewportListener listener) {
      this.viewportListeners.remove(listener);
   }

   protected void fireViewportListenersViewportChanged() {
      for (ViewportListener listener : this.viewportListeners) {
         listener.viewportChanged();
      }
   }

   protected void fireViewportListenersZoomChanged() {
      for (ViewportListener listener : this.viewportListeners) {
         listener.zoomChanged();
      }
   }
}
