package de.topobyte.jeography.core;

import java.util.Collection;

public class OverlayPoint {
   private double lon;
   private double lat;

   public OverlayPoint(double lon, double lat) {
      this.lon = lon;
      this.lat = lat;
   }

   public double getLongitude() {
      return this.lon;
   }

   public double getLatitude() {
      return this.lat;
   }

   public static OverlayPoint mean(Collection<OverlayPoint> points) {
      double lon = 0.0;
      double lat = 0.0;

      for (OverlayPoint p : points) {
         lon += p.getLongitude();
         lat += p.getLatitude();
      }

      lon /= (double)points.size();
      lat /= (double)points.size();
      return new OverlayPoint(lon, lat);
   }

   public static OverlayPoint minimum(Collection<OverlayPoint> points) {
      double lon = Double.POSITIVE_INFINITY;
      double lat = Double.POSITIVE_INFINITY;

      for (OverlayPoint p : points) {
         if (p.getLongitude() < lon) {
            lon = p.getLongitude();
         }

         if (p.getLatitude() < lat) {
            lat = p.getLatitude();
         }
      }

      return new OverlayPoint(lon, lat);
   }

   public static OverlayPoint maximum(Collection<OverlayPoint> points) {
      double lon = Double.NEGATIVE_INFINITY;
      double lat = Double.NEGATIVE_INFINITY;

      for (OverlayPoint p : points) {
         if (p.getLongitude() > lon) {
            lon = p.getLongitude();
         }

         if (p.getLatitude() > lat) {
            lat = p.getLatitude();
         }
      }

      return new OverlayPoint(lon, lat);
   }

   @Override
   public String toString() {
      return this.lon + "," + this.lat;
   }
}
