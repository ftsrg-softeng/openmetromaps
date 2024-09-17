package org.openmetromaps.gtfs4j.csv;

public enum Calendars implements Field {
   SERVICE_ID("service_id", true),
   MONDAY("monday", true),
   TUESDAY("tuesday", true),
   WEDNESDAY("wednesday", true),
   THURSDAY("thursday", true),
   FRIDAY("friday", true),
   SATURDAY("saturday", true),
   SUNDAY("sunday", true),
   START_DATE("start_date", true),
   END_DATE("end_date", true);

   private String csvName;
   private boolean required;

   private Calendars(String csvName, boolean required) {
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
