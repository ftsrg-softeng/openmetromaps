package de.topobyte.jeography.core;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import de.topobyte.adt.geo.BBox;
import de.topobyte.geomath.WGS84;

public class TileUtil {
   public static BBox getBoundingBox(Tile tile) {
      double lat1 = WGS84.merc2lat((double)tile.getTy(), (double)(1 << tile.getZoom()));
      double lon1 = WGS84.merc2lon((double)tile.getTx(), (double)(1 << tile.getZoom()));
      double lat2 = WGS84.merc2lat((double)(tile.getTy() + 1), (double)(1 << tile.getZoom()));
      double lon2 = WGS84.merc2lon((double)(tile.getTx() + 1), (double)(1 << tile.getZoom()));
      return new BBox(lon1, lat1, lon2, lat2);
   }

   public static BBox getBoundingBox(double txStart, double txEnd, double tyStart, double tyEnd, int zoom) {
      double lat1 = WGS84.merc2lat(tyStart, (double)(1 << zoom));
      double lon1 = WGS84.merc2lon(txStart, (double)(1 << zoom));
      double lat2 = WGS84.merc2lat(tyEnd, (double)(1 << zoom));
      double lon2 = WGS84.merc2lon(txEnd, (double)(1 << zoom));
      return new BBox(lon1, lat1, lon2, lat2);
   }

   public static boolean isValid(Tile tile) {
      int zoom = tile.getZoom();
      int max = 1 << zoom;
      int tx = tile.getTx();
      int ty = tile.getTy();
      return tx >= 0 && ty >= 0 && tx < max && ty < max;
   }

   public static <T extends Tile> Iterable<T> valid(Iterable<T> iterable) {
      return Iterables.filter(iterable, new Predicate<T>() {
         public boolean apply(T tile) {
            return TileUtil.isValid(tile);
         }
      });
   }
}
