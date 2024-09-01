package de.topobyte.geomath;

public class WGS84 {
   private static final double RADIUS_EARTH = 6371008.67;

   public static double merc2lon(double x) {
      return x * 360.0 - 180.0;
   }

   public static double merc2lon(double x, double worldsize) {
      return merc2lon(x / worldsize);
   }

   public static double merc2lat(double y) {
      double n = Math.PI - (Math.PI * 2) * y;
      return (180.0 / Math.PI) * Math.atan(0.5 * (Math.exp(n) - Math.exp(-n)));
   }

   public static double merc2lat(double y, double worldsize) {
      return merc2lat(y / worldsize);
   }

   public static double lon2merc(double lon) {
      return (lon + 180.0) / 360.0;
   }

   public static double lon2merc(double lon, double worldsize) {
      return lon2merc(lon) * worldsize;
   }

   public static double lat2merc(double lat) {
      double rlat = Math.toRadians(lat);
      double cos = Math.cos(rlat);
      double sin = Math.sin(rlat);
      double tan = sin / cos;
      return (1.0 - Math.log(tan + 1.0 / cos) / Math.PI) / 2.0;
   }

   public static double lat2merc(double lat, double worldsize) {
      return lat2merc(lat) * worldsize;
   }

   public static double haversineDistance(double lon1, double lat1, double lon2, double lat2) {
      double dLat = Math.toRadians(lat2 - lat1);
      double dLon = Math.toRadians(lon2 - lon1);
      double a = Math.sin(dLat / 2.0) * Math.sin(dLat / 2.0)
         + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) * Math.sin(dLon / 2.0) * Math.sin(dLon / 2.0);
      double c = 2.0 * Math.atan2(Math.sqrt(a), Math.sqrt(1.0 - a));
      return c * 6371008.67;
   }
}
