package de.topobyte.jeography.viewer.core;

import de.topobyte.adt.geo.BBox;
import de.topobyte.adt.geo.Coordinate;
import de.topobyte.awt.util.GraphicsUtil;
import de.topobyte.interactiveview.ZoomChangedListener;
import de.topobyte.jeography.core.Tile;
import de.topobyte.jeography.core.TileOnWindow;
import de.topobyte.jeography.core.TileUtil;
import de.topobyte.jeography.core.mapwindow.MapWindowChangeListener;
import de.topobyte.jeography.core.mapwindow.SteplessMapWindow;
import de.topobyte.jeography.core.mapwindow.SteppedMapWindow;
import de.topobyte.jeography.core.mapwindow.TileMapWindow;
import de.topobyte.jeography.tiles.LoadListener;
import de.topobyte.jeography.tiles.cache.MemoryCachePlus;
import de.topobyte.jeography.tiles.manager.ImageManager;
import de.topobyte.jeography.tiles.manager.ImageManagerHttpDisk;
import de.topobyte.jeography.tiles.manager.ImageManagerUpdateListener;
import de.topobyte.jeography.tiles.manager.PriorityImageManager;
import de.topobyte.jeography.viewer.Constants;
import de.topobyte.jeography.viewer.config.TileConfig;
import de.topobyte.melon.casting.CastUtil;
import java.awt.BasicStroke;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SteplessViewer
   extends AbstractViewer
   implements LoadListener<Tile, BufferedImage>,
   ImageManagerUpdateListener,
   MapWindowChangeListener,
   ZoomChangedListener {
   private static final long serialVersionUID = -2141729332089589643L;
   static final Logger logger = LoggerFactory.getLogger(SteplessViewer.class);
   private SteplessMapWindow mapWindow;
   private TileMapWindow tileMapWindow;
   private double tileScale;
   protected ImageManager<Tile, BufferedImage> imageManagerBase;
   protected ImageManager<Tile, BufferedImage> imageManagerOverlay;
   protected PaintListener globalManager;
   private MemoryCachePlus<Tile, Image> scaleCacheBase;
   private MemoryCachePlus<Tile, Image> scaleCacheOverlay;
   private SteplessViewer.TileDrawMode mode = SteplessViewer.TileDrawMode.SCALE_SMOOTH;
   private double zoomStep = 0.05;

   public SteplessViewer(TileConfig tileConfig, TileConfig tileConfigOverlay) {
      this(tileConfig, tileConfigOverlay, Constants.DEFAULT_ZOOM, Constants.DEFAULT_LON, Constants.DEFAULT_LAT);
   }

   public SteplessViewer(TileConfig tileConfig, TileConfig tileConfigOverlay, int zoom, double lon, double lat) {
      this.mapWindow = new SteplessMapWindow(1, 1, (double)zoom, lon, lat);
      this.mapWindow.setMaxZoom(22);
      this.mapWindow.addChangeListener(this);
      this.mapWindow.addZoomListener(this);
      this.setupTileMapWindow();
      this.setDoubleBuffered(true);
      this.setFocusable(true);
      this.setTileConfig(tileConfig);
      this.setOverlayTileConfig(tileConfigOverlay);
      this.addComponentListener(this);
      this.addMouseListener(this);
      this.addMouseMotionListener(this);
      this.addMouseWheelListener(this);
      AbstractViewer.Repainter repainter = new AbstractViewer.Repainter();
      Thread repainterThread = new Thread(repainter);
      repainterThread.start();
   }

   public SteplessMapWindow getMapWindow() {
      return this.mapWindow;
   }

   public void setNetworkState(boolean state) {
      if (this.imageManagerBase instanceof ImageManagerHttpDisk) {
         ((ImageManagerHttpDisk)this.imageManagerBase).setNetworkState(state);
      }
   }

   public boolean getNetworkState() {
      return this.imageManagerBase instanceof ImageManagerHttpDisk ? ((ImageManagerHttpDisk)this.imageManagerBase).getNetworkState() : false;
   }

   public void setMode(SteplessViewer.TileDrawMode mode) {
      this.mode = mode;
   }

   public SteplessViewer.TileDrawMode getMode() {
      return this.mode;
   }

   @Override
   public void paintComponent(Graphics graphics) {
      Graphics2D g = (Graphics2D)graphics;
      g.setColor(this.colorBackground);
      g.fillRect(0, 0, this.getWidth(), this.getHeight());
      int tw = this.tileMapWindow.getTileWidth();
      int th = this.tileMapWindow.getTileHeight();
      boolean isPriorityManager = this.imageManagerBase instanceof PriorityImageManager;
      this.refreshCache(this.scaleCacheBase);
      this.refreshCache(this.scaleCacheOverlay);
      if (!isPriorityManager) {
         for (TileOnWindow tile : TileUtil.valid(this.tileMapWindow)) {
            this.imageManagerBase.willNeed(tile);
         }

         for (TileOnWindow tile : TileUtil.valid(this.tileMapWindow)) {
            BufferedImage image = (BufferedImage)this.imageManagerBase.get(tile);
            this.drawTile(g, tile, image, this.scaleCacheBase, tw, th);
         }
      } else {
         PriorityImageManager<Tile, BufferedImage, Integer> pimb = (PriorityImageManager<Tile, BufferedImage, Integer>)CastUtil.cast(this.imageManagerBase);
         pimb.cancelJobs();

         for (TileOnWindow tile : TileUtil.valid(this.tileMapWindow)) {
            pimb.willNeed(tile);
         }

         for (TileOnWindow tile : TileUtil.valid(this.tileMapWindow)) {
            int priority = this.calculatePriority(tile);
            BufferedImage image = (BufferedImage)pimb.get(tile, priority);
            this.drawTile(g, tile, image, this.scaleCacheBase, tw, th);
         }
      }

      if (this.globalManager != null) {
         this.globalManager.onPaint(this.tileMapWindow, g);
      }

      boolean overlay = this.drawOverlay && this.imageManagerOverlay != null;
      if (overlay) {
         for (TileOnWindow tile : this.tileMapWindow) {
            this.imageManagerOverlay.willNeed(tile);
         }

         for (TileOnWindow tow : this.tileMapWindow) {
            BufferedImage image = overlay ? (BufferedImage)this.imageManagerOverlay.get(tow) : null;
            if (image != null) {
               this.drawImage(g, image, tow, this.scaleCacheOverlay);
            }
         }
      }

      if (this.drawBorder) {
         GraphicsUtil.useAntialiasing(g, false);
         g.setStroke(new BasicStroke(1.0F));
         g.setColor(this.colorBorder);

         for (TileOnWindow towx : this.tileMapWindow) {
            double mx = (double)(towx.getTx() * 256) * this.tileScale;
            double my = (double)(towx.getTy() * 256) * this.tileScale;
            double ddx = this.mapWindow.mercatorToX(mx);
            double ddy = this.mapWindow.mercatorToY(my);
            int dx = (int)Math.round(ddx);
            int dy = (int)Math.round(ddy);
            towx = new TileOnWindow(towx.getZoom(), towx.getTx(), towx.getTy(), dx, dy);
            g.drawRect(towx.dx, towx.dy, (int)Math.round((double)tw * this.tileScale), (int)Math.round((double)th * this.tileScale));
         }
      }

      if (this.drawTileNumbers) {
         GraphicsUtil.useAntialiasing(g, true);
         Font font = new Font("SansSerif", 1, 12);
         g.setFont(font);
         g.setColor(this.colorTilenumbers);

         for (TileOnWindow towx : this.tileMapWindow) {
            double mx = (double)(towx.getTx() * 256) * this.tileScale;
            double my = (double)(towx.getTy() * 256) * this.tileScale;
            double ddx = this.mapWindow.mercatorToX(mx);
            double ddy = this.mapWindow.mercatorToY(my);
            int dx = (int)Math.round(ddx);
            int dy = (int)Math.round(ddy);
            towx = new TileOnWindow(towx.getZoom(), towx.getTx(), towx.getTy(), dx, dy);
            String tilename = towx.getTx() + "," + towx.getTy();
            g.drawString(tilename, towx.dx + 10, towx.dy + 20);
         }
      }

      if (this.drawCrosshair) {
         this.drawCrosshair(g);
      }

      this.drawOverlayPoints(g);
      this.triggerPaintListeners(g);
   }

   private void refreshCache(MemoryCachePlus<Tile, Image> cache) {
      if (cache != null) {
         for (TileOnWindow tow : this.tileMapWindow) {
            cache.refresh(tow);
         }
      }
   }

   private void drawTile(Graphics2D g, TileOnWindow tile, BufferedImage image, MemoryCachePlus<Tile, Image> scaleCache, int tw, int th) {
      if (image == null) {
         g.setColor(this.colorBackground);
         g.fillRect(tile.dx, tile.dy, tw, th);
      } else {
         if (this.mode == SteplessViewer.TileDrawMode.TRANSFORM) {
            double mx = (double)(tile.getTx() * tw) * this.tileScale;
            double my = (double)(tile.getTy() * th) * this.tileScale;
            double ddx = this.mapWindow.mercatorToX(mx);
            double ddy = this.mapWindow.mercatorToY(my);
            this.drawImage(g, image, ddx, ddy);
         } else {
            this.drawImage(g, image, tile, scaleCache);
         }
      }
   }

   private void drawImage(Graphics2D g, BufferedImage image, double ddx, double ddy) {
      AffineTransform backup = g.getTransform();
      g.translate(ddx, ddy);
      g.scale(this.tileScale, this.tileScale);
      g.drawImage(image, 0, 0, null);
      g.setTransform(backup);
   }

   private void drawImage(Graphics g, BufferedImage image, TileOnWindow tow, MemoryCachePlus<Tile, Image> cache) {
      int tw = (int)Math.round((double)this.tileMapWindow.getTileWidth() * this.tileScale);
      int th = (int)Math.round((double)this.tileMapWindow.getTileHeight() * this.tileScale);
      int atw = this.mapWindow.getWorldScale();
      int ath = this.mapWindow.getWorldScale();
      double ddx = (double)tow.getDX() * this.tileScale;
      double ddy = (double)tow.getDY() * this.tileScale;
      int left = (int)Math.round(ddx);
      int top = (int)Math.round(ddy);
      int right = (int)Math.round(ddx + this.tileScale * (double)atw);
      int bottom = (int)Math.round(ddy + this.tileScale * (double)ath);
      if (image.getWidth() == tw && image.getHeight() == th) {
         g.drawImage(image, tow.dx, tow.dy, null);
      } else if (this.mode == SteplessViewer.TileDrawMode.SCALE_FAST) {
         g.drawImage(image, left, top, right - left, bottom - top, null);
      } else if (this.mode == SteplessViewer.TileDrawMode.SCALE_SMOOTH) {
         Image scaled = (Image)cache.get(tow);
         if (scaled == null) {
            scaled = image.getScaledInstance(tw, th, 4);
            cache.put(tow, scaled);
         }

         g.drawImage(scaled, left, top, right - left, bottom - top, null);
      }
   }

   private int calculatePriority(TileOnWindow tile) {
      int width = this.mapWindow.getWidth();
      int height = this.mapWindow.getHeight();
      int midX = width / 2;
      int midY = height / 2;
      int tX = tile.getDX() + Tile.SIZE / 2;
      int tY = tile.getDY() + Tile.SIZE / 2;
      int dX = tX - midX;
      int dY = tY - midY;
      return dX * dX + dY * dY;
   }

   @Override
   public void componentResized(ComponentEvent e) {
      int width = this.getWidth();
      int height = this.getHeight();
      this.mapWindow.resize(width, height);
      this.repaint();
   }

   @Override
   public void mouseClicked(MouseEvent e) {
      this.grabFocus();
      if (this.mouseActive && e.getClickCount() == 2) {
         if (e.getButton() == 1) {
            this.zoomIn(e.getPoint(), this.zoomStep);
         } else if (e.getButton() == 3) {
            this.zoomOut(e.getPoint(), this.zoomStep);
         }

         this.repaint();
      }

      for (MouseListener listener : this.mouseListeners) {
         listener.mouseClicked(e);
      }
   }

   @Override
   public void mouseWheelMoved(MouseWheelEvent e) {
      int scrollType = e.getScrollType();
      if (scrollType == 0) {
         int wheelRotation = e.getWheelRotation();
         if (wheelRotation < 0) {
            this.zoomIn(e.getPoint(), this.zoomStep);
         } else {
            this.zoomOut(e.getPoint(), this.zoomStep);
         }

         this.repaint();
      }
   }

   public void loadFailed(Tile thing) {
   }

   public void loaded(Tile thing, BufferedImage data) {
      this.dispatchRepaint();
   }

   private void zoomIn(Point point, double zoomStep) {
      switch (this.zoomMode) {
         case ZOOM_AT_CENTER:
         default:
            this.mapWindow.zoomIn(zoomStep);
            break;
         case ZOOM_AND_CENTER_POINT:
            this.mapWindow.zoomInToPosition(point.x, point.y, zoomStep);
            break;
         case ZOOM_AND_KEEP_POINT:
            this.zoomFixed(point.x, point.y, true, zoomStep);
      }
   }

   private void zoomOut(Point point, double zoomStep) {
      switch (this.zoomMode) {
         case ZOOM_AT_CENTER:
         default:
            this.mapWindow.zoomOut(zoomStep);
            break;
         case ZOOM_AND_CENTER_POINT:
            this.mapWindow.zoomOutToPosition(point.x, point.y, zoomStep);
            break;
         case ZOOM_AND_KEEP_POINT:
            this.zoomFixed(point.x, point.y, false, zoomStep);
      }
   }

   private void zoomFixed(int x, int y, boolean in, double zoomStep) {
      double flon = this.mapWindow.getPositionLon(x);
      double flat = this.mapWindow.getPositionLat(y);
      if (in) {
         this.mapWindow.zoomIn(zoomStep);
      } else {
         this.mapWindow.zoomOut(zoomStep);
      }

      double fx = this.mapWindow.getX(flon);
      double fy = this.mapWindow.getY(flat);
      this.mapWindow.move((int)Math.round(fx - (double)x), (int)Math.round(fy - (double)y));
   }

   public Coordinate getMouseGeoPosition() {
      Point position = this.getMousePosition();
      if (position == null) {
         return null;
      } else {
         double lon = this.mapWindow.getPositionLon(position.x);
         double lat = this.mapWindow.getPositionLat(position.y);
         return new Coordinate(lon, lat);
      }
   }

   public void setTileConfig(TileConfig tileConfig) {
      ImageManager<Tile, BufferedImage> imageManager = tileConfig.createImageManager();
      if (imageManager != null) {
         this.tileConfig = tileConfig;
         this.imageManagerBase = imageManager;
         this.imageManagerBase.addLoadListener(this);
         this.scaleCacheBase = new MemoryCachePlus(this.tileMapWindow.minimumCacheSize());
         this.globalManager = tileConfig.createGlobalManager();
         this.triggerTileConfigListeners();
         this.dispatchRepaint();
      }
   }

   public void setOverlayTileConfig(TileConfig config) {
      this.overlayTileConfig = config;
      if (config != null) {
         ImageManager<Tile, BufferedImage> imageManager = config.createImageManager();
         if (imageManager == null) {
            return;
         }

         this.imageManagerOverlay = imageManager;
         if (this.imageManagerOverlay instanceof ImageManagerHttpDisk) {
            ((ImageManagerHttpDisk)this.imageManagerOverlay).setNetworkState(true);
         }

         this.imageManagerOverlay.addLoadListener(this);
         this.scaleCacheOverlay = new MemoryCachePlus(this.tileMapWindow.minimumCacheSize());
         this.triggerOverlayTileConfigListeners();
         this.dispatchRepaint();
      }
   }

   private void triggerPaintListeners(Graphics g) {
      synchronized (this.paintListeners) {
         for (PaintListener pl : this.paintListeners) {
            pl.onPaint(this.tileMapWindow, g);
         }
      }
   }

   public double getZoomLevel() {
      return this.mapWindow.getZoom();
   }

   public int getMinZoomLevel() {
      return 1;
   }

   public int getMaxZoomLevel() {
      return 22;
   }

   public void updated() {
      this.repaint();
   }

   public ImageManager<Tile, BufferedImage> getImageManagerBase() {
      return this.imageManagerBase;
   }

   public ImageManager<Tile, BufferedImage> getImageManagerOverlay() {
      return this.imageManagerOverlay;
   }

   public void changed() {
      this.setupTileMapWindow();
      this.ensureCacheSize();
   }

   public void zoomChanged() {
      if (this.scaleCacheBase != null) {
         this.scaleCacheBase.clear();
      }

      if (this.scaleCacheOverlay != null) {
         this.scaleCacheOverlay.clear();
      }

      this.ensureCacheSize();
   }

   private void ensureCacheSize() {
      int cacheSize = this.tileMapWindow.minimumCacheSize();
      if (this.scaleCacheBase != null) {
         this.scaleCacheBase.setSize(cacheSize);
      }

      if (this.scaleCacheOverlay != null) {
         this.scaleCacheOverlay.setSize(cacheSize);
      }

      if (this.imageManagerBase != null) {
         this.imageManagerBase.setCacheHintMinimumSize(cacheSize);
      }

      if (this.imageManagerOverlay != null) {
         this.imageManagerOverlay.setCacheHintMinimumSize(cacheSize);
      }
   }

   private void setupTileMapWindow() {
      BBox bbox = this.mapWindow.getBoundingBox();
      double realZoom = this.mapWindow.getZoom();
      int tileZoom = (int)Math.round(realZoom);
      this.tileMapWindow = new SteppedMapWindow(bbox, tileZoom);
      this.tileScale = Math.pow(2.0, realZoom - (double)tileZoom);
      logger.info("real zoom: " + realZoom);
      logger.info("tile zoom: " + tileZoom);
      logger.info("scale factor: " + this.tileScale);
   }

   public void setTileSize(int tileSize) {
      boolean changed = this.mapWindow.setWorldScale(tileSize);
      if (changed) {
         if (this.scaleCacheBase != null) {
            this.scaleCacheBase.clear();
         }

         if (this.scaleCacheOverlay != null) {
            this.scaleCacheOverlay.clear();
         }

         this.tileSizeChanged(tileSize);
         this.repaint();
      }
   }

   protected void tileSizeChanged(int tileSize) {
   }

   public static enum TileDrawMode {
      TRANSFORM,
      SCALE_FAST,
      SCALE_SMOOTH;
   }
}
