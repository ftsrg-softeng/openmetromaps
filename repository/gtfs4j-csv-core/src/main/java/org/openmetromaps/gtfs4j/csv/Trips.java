package org.openmetromaps.gtfs4j.csv;

public enum Trips implements Field {
   ROUTE_ID("route_id", true),
   SERVICE_ID("service_id", true),
   ID("trip_id", true),
   HEADSIGN("trip_headsign", false),
   SHORT_NAME("trip_short_name", false),
   DIRECTION_ID("direction_id", false),
   BLOCK_ID("block_id", false),
   SHAPE_ID("shape_id", false),
   WHEELCHAIR_ACCESSIBLE("wheelchair_accessible", false),
   BIKES_ALLOWED("bikes_allowed", false);

   private String csvName;
   private boolean required;

   private Trips(String csvName, boolean required) {
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
