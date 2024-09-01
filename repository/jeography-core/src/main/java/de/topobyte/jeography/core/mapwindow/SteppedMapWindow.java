package de.topobyte.jeography.core.mapwindow;

import de.topobyte.adt.geo.BBox;
import de.topobyte.geomath.WGS84;
import de.topobyte.interactiveview.ZoomChangedListener;
import de.topobyte.jeography.core.OverlayPoint;
import de.topobyte.jeography.core.Tile;
import de.topobyte.jeography.core.TileOnWindow;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SteppedMapWindow implements TileMapWindow {
   static final Logger logger = LoggerFactory.getLogger(SteppedMapWindow.class);
   private static final int DEFAULT_ZOOM_MIN = 1;
   private static final int DEFAULT_ZOOM_MAX = 18;
   private int zoomMin = 1;
   private int zoomMax = 18;
   int tileWidth = 256;
   int tileHeight = 256;
   double lon;
   double lat;
   int width;
   int height;
   int zoom;
   int tx;
   int ty;
   int xoff;
   int yoff;
   Set<MapWindowChangeListener> listenersChangeGeneral = new HashSet<>();
   Set<ZoomChangedListener> listenersChangeZoom = new HashSet<>();
   Set<MapWindowTileSizeListener> listenersChangeTileSize = new HashSet<>();
   Set<MapWindowWorldScaleListener> listenersChangeWorldScale = new HashSet<>();

   public SteppedMapWindow(int width, int height, int zoom, int tx, int ty, int xoff, int yoff) {
      this.width = width;
      this.height = height;
      this.zoom = zoom;
      this.tx = tx;
      this.ty = ty;
      this.xoff = xoff;
      this.yoff = yoff;
      this.geoFromTiles();
   }

   public SteppedMapWindow(int width, int height, int zoom, double lon, double lat) {
      this.width = width;
      this.height = height;
      this.zoom = zoom;
      this.lon = lon;
      this.lat = lat;
      this.tilesFromGeo();
   }

   public SteppedMapWindow(BBox bbox, int zoom) {
      this.zoom = zoom;
      double tileX1 = WGS84.lon2merc(bbox.getLon1(), (double)(1 << zoom));
      double tileY1 = WGS84.lat2merc(bbox.getLat1(), (double)(1 << zoom));
      double tileX2 = WGS84.lon2merc(bbox.getLon2(), (double)(1 << zoom));
      double tileY2 = WGS84.lat2merc(bbox.getLat2(), (double)(1 << zoom));
      logger.debug(String.format("%f,%f %f,%f", tileX1, tileY1, tileX2, tileY2));
      this.tx = (int)tileX1;
      this.ty = (int)tileY1;
      this.xoff = (int)Math.round((tileX1 - (double)this.tx) * (double)this.tileWidth);

      for (this.yoff = (int)Math.round((tileY1 - (double)this.ty) * (double)this.tileHeight); this.xoff >= this.tileWidth; this.tx++) {
         this.xoff = this.xoff - this.tileWidth;
      }

      while (this.yoff >= this.tileHeight) {
         this.yoff = this.yoff - this.tileHeight;
         this.ty++;
      }

      this.width = (int)Math.ceil((tileX2 - tileX1) * (double)this.tileWidth);
      this.height = (int)Math.ceil((tileY2 - tileY1) * (double)this.tileHeight);
      logger.debug(String.format("%d,%d", this.width, this.height));
      this.geoFromTiles();
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
   }

   private void tilesFromGeo() {
      double tileX = WGS84.lon2merc(this.lon, (double)(1 << this.zoom));
      double tileY = WGS84.lat2merc(this.lat, (double)(1 << this.zoom));
      tileX -= (double)this.width / 2.0 / (double)this.tileWidth;
      tileY -= (double)this.height / 2.0 / (double)this.tileHeight;
      this.tx = (int)Math.floor(tileX);
      this.ty = (int)Math.floor(tileY);
      this.xoff = (int)Math.round((tileX - (double)this.tx) * (double)this.tileWidth);

      for (this.yoff = (int)Math.round((tileY - (double)this.ty) * (double)this.tileHeight); this.xoff >= this.tileWidth; this.tx++) {
         this.xoff = this.xoff - this.tileWidth;
      }

      while (this.yoff >= this.tileHeight) {
         this.yoff = this.yoff - this.tileHeight;
         this.ty++;
      }

      logger.debug(String.format("%d,%d %d,%d", this.tx, this.ty, this.xoff, this.yoff));
   }

   @Override
   public double getCenterX() {
      double x = (double)(this.tx * this.tileWidth);
      x += (double)this.xoff;
      x += (double)this.width / 2.0;
      return x / (double)this.tileWidth;
   }

   @Override
   public double getCenterY() {
      double y = (double)(this.ty * this.tileHeight);
      y += (double)this.yoff;
      y += (double)this.height / 2.0;
      return y / (double)this.tileHeight;
   }

   @Override
   public double getCenterLon() {
      return WGS84.merc2lon(this.getCenterX(), (double)(1 << this.zoom));
   }

   @Override
   public double getCenterLat() {
      return WGS84.merc2lat(this.getCenterY(), (double)(1 << this.zoom));
   }

   @Override
   public double getPositionX(int px) {
      double x = (double)(this.tx * this.tileWidth);
      x += (double)this.xoff;
      x += (double)px;
      return x / (double)this.tileWidth;
   }

   @Override
   public double getPositionY(int py) {
      double y = (double)(this.ty * this.tileHeight);
      y += (double)this.yoff;
      y += (double)py;
      return y / (double)this.tileHeight;
   }

   @Override
   public double getPositionLon(int x) {
      return WGS84.merc2lon(this.getPositionX(x), (double)(1 << this.zoom));
   }

   @Override
   public double getPositionLat(int y) {
      return WGS84.merc2lat(this.getPositionY(y), (double)(1 << this.zoom));
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
   public int getNumTilesX() {
      int n = 1;
      int coverFirst = this.tileWidth - this.xoff;
      int left = this.width - coverFirst;
      if (left >= 0) {
         left += this.tileWidth - 1;
         n = 1 + left / this.tileWidth;
      }

      return n;
   }

   @Override
   public int getNumTilesY() {
      int n = 1;
      int coverFirst = this.tileHeight - this.yoff;
      int top = this.height - coverFirst;
      if (top >= 0) {
         top += this.tileHeight - 1;
         n = 1 + top / this.tileHeight;
      }

      return n;
   }

   @Override
   public double getZoom() {
      return (double)this.zoom;
   }

   @Override
   public int getZoomLevel() {
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
   public Iterator<TileOnWindow> iterator() {
      return new MapWindowIterator(this);
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
      this.xoff += dx;

      for (this.yoff += dy; this.xoff < 0; this.tx--) {
         this.xoff = this.xoff + this.tileWidth;
      }

      while (this.xoff > this.tileWidth) {
         this.xoff = this.xoff - this.tileWidth;
         this.tx++;
      }

      while (this.yoff < 0) {
         this.yoff = this.yoff + this.tileHeight;
         this.ty--;
      }

      while (this.yoff > this.tileHeight) {
         this.yoff = this.yoff - this.tileHeight;
         this.ty++;
      }

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

   @Override
   public boolean zoomIn() {
      int oldZoom = this.zoom;
      if (this.zoom < this.zoomMax) {
         this.zoom++;
      }

      this.tilesFromGeo();
      this.fireChangeListeners();
      this.fireZoomListeners();
      return this.zoom != oldZoom;
   }

   @Override
   public boolean zoomOut() {
      int oldZoom = this.zoom;
      if (this.zoom > this.zoomMin) {
         this.zoom--;
      }

      this.tilesFromGeo();
      this.fireChangeListeners();
      this.fireZoomListeners();
      return this.zoom != oldZoom;
   }

   @Override
   public boolean zoom(int zoomlevel) {
      if (zoomlevel > this.zoomMax || zoomlevel < this.zoomMin) {
         return false;
      } else if (zoomlevel == this.zoom) {
         return false;
      } else {
         this.zoom = zoomlevel;
         this.tilesFromGeo();
         this.fireChangeListeners();
         this.fireZoomListeners();
         return true;
      }
   }

   @Override
   public void zoomInToPosition(int x, int y) {
      this.lon = this.getPositionLon(x);
      this.lat = this.getPositionLat(y);
      this.checkAndCorrectLongitudeBounds();
      this.zoomIn();
   }

   @Override
   public void zoomOutToPosition(int x, int y) {
      this.lon = this.getPositionLon(x);
      this.lat = this.getPositionLat(y);
      this.checkAndCorrectLongitudeBounds();
      this.zoomOut();
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
   public void addTileSizeListener(MapWindowTileSizeListener listener) {
      this.listenersChangeTileSize.add(listener);
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

   private void fireTileSizeChangeListeners() {
      for (MapWindowTileSizeListener listener : this.listenersChangeTileSize) {
         listener.tileSizeChanged();
      }
   }

   private void fireWorldScaleChangeListeners() {
      for (MapWindowWorldScaleListener listener : this.listenersChangeWorldScale) {
         listener.worldScaleChanged();
      }
   }

   @Override
   public double longitudeToX(double ilon) {
      double tileX = WGS84.lon2merc(ilon, (double)(1 << this.zoom));
      double pos = tileX - (double)this.tx - (double)this.xoff / (double)this.tileWidth;
      return pos * (double)this.tileWidth;
   }

   @Override
   public double latitudeToY(double ilat) {
      double tileY = WGS84.lat2merc(ilat, (double)(1 << this.zoom));
      double pos = tileY - (double)this.ty - (double)this.yoff / (double)this.tileHeight;
      return pos * (double)this.tileHeight;
   }

   @Override
   public double mercatorToX(double mx) {
      if (this.tileWidth == Tile.SIZE) {
         int bx = this.tx * Tile.SIZE + this.xoff;
         return mx - (double)bx;
      } else {
         double ratio = (double)this.tileWidth / (double)Tile.SIZE;
         double bx = (double)(this.tx * Tile.SIZE) + (double)this.xoff / ratio;
         double pos = mx - bx;
         return pos * ratio;
      }
   }

   @Override
   public double mercatorToY(double my) {
      if (this.tileHeight == Tile.SIZE) {
         int by = this.ty * Tile.SIZE + this.yoff;
         return my - (double)by;
      } else {
         double ratio = (double)this.tileHeight / (double)Tile.SIZE;
         double by = (double)(this.ty * Tile.SIZE) + (double)this.yoff / ratio;
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
      this.zoom = this.zoomMax;
      this.tilesFromGeo();

      while ((!this.containsPoint(minimum) || !this.containsPoint(maximum)) && this.zoomOut()) {
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
      double tileX = WGS84.lon2merc(point.getLongitude(), (double)(1 << this.zoom));
      double tileY = WGS84.lat2merc(point.getLatitude(), (double)(1 << this.zoom));
      double minX = (double)this.tx + (double)this.xoff / (double)this.tileWidth;
      double maxX = (double)this.tx + (double)(this.xoff + this.width) / (double)this.tileWidth;
      double minY = (double)this.ty + (double)this.yoff / (double)this.tileHeight;
      double maxY = (double)this.ty + (double)(this.yoff + this.height) / (double)this.tileHeight;
      return tileX >= minX && tileX <= maxX && tileY >= minY && tileY <= maxY;
   }

   public double getX(double x) {
      return this.longitudeToX(x);
   }

   public double getY(double y) {
      return this.latitudeToY(y);
   }

   public int getPositionX(SteppedMapWindow mapWindow) {
      int thisX = this.tx * this.tileWidth + this.xoff;
      int otherX = mapWindow.tx * this.tileWidth + mapWindow.xoff;
      return otherX - thisX;
   }

   public int getPositionY(SteppedMapWindow mapWindow) {
      int thisY = this.ty * this.tileHeight + this.yoff;
      int otherY = mapWindow.ty * this.tileHeight + mapWindow.yoff;
      return otherY - thisY;
   }

   @Override
   public boolean setTileSize(int tileSize) {
      if (this.tileWidth == tileSize && this.tileHeight == tileSize) {
         return false;
      } else {
         this.tileWidth = tileSize;
         this.tileHeight = tileSize;
         this.tilesFromGeo();
         this.fireChangeListeners();
         this.fireTileSizeChangeListeners();
         this.fireWorldScaleChangeListeners();
         return true;
      }
   }

   @Override
   public int getTileWidth() {
      return this.tileWidth;
   }

   @Override
   public int getTileHeight() {
      return this.tileHeight;
   }

   @Override
   public int minimumCacheSize() {
      int tilesX = (this.width + this.tileWidth - 1) / this.tileWidth;
      int tilesY = (this.height + this.tileHeight - 1) / this.tileHeight;
      return (tilesX + 1) * (tilesY + 1);
   }

   @Override
   public double getWorldsizePixels() {
      return (double)((1 << this.zoom) * this.tileWidth);
   }

   @Override
   public boolean setWorldScale(int worldScale) {
      return this.setTileSize(worldScale);
   }

   @Override
   public int getWorldScale() {
      return this.getTileWidth();
   }
}
