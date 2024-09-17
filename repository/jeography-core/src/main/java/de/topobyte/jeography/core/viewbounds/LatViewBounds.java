package de.topobyte.jeography.core.viewbounds;

public class LatViewBounds implements ViewBounds {
   private double minLat;
   private double maxLat;

   public LatViewBounds(double minLat, double maxLat) {
      this.minLat = minLat;
      this.maxLat = maxLat;
   }

   @Override
   public BoundsInfo checkBounds(double lon, double lat) {
      if (lat > this.maxLat) {
         return BoundsInfo.LAT_OUT_OF_BOUNDS;
      } else {
         return lat < this.minLat ? BoundsInfo.LAT_OUT_OF_BOUNDS : BoundsInfo.OK;
      }
   }

   @Override
   public double fixLon(double lon) {
      return lon;
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
