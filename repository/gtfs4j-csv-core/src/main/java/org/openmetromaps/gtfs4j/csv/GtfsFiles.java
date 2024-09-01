package org.openmetromaps.gtfs4j.csv;

public enum GtfsFiles {
   AGENCY("agency.txt", true, Agencies.class),
   STOPS("stops.txt", true, Stops.class),
   ROUTES("routes.txt", true, Routes.class),
   TRIPS("trips.txt", true, Trips.class),
   STOP_TIMES("stop_times.txt", true, StopTimes.class),
   CALENDAR("calendar.txt", true, Calendars.class),
   CALENDAR_DATES("calendar_dates.txt", false, null),
   FARE_ATTRIBUTES("fare_attributes.txt", false, null),
   FARE_RULES("fare_rules.txt", false, null),
   SHAPES("shapes.txt", false, null),
   FREQUENCIES("frequencies.txt", false, null),
   TRANSFERS("transfers.txt", false, null),
   FEED_INFO("feed_info.txt", false, null);

   private String filename;
   private boolean required;
   private Class<? extends Field> clazz;

   private GtfsFiles(String filename, boolean required, Class<? extends Field> clazz) {
      this.filename = filename;
      this.required = required;
      this.clazz = clazz;
   }

   public String getFilename() {
      return this.filename;
   }

   public boolean isRequired() {
      return this.required;
   }

   public Class<? extends Field> getFieldClass() {
      return this.clazz;
   }
}
