package org.openmetromaps.gtfs4j.csv;

public enum Agencies implements Field {
   ID("agency_id", false),
   NAME("agency_name", true),
   URL("agency_url", true),
   TIMEZONE("agency_timezone", true),
   LANG("agency_lang", false),
   PHONE("agency_phone", false),
   FARE_URL("agency_fare_url", false),
   EMAIL("agency_email", false);

   private String csvName;
   private boolean required;

   private Agencies(String csvName, boolean required) {
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
