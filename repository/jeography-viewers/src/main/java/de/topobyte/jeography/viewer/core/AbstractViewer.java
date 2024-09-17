package de.topobyte.jeography.viewer.core;

import de.topobyte.awt.util.GraphicsUtil;
import de.topobyte.jeography.core.OverlayPoint;
import de.topobyte.jeography.core.mapwindow.MapWindow;
import de.topobyte.jeography.tiles.TileConfigListener;
import de.topobyte.jeography.viewer.MouseUser;
import de.topobyte.jeography.viewer.config.TileConfig;
import de.topobyte.jeography.viewer.zoom.ZoomMode;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.dnd.DragGestureListener;
import java.awt.dnd.DragGestureRecognizer;
import java.awt.dnd.DragSource;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.Arc2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Arc2D.Double;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.swing.JPanel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractViewer extends JPanel implements MouseUser, ComponentListener, MouseMotionListener, MouseListener, MouseWheelListener {
   private static final long serialVersionUID = -3023462611876276320L;
   static final Logger logger = LoggerFactory.getLogger(AbstractViewer.class);
   protected Color colorBackground = new Color(255, 255, 255, 255);
   protected Color colorBorder = new Color(0, 0, 0, 255);
   protected Color colorTilenumbers = new Color(0, 0, 0, 255);
   protected Color colorCrosshair = new Color(127, 0, 0, 255);
   protected boolean mouseActive = false;
   protected ZoomMode zoomMode = ZoomMode.ZOOM_AND_KEEP_POINT;
   protected boolean drawBorder = true;
   protected boolean drawCrosshair = true;
   protected boolean drawOverlay = true;
   protected boolean drawTileNumbers = true;
   protected TileConfig tileConfig;
   protected TileConfig overlayTileConfig;
   private Set<OverlayPoint> points = null;
   boolean shallRepaint = true;
   Object repaintLock = new Object();
   private DragGestureRecognizer recognizer;
   private DragGestureListener currentDragGestureListener = null;
   private DragGestureListener dragGestureListener = null;
   private Set<TileConfigListener> listeners = new HashSet<>();
   private Set<TileConfigListener> listenersOverlay = new HashSet<>();
   protected List<PaintListener> paintListeners = new ArrayList<>();
   protected Collection<MouseListener> mouseListeners = new ArrayList<>();
   private Point pointPress;
   private boolean mousePressed = false;

   public abstract MapWindow getMapWindow();

   @Override
   public boolean getMouseActive() {
      return this.mouseActive;
   }

   @Override
   public void setMouseActive(boolean state) {
      this.mouseActive = state;
   }

   public ZoomMode getZoomMode() {
      return this.zoomMode;
   }

   public void setZoomMode(ZoomMode zoomMode) {
      this.zoomMode = zoomMode;
   }

   public boolean isDrawBorder() {
      return this.drawBorder;
   }

   public boolean isDrawCrosshair() {
      return this.drawCrosshair;
   }

   public boolean isDrawOverlay() {
      return this.drawOverlay;
   }

   public boolean isDrawTileNumbers() {
      return this.drawTileNumbers;
   }

   public void setDrawBorder(boolean drawBorder) {
      this.drawBorder = drawBorder;
      this.dispatchRepaint();
   }

   public void setDrawCrosshair(boolean drawCrosshair) {
      this.drawCrosshair = drawCrosshair;
      this.dispatchRepaint();
   }

   public void setDrawOverlay(boolean drawOverlay) {
      this.drawOverlay = drawOverlay;
      this.dispatchRepaint();
   }

   public void setDrawTileNumbers(boolean drawTileNumbers) {
      this.drawTileNumbers = drawTileNumbers;
      this.dispatchRepaint();
   }

   public Color getColorBackground() {
      return this.colorBackground;
   }

   public void setColorBackground(Color color) {
      this.colorBackground = color;
   }

   public Color getColorBorder() {
      return this.colorBorder;
   }

   public void setColorBorder(Color color) {
      this.colorBorder = color;
   }

   public Color getColorTilenumbers() {
      return this.colorTilenumbers;
   }

   public void setColorTilenumbers(Color color) {
      this.colorTilenumbers = color;
   }

   public Color getColorCrosshair() {
      return this.colorCrosshair;
   }

   public void setColorCrosshair(Color color) {
      this.colorCrosshair = color;
   }

   public TileConfig getTileConfig() {
      return this.tileConfig;
   }

   public TileConfig getOverlayTileConfig() {
      return this.overlayTileConfig;
   }

   protected void dispatchRepaint() {
      synchronized (this.repaintLock) {
         this.shallRepaint = true;
         this.repaintLock.notify();
      }
   }

   public void setDragging(boolean drag) {
      if (drag) {
         if (this.dragGestureListener != null) {
            this.uninstallDragSource();
            DragSource dragSource = new DragSource();
            this.currentDragGestureListener = this.dragGestureListener;
            this.recognizer = dragSource.createDefaultDragGestureRecognizer(this, 1, this.dragGestureListener);
         }
      } else {
         this.uninstallDragSource();
      }
   }

   private void uninstallDragSource() {
      if (this.recognizer != null) {
         this.recognizer.removeDragGestureListener(this.currentDragGestureListener);
         this.recognizer.setComponent(null);
         this.recognizer = null;
      }
   }

   public void setDragGestureListener(DragGestureListener listener) {
      this.dragGestureListener = listener;
   }

   public void addTileConfigListener(TileConfigListener listener) {
      this.listeners.add(listener);
   }

   public void addOverlayTileConfigListener(TileConfigListener listener) {
      this.listenersOverlay.add(listener);
   }

   protected void triggerTileConfigListeners() {
      for (TileConfigListener listener : this.listeners) {
         listener.tileConfigChanged();
      }
   }

   protected void triggerOverlayTileConfigListeners() {
      for (TileConfigListener listener : this.listenersOverlay) {
         listener.tileConfigChanged();
      }
   }

   public void addPaintListener(PaintListener paintListener) {
      synchronized (this.paintListeners) {
         this.paintListeners.add(paintListener);
      }
   }

   public void removePaintListener(PaintListener paintListener) {
      synchronized (this.paintListeners) {
         this.paintListeners.remove(paintListener);
      }
   }

   public void setMouseListeners(Collection<MouseListener> listeners) {
      this.mouseListeners = listeners;
   }

   @Override
   public void componentResized(ComponentEvent e) {
   }

   @Override
   public void componentMoved(ComponentEvent e) {
   }

   @Override
   public void componentShown(ComponentEvent e) {
   }

   @Override
   public void componentHidden(ComponentEvent e) {
   }

   @Override
   public void mouseDragged(MouseEvent e) {
      if (this.mouseActive && this.mousePressed) {
         Point currentPoint = e.getPoint();
         int dx = this.pointPress.x - currentPoint.x;
         int dy = this.pointPress.y - currentPoint.y;
         this.pointPress = currentPoint;
         this.getMapWindow().move(dx, dy);
         this.repaint();
      }
   }

   @Override
   public void mouseMoved(MouseEvent e) {
   }

   @Override
   public void mouseClicked(MouseEvent e) {
   }

   @Override
   public void mousePressed(MouseEvent e) {
      if (e.getButton() == 1) {
         this.pointPress = e.getPoint();
         this.mousePressed = true;
      }
   }

   @Override
   public void mouseReleased(MouseEvent e) {
      if (e.getButton() == 1) {
         this.mousePressed = false;
      }
   }

   @Override
   public void mouseEntered(MouseEvent e) {
   }

   @Override
   public void mouseExited(MouseEvent e) {
   }

   @Override
   public void mouseWheelMoved(MouseWheelEvent e) {
   }

   protected void drawCrosshair(Graphics2D g) {
      GraphicsUtil.useAntialiasing(g, false);
      g.setStroke(new BasicStroke(1.0F));
      g.setColor(this.colorCrosshair);
      g.drawLine(this.getWidth() / 2, 0, this.getWidth() / 2, this.getHeight());
      g.drawLine(0, this.getHeight() / 2, this.getWidth(), this.getHeight() / 2);
      GraphicsUtil.useAntialiasing(g, true);
      int d = 20;
      g.drawArc(this.getWidth() / 2 - d / 2, this.getHeight() / 2 - d / 2, d, d, 0, 90);
      g.drawArc(this.getWidth() / 2 - d / 2, this.getHeight() / 2 - d / 2, d, d, 180, 90);
   }

   protected void drawOverlayPoints(Graphics2D g) {
      if (this.points != null) {
         GraphicsUtil.useAntialiasing(g, true);

         for (OverlayPoint point : this.points) {
            double px = this.getMapWindow().longitudeToX(point.getLongitude());
            double py = this.getMapWindow().latitudeToY(point.getLatitude());
            int d = 20;
            g.setColor(new Color(255, 0, 0, 127));
            boolean circle = false;
            if (circle) {
               Arc2D arc = new Double(px - (double)(d / 2), py - (double)(d / 2), (double)d, (double)d, 0.0, 360.0, 1);
               g.fill(arc);
            } else {
               Rectangle2D rect = new java.awt.geom.Rectangle2D.Double(px - (double)(d / 2), py - (double)(d / 2), (double)d, (double)d);
               g.fill(rect);
            }
         }
      }
   }

   public void setOverlayPoints(Set<OverlayPoint> points) {
      this.points = points;
   }

   public void gotoOverlayPoints() {
      if (this.points != null && this.points.size() != 0) {
         if (this.points.size() == 1) {
            logger.debug("hopping to point");
            OverlayPoint point = this.points.iterator().next();
            this.getMapWindow().gotoLonLat(point.getLongitude(), point.getLatitude());
         } else {
            logger.debug("showing points");
            this.getMapWindow().gotoPoints(this.points);
         }
      }
   }

   protected class Repainter implements Runnable {
      Repainter() {
      }

      @Override
      public void run() {
         while (true) {
            synchronized (AbstractViewer.this.repaintLock) {
               AbstractViewer.this.shallRepaint = false;
            }

            AbstractViewer.this.repaint();
            synchronized (AbstractViewer.this.repaintLock) {
               if (!AbstractViewer.this.shallRepaint) {
                  try {
                     AbstractViewer.this.repaintLock.wait();
                  } catch (InterruptedException var4) {
                  }
               }
            }
         }
      }
   }
}
