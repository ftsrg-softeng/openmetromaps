package de.topobyte.jeography.viewer.core;

import de.topobyte.adt.geo.Coordinate;
import de.topobyte.awt.util.GraphicsUtil;
import de.topobyte.jeography.core.Tile;
import de.topobyte.jeography.core.TileOnWindow;
import de.topobyte.jeography.core.TileUtil;
import de.topobyte.jeography.core.mapwindow.MapWindowChangeListener;
import de.topobyte.jeography.core.mapwindow.SteppedMapWindow;
import de.topobyte.jeography.core.mapwindow.TileMapWindow;
import de.topobyte.jeography.tiles.LoadListener;
import de.topobyte.jeography.tiles.cache.MemoryCachePlus;
import de.topobyte.jeography.tiles.manager.ImageManager;
import de.topobyte.jeography.tiles.manager.ImageManagerHttpDisk;
import de.topobyte.jeography.tiles.manager.ImageManagerUpdateListener;
import de.topobyte.jeography.tiles.manager.PriorityImageManager;
import de.topobyte.jeography.tiles.manager.PriorityImageManagerHttpDisk;
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
import java.awt.image.BufferedImage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Viewer extends AbstractViewer implements LoadListener<Tile, BufferedImage>, ImageManagerUpdateListener, MapWindowChangeListener {
   private static final long serialVersionUID = -2141729332089589643L;
   static final Logger logger = LoggerFactory.getLogger(Viewer.class);
   private TileMapWindow mapWindow;
   protected ImageManager<Tile, BufferedImage> imageManagerBase;
   protected ImageManager<Tile, BufferedImage> imageManagerOverlay;
   protected PaintListener globalManager;
   private MemoryCachePlus<Tile, Image> scaleCacheBase;
   private MemoryCachePlus<Tile, Image> scaleCacheOverlay;
   private boolean scaleFast = false;

   public Viewer(TileConfig tileConfig, TileConfig tileConfigOverlay) {
      this(tileConfig, tileConfigOverlay, Constants.DEFAULT_ZOOM, Constants.DEFAULT_LON, Constants.DEFAULT_LAT);
   }

   public Viewer(TileConfig tileConfig, TileConfig tileConfigOverlay, int zoom, double lon, double lat) {
      this.mapWindow = new SteppedMapWindow(1, 1, zoom, lon, lat);
      this.mapWindow.setMaxZoom(22);
      this.mapWindow.addChangeListener(this);
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

   public TileMapWindow getMapWindow() {
      return this.mapWindow;
   }

   public void setNetworkState(boolean state) {
      if (this.imageManagerBase instanceof ImageManagerHttpDisk) {
         ((ImageManagerHttpDisk)this.imageManagerBase).setNetworkState(state);
      } else if (this.imageManagerBase instanceof PriorityImageManagerHttpDisk) {
         ((PriorityImageManagerHttpDisk)this.imageManagerBase).setNetworkState(state);
      }
   }

   public boolean getNetworkState() {
      if (this.imageManagerBase instanceof ImageManagerHttpDisk) {
         return ((ImageManagerHttpDisk)this.imageManagerBase).getNetworkState();
      } else {
         return this.imageManagerBase instanceof PriorityImageManagerHttpDisk ? ((PriorityImageManagerHttpDisk)this.imageManagerBase).getNetworkState() : false;
      }
   }

   @Override
   public void paintComponent(Graphics graphics) {
      Graphics2D g = (Graphics2D)graphics;
      g.setColor(this.colorBackground);
      g.fillRect(0, 0, this.getWidth(), this.getHeight());
      int tw = this.mapWindow.getTileWidth();
      int th = this.mapWindow.getTileHeight();
      boolean isPriorityManager = this.imageManagerBase instanceof PriorityImageManager;
      this.refreshCache(this.scaleCacheBase);
      this.refreshCache(this.scaleCacheOverlay);
      if (!isPriorityManager) {
         for (TileOnWindow tile : TileUtil.valid(this.mapWindow)) {
            this.imageManagerBase.willNeed(tile);
         }

         for (TileOnWindow tow : TileUtil.valid(this.mapWindow)) {
            BufferedImage image = (BufferedImage)this.imageManagerBase.get(tow);
            if (image != null) {
               this.drawImage(g, image, tow, this.scaleCacheBase);
            } else {
               g.setColor(this.colorBackground);
               g.fillRect(tow.dx, tow.dy, tw, th);
            }
         }
      } else {
         PriorityImageManager<Tile, BufferedImage, Integer> pimb = (PriorityImageManager<Tile, BufferedImage, Integer>)CastUtil.cast(this.imageManagerBase);
         pimb.cancelJobs();

         for (TileOnWindow tile : TileUtil.valid(this.mapWindow)) {
            pimb.willNeed(tile);
         }

         for (TileOnWindow tile : TileUtil.valid(this.mapWindow)) {
            int priority = this.calculatePriority(tile);
            BufferedImage image = (BufferedImage)pimb.get(tile, priority);
            if (image != null) {
               this.drawImage(g, image, tile, this.scaleCacheBase);
            }
         }
      }

      if (this.globalManager != null) {
         this.globalManager.onPaint(this.mapWindow, g);
      }

      boolean overlay = this.drawOverlay && this.imageManagerOverlay != null;
      if (overlay) {
         for (TileOnWindow tilex : this.getMapWindow()) {
            this.imageManagerOverlay.willNeed(tilex);
         }

         for (TileOnWindow towx : this.mapWindow) {
            BufferedImage image = overlay ? (BufferedImage)this.imageManagerOverlay.get(towx) : null;
            if (image != null) {
               this.drawImage(g, image, towx, this.scaleCacheOverlay);
            }
         }
      }

      if (this.drawBorder) {
         GraphicsUtil.useAntialiasing(g, false);
         g.setStroke(new BasicStroke(1.0F));
         g.setColor(this.colorBorder);

         for (TileOnWindow towxx : this.mapWindow) {
            g.drawRect(towxx.dx, towxx.dy, tw, th);
         }
      }

      if (this.drawTileNumbers) {
         GraphicsUtil.useAntialiasing(g, true);
         Font font = new Font("SansSerif", 1, 12);
         g.setFont(font);
         g.setColor(this.colorTilenumbers);

         for (TileOnWindow towxx : this.mapWindow) {
            String tilename = towxx.getTx() + "," + towxx.getTy();
            g.drawString(tilename, towxx.dx + 10, towxx.dy + 20);
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
         for (TileOnWindow tow : this.mapWindow) {
            cache.refresh(tow);
         }
      }
   }

   private void drawImage(Graphics g, BufferedImage image, TileOnWindow tow, MemoryCachePlus<Tile, Image> cache) {
      int tw = this.mapWindow.getTileWidth();
      int th = this.mapWindow.getTileHeight();
      if (image.getWidth() == tw && image.getHeight() == th) {
         g.drawImage(image, tow.dx, tow.dy, null);
      } else if (this.scaleFast) {
         g.drawImage(image, tow.dx, tow.dy, tw, th, null);
      } else {
         Image scaled = (Image)cache.get(tow);
         if (scaled == null) {
            scaled = image.getScaledInstance(tw, th, 4);
            cache.put(tow, scaled);
         }

         g.drawImage(scaled, tow.dx, tow.dy, null);
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
            this.zoomIn(e.getPoint());
         } else if (e.getButton() == 3) {
            this.zoomOut(e.getPoint());
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
            this.zoomIn(e.getPoint());
         } else {
            this.zoomOut(e.getPoint());
         }

         this.repaint();
      }
   }

   public void loadFailed(Tile thing) {
   }

   public void loaded(Tile thing, BufferedImage data) {
      this.dispatchRepaint();
   }

   private void zoomIn(Point point) {
      switch (this.zoomMode) {
         case ZOOM_AT_CENTER:
         default:
            this.mapWindow.zoomIn();
            break;
         case ZOOM_AND_CENTER_POINT:
            this.mapWindow.zoomInToPosition(point.x, point.y);
            break;
         case ZOOM_AND_KEEP_POINT:
            this.zoomFixed(point.x, point.y, true);
      }
   }

   private void zoomOut(Point point) {
      switch (this.zoomMode) {
         case ZOOM_AT_CENTER:
         default:
            this.mapWindow.zoomOut();
            break;
         case ZOOM_AND_CENTER_POINT:
            this.mapWindow.zoomOutToPosition(point.x, point.y);
            break;
         case ZOOM_AND_KEEP_POINT:
            this.zoomFixed(point.x, point.y, false);
      }
   }

   private void zoomFixed(int x, int y, boolean in) {
      double flon = this.mapWindow.getPositionLon(x);
      double flat = this.mapWindow.getPositionLat(y);
      if (in) {
         this.mapWindow.zoomIn();
      } else {
         this.mapWindow.zoomOut();
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
         this.scaleCacheBase = new MemoryCachePlus(this.mapWindow.minimumCacheSize());
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
         this.scaleCacheOverlay = new MemoryCachePlus(this.mapWindow.minimumCacheSize());
         this.triggerOverlayTileConfigListeners();
         this.dispatchRepaint();
      }
   }

   private void triggerPaintListeners(Graphics g) {
      synchronized (this.paintListeners) {
         for (PaintListener pl : this.paintListeners) {
            pl.onPaint(this.mapWindow, g);
         }
      }
   }

   public int getZoomLevel() {
      return this.mapWindow.getZoomLevel();
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
      int cacheSize = this.mapWindow.minimumCacheSize();
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

   public void setTileSize(int tileSize) {
      boolean changed = this.mapWindow.setTileSize(tileSize);
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
}
