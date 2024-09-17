package org.openmetromaps.gtfs4j.csv;

public enum Stops implements Field {
   ID("stop_id", true),
   CODE("stop_code", false),
   NAME("stop_name", true),
   DESC("stop_desc", false),
   LAT("stop_lat", true),
   LON("stop_lon", true),
   ZONE_ID("zone_id", false),
   URL("stop_url", false),
   LOCATION_TYPE("location_type", false),
   PARENT_STATION("parent_station", false),
   TIMEZONE("stop_timezone", false),
   WHEELCHAIR_BOARDING("wheelchair_boarding", false);

   private String csvName;
   private boolean required;

   private Stops(String csvName, boolean required) {
      this.csvName = csvName;
      this.required = required;
   }

   @Override
   public String getCsvName() {
      return this.csvName;
   }

   @Override
   public boolean isRequired() {
      return this.required;
   }
}
