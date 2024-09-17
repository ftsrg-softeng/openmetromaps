package de.topobyte.adt.geo;

import java.util.Collection;

public class Coordinate {
   public double lon;
   public double lat;

   public Coordinate(double lon, double lat) {
      this.lon = lon;
      this.lat = lat;
   }

   public double getLongitude() {
      return this.lon;
   }

   public double getLatitude() {
      return this.lat;
   }

   @Override
   public String toString() {
      return String.format("%f;%f", this.lon, this.lat);
   }

   public static Coordinate mean(Collection<Coordinate> coords) {
      Coordinate m = new Coordinate(0.0, 0.0);

      for (Coordinate c : coords) {
         m.lon = m.lon + c.lon;
         m.lat = m.lat + c.lat;
      }

      m.lon = m.lon / (double)coords.size();
      m.lat = m.lat / (double)coords.size();
      return m;
   }

   public static Coordinate minimum(Collection<Coordinate> coords) {
      double lon = Double.POSITIVE_INFINITY;
      double lat = Double.POSITIVE_INFINITY;

      for (Coordinate c : coords) {
         if (c.lon < lon) {
            lon = c.lon;
         }

         if (c.lat < lat) {
            lat = c.lat;
         }
      }

      return new Coordinate(lon, lat);
   }

   public static Coordinate maximum(Collection<Coordinate> coords) {
      double lon = Double.NEGATIVE_INFINITY;
      double lat = Double.NEGATIVE_INFINITY;

      for (Coordinate c : coords) {
         if (c.lon > lon) {
            lon = c.lon;
         }

         if (c.lat > lat) {
            lat = c.lat;
         }
      }

      return new Coordinate(lon, lat);
   }
}
