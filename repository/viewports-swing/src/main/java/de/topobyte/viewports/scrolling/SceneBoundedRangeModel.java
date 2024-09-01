package de.topobyte.viewports.scrolling;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BoundedRangeModel;
import javax.swing.JComponent;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SceneBoundedRangeModel<T extends JComponent & ViewportWithSignals & HasScene & HasMargin> implements BoundedRangeModel {
   static final Logger logger = LoggerFactory.getLogger(SceneBoundedRangeModel.class);
   private T view;
   private boolean horizontal;
   private ViewportMath<T> calculator;
   private List<ChangeListener> listeners = new ArrayList<>();
   private boolean adjusting = false;

   public SceneBoundedRangeModel(T view, boolean horizontal) {
      this.view = view;
      this.horizontal = horizontal;
      this.calculator = new ViewportMath(view);
      view.addComponentListener(new ComponentAdapter() {
         @Override
         public void componentResized(ComponentEvent e) {
            SceneBoundedRangeModel.this.viewResized();
         }
      });
      view.addViewportListener(new ViewportListener() {
         public void zoomChanged() {
            SceneBoundedRangeModel.this.viewZoomChanged();
         }

         public void viewportChanged() {
            SceneBoundedRangeModel.this.viewViewportChanged();
         }

         public void complexChange() {
            SceneBoundedRangeModel.this.complexChange();
         }
      });
   }

   protected void viewResized() {
      this.fireListeners();
   }

   protected void viewViewportChanged() {
      this.fireListeners();
   }

   protected void viewZoomChanged() {
      this.fireListeners();
   }

   protected void complexChange() {
      this.fireListeners();
   }

   @Override
   public int getMinimum() {
      return this.calculator.getRangeMinimum();
   }

   @Override
   public int getMaximum() {
      return this.calculator.getRangeMaximum(this.horizontal);
   }

   @Override
   public int getExtent() {
      return this.calculator.getRangeExtent(this.horizontal);
   }

   @Override
   public int getValue() {
      return this.calculator.getRangeValue(this.horizontal);
   }

   @Override
   public void setValue(int newValue) {
      logger.debug("setValue(" + newValue + ")");
      double viewportOffset = this.calculator.getViewportOffset(newValue, this.horizontal);
      if (this.horizontal) {
         ((Viewport)this.view).setPositionX(viewportOffset);
      } else {
         ((Viewport)this.view).setPositionY(viewportOffset);
      }

      this.view.repaint();
      this.fireListeners();
   }

   @Override
   public void setMinimum(int newMinimum) {
      logger.debug("setMinimum(" + newMinimum + ")");
   }

   @Override
   public void setMaximum(int newMaximum) {
      logger.debug("setMaximum(" + newMaximum + ")");
   }

   @Override
   public void setExtent(int newExtent) {
      logger.debug("setExtend(" + newExtent + ")");
   }

   @Override
   public void setRangeProperties(int value, int extent, int min, int max, boolean adjusting) {
      logger.debug(String.format("setRangeProperties(%d, %d, %d, %d, %b)", value, extent, min, max, adjusting));
   }

   @Override
   public void addChangeListener(ChangeListener listener) {
      this.listeners.add(listener);
   }

   @Override
   public void removeChangeListener(ChangeListener listener) {
      this.listeners.remove(listener);
   }

   private void fireListeners() {
      ChangeEvent event = new ChangeEvent(this);

      for (ChangeListener listener : this.listeners) {
         listener.stateChanged(event);
      }
   }

   @Override
   public void setValueIsAdjusting(boolean b) {
      logger.debug("setValueIsAdjusting(" + b + ")");
      this.adjusting = b;
      this.fireListeners();
   }

   @Override
   public boolean getValueIsAdjusting() {
      return this.adjusting;
   }
}
