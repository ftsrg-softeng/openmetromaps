package de.topobyte.jeography.core.mapwindow;

import de.topobyte.adt.geo.BBox;
import de.topobyte.geomath.WGS84;
import de.topobyte.interactiveview.ZoomChangedListener;
import de.topobyte.jeography.core.OverlayPoint;
import de.topobyte.jeography.core.Tile;
import de.topobyte.jeography.core.viewbounds.BoundsInfo;
import de.topobyte.jeography.core.viewbounds.NopViewBounds;
import de.topobyte.jeography.core.viewbounds.ViewBounds;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SteplessMapWindow implements MapWindow {
   static final Logger logger = LoggerFactory.getLogger(SteplessMapWindow.class);
   private static final int DEFAULT_ZOOM_MIN = 1;
   private static final int DEFAULT_ZOOM_MAX = 18;
   private int zoomMin = 1;
   private int zoomMax = 18;
   double lon;
   double lat;
   double zoom;
   int width;
   int height;
   double worldsize;
   int worldscale = Tile.SIZE;
   double worldsizePixels;
   int px;
   int py;
   private ViewBounds bounds = new NopViewBounds();
   Set<MapWindowChangeListener> listenersChangeGeneral = new HashSet<>();
   Set<ZoomChangedListener> listenersChangeZoom = new HashSet<>();
   Set<MapWindowWorldScaleListener> listenersChangeWorldScale = new HashSet<>();

   private void worldsize() {
      this.worldsize = Math.pow(2.0, this.zoom);
      this.worldsizePixels = this.worldsize * (double)this.worldscale;
   }

   public SteplessMapWindow(int width, int height, double zoom, int px, int py) {
      this.width = width;
      this.height = height;
      this.zoom = zoom;
      this.worldsize();
      this.px = px;
      this.py = py;
      this.geoFromTiles();
   }

   public SteplessMapWindow(int width, int height, double zoom, double lon, double lat) {
      this.width = width;
      this.height = height;
      this.zoom = zoom;
      this.worldsize();
      this.lon = lon;
      this.lat = lat;
      this.tilesFromGeo();
   }

   public SteplessMapWindow(BBox bbox, double zoom) {
      this.zoom = zoom;
      this.worldsize();
      double tileX1 = WGS84.lon2merc(bbox.getLon1(), this.worldsize);
      double tileY1 = WGS84.lat2merc(bbox.getLat1(), this.worldsize);
      double tileX2 = WGS84.lon2merc(bbox.getLon2(), this.worldsize);
      double tileY2 = WGS84.lat2merc(bbox.getLat2(), this.worldsize);
      logger.debug(String.format("%f,%f %f,%f", tileX1, tileY1, tileX2, tileY2));
      this.px = (int)Math.round(tileX1 * (double)this.worldscale);
      this.py = (int)Math.round(tileY1 * (double)this.worldscale);
      this.width = (int)Math.ceil((tileX2 - tileX1) * (double)this.worldscale);
      this.height = (int)Math.ceil((tileY2 - tileY1) * (double)this.worldscale);
      logger.debug(String.format("%d,%d", this.width, this.height));
      this.geoFromTiles();
   }

   public void setViewBounds(ViewBounds bounds) {
      this.bounds = bounds;
   }

   private boolean checkAndCorrectLongitudeBounds() {
      boolean boundariesCrossed = false;

      while (this.lon > 180.0) {
         boundariesCrossed = true;
         this.lon -= 360.0;
      }

      while (this.lon < -180.0) {
         boundariesCrossed = true;
         this.lon += 360.0;
      }

      return boundariesCrossed;
   }

   private void geoFromTiles() {
      this.lon = this.getCenterLon();
      this.lat = this.getCenterLat();
      if (this.checkAndCorrectLongitudeBounds()) {
         this.tilesFromGeo();
      }

      if (this.fixViewBounds()) {
         this.tilesFromGeo();
      }
   }

   private boolean fixViewBounds() {
      BoundsInfo info = this.bounds.checkBounds(this.lon, this.lat);
      switch (info) {
         case OK:
         default:
            return false;
         case LON_OUT_OF_BOUNDS:
            this.lon = this.bounds.fixLon(this.lon);
            return true;
         case LAT_OUT_OF_BOUNDS:
            this.lat = this.bounds.fixLat(this.lat);
            return true;
         case LON_LAT_OUT_OF_BOUNDS:
            this.lon = this.bounds.fixLon(this.lon);
            this.lat = this.bounds.fixLat(this.lat);
            return true;
      }
   }

   private void tilesFromGeo() {
      double tileX = WGS84.lon2merc(this.lon, this.worldsize);
      double tileY = WGS84.lat2merc(this.lat, this.worldsize);
      this.px = (int)Math.floor(tileX * (double)this.worldscale - (double)this.width / 2.0);
      this.py = (int)Math.floor(tileY * (double)this.worldscale - (double)this.height / 2.0);
      logger.debug(String.format("%d,%d", this.px, this.py));
   }

   public double getCenterX() {
      double x = (double)this.px;
      x += (double)this.width / 2.0;
      return x / (double)this.worldscale;
   }

   public double getCenterY() {
      double y = (double)this.py;
      y += (double)this.height / 2.0;
      return y / (double)this.worldscale;
   }

   @Override
   public double getCenterLon() {
      return WGS84.merc2lon(this.getCenterX(), this.worldsize);
   }

   @Override
   public double getCenterLat() {
      return WGS84.merc2lat(this.getCenterY(), this.worldsize);
   }

   public double getPositionX(int px) {
      double x = (double)this.px;
      x += (double)px;
      return x / (double)this.worldscale;
   }

   public double getPositionY(int py) {
      double y = (double)this.py;
      y += (double)py;
      return y / (double)this.worldscale;
   }

   @Override
   public double getPositionLon(int x) {
      return WGS84.merc2lon(this.getPositionX(x), this.worldsize);
   }

   @Override
   public double getPositionLat(int y) {
      return WGS84.merc2lat(this.getPositionY(y), this.worldsize);
   }

   @Override
   public BBox getBoundingBox() {
      double lon1 = this.getPositionLon(0);
      double lon2 = this.getPositionLon(this.width);
      double lat1 = this.getPositionLat(0);
      double lat2 = this.getPositionLat(this.height);
      return new BBox(lon1, lat1, lon2, lat2);
   }

   @Override
   public double getZoom() {
      return this.zoom;
   }

   @Override
   public int getWidth() {
      return this.width;
   }

   @Override
   public int getHeight() {
      return this.height;
   }

   @Override
   public void resize(int newWidth, int newHeight) {
      this.width = newWidth;
      this.height = newHeight;
      this.tilesFromGeo();
      this.fireChangeListeners();
   }

   @Override
   public void move(int dx, int dy) {
      this.px += dx;
      this.py += dy;
      this.geoFromTiles();
      this.fireChangeListeners();
   }

   @Override
   public void setMaxZoom(int zoomMax) {
      this.zoomMax = zoomMax;
   }

   @Override
   public void setMinZoom(int zoomMin) {
      this.zoomMin = zoomMin;
   }

   @Override
   public int getMaxZoom() {
      return this.zoomMax;
   }

   @Override
   public int getMinZoom() {
      return this.zoomMin;
   }

   public boolean zoomIn(double d) {
      double oldZoom = this.zoom;
      if (this.zoom < (double)this.zoomMax) {
         this.zoom = Math.min(this.zoom + d, (double)this.zoomMax);
         this.worldsize();
      }

      this.tilesFromGeo();
      this.fireChangeListeners();
      this.fireZoomListeners();
      return this.zoom != oldZoom;
   }

   public boolean zoomOut(double d) {
      double oldZoom = this.zoom;
      if (this.zoom > (double)this.zoomMin) {
         this.zoom = Math.max(this.zoom - d, (double)this.zoomMin);
         this.worldsize();
      }

      this.tilesFromGeo();
      this.fireChangeListeners();
      this.fireZoomListeners();
      return this.zoom != oldZoom;
   }

   public boolean zoom(double zoomlevel) {
      if (zoomlevel > (double)this.zoomMax || zoomlevel < (double)this.zoomMin) {
         return false;
      } else if (zoomlevel == this.zoom) {
         return false;
      } else {
         this.zoom = zoomlevel;
         this.worldsize();
         this.tilesFromGeo();
         this.fireChangeListeners();
         this.fireZoomListeners();
         return true;
      }
   }

   public void zoomInToPosition(int x, int y, double d) {
      this.lon = this.bounds.fixLon(this.getPositionLon(x));
      this.lat = this.bounds.fixLat(this.getPositionLat(y));
      this.checkAndCorrectLongitudeBounds();
      this.zoomIn(d);
   }

   public void zoomOutToPosition(int x, int y, double d) {
      this.lon = this.bounds.fixLon(this.getPositionLon(x));
      this.lat = this.bounds.fixLat(this.getPositionLat(y));
      this.checkAndCorrectLongitudeBounds();
      this.zoomOut(d);
   }

   @Override
   public void addChangeListener(MapWindowChangeListener listener) {
      this.listenersChangeGeneral.add(listener);
   }

   @Override
   public void addZoomListener(ZoomChangedListener listener) {
      this.listenersChangeZoom.add(listener);
   }

   @Override
   public void addWorldScaleListener(MapWindowWorldScaleListener listener) {
      this.listenersChangeWorldScale.add(listener);
   }

   private void fireChangeListeners() {
      for (MapWindowChangeListener listener : this.listenersChangeGeneral) {
         listener.changed();
      }
   }

   private void fireZoomListeners() {
      for (ZoomChangedListener listener : this.listenersChangeZoom) {
         listener.zoomChanged();
      }
   }

   private void fireWorldScaleChangeListeners() {
      for (MapWindowWorldScaleListener listener : this.listenersChangeWorldScale) {
         listener.worldScaleChanged();
      }
   }

   @Override
   public double longitudeToX(double ilon) {
      double tileX = WGS84.lon2merc(ilon, this.worldsize) * (double)this.worldscale;
      return tileX - (double)this.px;
   }

   @Override
   public double latitudeToY(double ilat) {
      double tileY = WGS84.lat2merc(ilat, this.worldsize) * (double)this.worldscale;
      return tileY - (double)this.py;
   }

   @Override
   public double mercatorToX(double mx) {
      if (this.worldscale == Tile.SIZE) {
         return mx - (double)this.px;
      } else {
         double ratio = (double)this.worldscale / (double)Tile.SIZE;
         double bx = (double)this.px / ratio;
         double pos = mx - bx;
         return pos * ratio;
      }
   }

   @Override
   public double mercatorToY(double my) {
      if (this.worldscale == Tile.SIZE) {
         return my - (double)this.py;
      } else {
         double ratio = (double)this.worldscale / (double)Tile.SIZE;
         double by = (double)this.py / ratio;
         double pos = my - by;
         return pos * ratio;
      }
   }

   @Override
   public void gotoLonLat(double longitude, double latitude) {
      this.lon = longitude;
      this.lat = latitude;
      this.checkAndCorrectLongitudeBounds();
      this.tilesFromGeo();
      this.fireChangeListeners();
   }

   @Override
   public void gotoPoints(Collection<OverlayPoint> points) {
      OverlayPoint mean = OverlayPoint.mean(points);
      this.gotoLonLat(mean.getLongitude(), mean.getLatitude());
      OverlayPoint minimum = OverlayPoint.minimum(points);
      OverlayPoint maximum = OverlayPoint.maximum(points);
      this.zoom = (double)this.zoomMax;
      this.worldsize();
      this.tilesFromGeo();

      while ((!this.containsPoint(minimum) || !this.containsPoint(maximum)) && this.zoomOut(1.0)) {
      }

      this.fireChangeListeners();
   }

   @Override
   public void gotoLonLat(double lon1, double lon2, double lat1, double lat2) {
      List<OverlayPoint> points = new ArrayList<>();
      points.add(new OverlayPoint(lon1, lat1));
      points.add(new OverlayPoint(lon2, lat2));
      this.gotoPoints(points);
   }

   @Override
   public boolean containsPoint(OverlayPoint point) {
      double tileX = WGS84.lon2merc(point.getLongitude(), this.worldsize);
      double tileY = WGS84.lat2merc(point.getLatitude(), this.worldsize);
      double minX = (double)this.px / (double)this.worldscale;
      double maxX = (double)(this.px + this.width) / (double)this.worldscale;
      double minY = (double)this.py / (double)this.worldscale;
      double maxY = (double)(this.py + this.height) / (double)this.worldscale;
      return tileX >= minX && tileX <= maxX && tileY >= minY && tileY <= maxY;
   }

   public double getX(double x) {
      return this.longitudeToX(x);
   }

   public double getY(double y) {
      return this.latitudeToY(y);
   }

   @Override
   public boolean setWorldScale(int worldScale) {
      if (this.worldscale == worldScale) {
         return false;
      } else {
         this.worldscale = worldScale;
         this.worldsize();
         this.tilesFromGeo();
         this.fireChangeListeners();
         this.fireWorldScaleChangeListeners();
         return true;
      }
   }

   @Override
   public int getWorldScale() {
      return this.worldscale;
   }

   @Override
   public boolean zoomIn() {
      return this.zoomIn(0.5);
   }

   @Override
   public boolean zoomOut() {
      return this.zoomOut(0.5);
   }

   @Override
   public boolean zoom(int zoomlevel) {
      if (zoomlevel <= this.zoomMax && zoomlevel >= this.zoomMin) {
         if ((double)zoomlevel == this.zoom) {
            return false;
         } else {
            this.zoom = (double)zoomlevel;
            this.worldsize();
            this.tilesFromGeo();
            this.fireChangeListeners();
            this.fireZoomListeners();
            return true;
         }
      } else {
         return false;
      }
   }

   @Override
   public void zoomInToPosition(int x, int y) {
      this.zoomInToPosition(x, y, 0.5);
   }

   @Override
   public void zoomOutToPosition(int x, int y) {
      this.zoomOutToPosition(x, y, 0.5);
   }

   @Override
   public double getWorldsizePixels() {
      return this.worldsizePixels;
   }
}
