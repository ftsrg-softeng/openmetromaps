package de.topobyte.jeography.core.viewbounds;

public class NopViewBounds implements ViewBounds {
   @Override
   public BoundsInfo checkBounds(double lon, double lat) {
      return BoundsInfo.OK;
   }

   @Override
   public double fixLon(double lon) {
      return lon;
   }

   @Override
   public double fixLat(double lat) {
      return lat;
   }
}
