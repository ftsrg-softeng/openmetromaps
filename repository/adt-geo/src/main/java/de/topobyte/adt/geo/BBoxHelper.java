package de.topobyte.adt.geo;

import java.util.Collection;

public class BBoxHelper {
   public static BBox minimumBoundingBox(Coordinate... coordinates) {
      if (coordinates.length == 0) {
         return null;
      } else {
         BBox bbox = new BBox(0.0, 0.0, 0.0, 0.0);
         minimumBoundingBox(bbox, coordinates);
         return bbox;
      }
   }

   public static void minimumBoundingBox(BBox bbox, Coordinate... coordinates) {
      Coordinate c = coordinates[0];
      double minLat = c.getLatitude();
      double maxLat = c.getLatitude();
      double minLon = c.getLongitude();
      double maxLon = c.getLongitude();

      for (Coordinate var17 : coordinates) {
         double lon = var17.lon;
         if (lon < minLon) {
            minLon = lon;
         }

         if (lon > maxLon) {
            maxLon = lon;
         }

         double lat = var17.lat;
         if (lat < minLat) {
            minLat = lat;
         }

         if (lat > maxLat) {
            maxLat = lat;
         }
      }

      bbox.set(minLon, maxLat, maxLon, minLat);
   }

   public static BBox minimumBoundingBox(Collection<Coordinate> coordinates) {
      if (coordinates.size() == 0) {
         return null;
      } else {
         BBox bbox = new BBox(0.0, 0.0, 0.0, 0.0);
         minimumBoundingBox(bbox, coordinates);
         return bbox;
      }
   }

   public static void minimumBoundingBox(BBox bbox, Collection<Coordinate> coordinates) {
      Coordinate c = coordinates.iterator().next();
      double minLat = c.getLatitude();
      double maxLat = c.getLatitude();
      double minLon = c.getLongitude();
      double maxLon = c.getLongitude();

      for (Coordinate var16 : coordinates) {
         double lon = var16.lon;
         if (lon < minLon) {
            minLon = lon;
         }

         if (lon > maxLon) {
            maxLon = lon;
         }

         double lat = var16.lat;
         if (lat < minLat) {
            minLat = lat;
         }

         if (lat > maxLat) {
            maxLat = lat;
         }
      }

      bbox.set(minLon, minLat, maxLon, maxLat);
   }

   public static void scaleByFactor(BBox box, double factor) {
      double lon1 = box.getLon1();
      double lon2 = box.getLon2();
      double lat1 = box.getLat1();
      double lat2 = box.getLat2();
      double width = lon2 - lon1;
      double height = lat1 - lat2;
      double dLon = (factor - 1.0) / 2.0 * width;
      double dLat = (factor - 1.0) / 2.0 * height;
      box.set(lon1 - dLon, lat1 + dLat, lon2 + dLon, lat2 - dLat);
   }
}
