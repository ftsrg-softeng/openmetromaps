package de.topobyte.jeography.core.viewbounds;

import de.topobyte.adt.geo.BBox;

public class BboxViewBounds implements ViewBounds {
   private double minLon;
   private double maxLon;
   private double minLat;
   private double maxLat;

   public BboxViewBounds(BBox bbox) {
      this.minLon = bbox.getLon1();
      this.maxLon = bbox.getLon2();
      this.minLat = bbox.getLat2();
      this.maxLat = bbox.getLat1();
   }

   @Override
   public BoundsInfo checkBounds(double lon, double lat) {
      boolean lonOk = lon >= this.minLon && lon <= this.maxLon;
      boolean latOk = lat >= this.minLat && lat <= this.maxLat;
      if (lonOk && latOk) {
         return BoundsInfo.OK;
      } else if (!lonOk && !latOk) {
         return BoundsInfo.LON_LAT_OUT_OF_BOUNDS;
      } else {
         return !lonOk ? BoundsInfo.LON_OUT_OF_BOUNDS : BoundsInfo.LAT_OUT_OF_BOUNDS;
      }
   }

   @Override
   public double fixLon(double lon) {
      if (lon > this.maxLon) {
         return this.maxLon;
      } else {
         return lon < this.minLon ? this.minLon : lon;
      }
   }

   @Override
   public double fixLat(double lat) {
      if (lat > this.maxLat) {
         return this.maxLat;
      } else {
         return lat < this.minLat ? this.minLat : lat;
      }
   }
}
