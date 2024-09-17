package org.openmetromaps.gtfs4j.csv;

public enum StopTimes implements Field {
   TRIP_ID("trip_id", true),
   ARRVIAL_TIME("arrival_time", true),
   DEPARTURE_TIME("departure_time", true),
   STOP_ID("stop_id", true),
   STOP_SEQUENCE("stop_sequence", true),
   STOP_HEADSIGN("stop_headsign", false),
   PICKUP_TYPE("pickup_type", false),
   DROP_OFF_TYPE("drop_off_type", false),
   SHAPE_DIST_TRAVELED("shape_dist_traveled", false),
   TIMEPOINT("timepoint", false);

   private String csvName;
   private boolean required;

   private StopTimes(String csvName, boolean required) {
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
