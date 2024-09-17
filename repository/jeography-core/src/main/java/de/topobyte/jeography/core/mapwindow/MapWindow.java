package de.topobyte.jeography.core.mapwindow;

import de.topobyte.adt.geo.BBox;
import de.topobyte.interactiveview.ZoomChangedListener;
import de.topobyte.jeography.core.OverlayPoint;
import de.topobyte.jgs.transform.CoordinateTransformer;
import java.util.Collection;

public interface MapWindow extends CoordinateTransformer {
   boolean setWorldScale(int var1);

   int getWorldScale();

   double getWorldsizePixels();

   double getCenterLon();

   double getCenterLat();

   double getZoom();

   double getPositionLon(int var1);

   double getPositionLat(int var1);

   BBox getBoundingBox();

   int getWidth();

   int getHeight();

   void resize(int var1, int var2);

   void move(int var1, int var2);

   void setMaxZoom(int var1);

   void setMinZoom(int var1);

   int getMaxZoom();

   int getMinZoom();

   boolean zoomIn();

   boolean zoomOut();

   boolean zoom(int var1);

   void zoomInToPosition(int var1, int var2);

   void zoomOutToPosition(int var1, int var2);

   void addChangeListener(MapWindowChangeListener var1);

   void addZoomListener(ZoomChangedListener var1);

   void addWorldScaleListener(MapWindowWorldScaleListener var1);

   double longitudeToX(double var1);

   double latitudeToY(double var1);

   double mercatorToX(double var1);

   double mercatorToY(double var1);

   void gotoLonLat(double var1, double var3);

   void gotoPoints(Collection<OverlayPoint> var1);

   void gotoLonLat(double var1, double var3, double var5, double var7);

   boolean containsPoint(OverlayPoint var1);
}
