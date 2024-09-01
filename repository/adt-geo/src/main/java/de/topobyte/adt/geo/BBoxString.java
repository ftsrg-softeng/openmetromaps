package de.topobyte.adt.geo;

public class BBoxString {
   public double lon1;
   public double lon2;
   public double lat1;
   public double lat2;

   public static BBoxString parse(String string) {
      BBoxString bbox = new BBoxString();
      String[] splitted = string.split(",");
      if (splitted.length == 4) {
         bbox.lon1 = Double.valueOf(splitted[0]);
         bbox.lat1 = Double.valueOf(splitted[1]);
         bbox.lon2 = Double.valueOf(splitted[2]);
         bbox.lat2 = Double.valueOf(splitted[3]);
      }

      return bbox;
   }

   public static BBoxString create(BBox box) {
      BBoxString bbox = new BBoxString();
      bbox.lon1 = box.getLon1();
      bbox.lat1 = box.getLat1();
      bbox.lon2 = box.getLon2();
      bbox.lat2 = box.getLat2();
      return bbox;
   }

   @Override
   public String toString() {
      return String.format("%f,%f,%f,%f", this.lon1, this.lat1, this.lon2, this.lat2);
   }

   public BBox toBbox() {
      return new BBox(this.lon1, this.lat1, this.lon2, this.lat2);
   }
}
