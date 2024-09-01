package org.openmetromaps.gtfs4j.csv;

public enum Routes implements Field {
   ID("route_id", true),
   AGENCY_ID("agency_id", false),
   SHORT_NAME("route_short_name", true),
   LONG_NAME("route_long_name", true),
   DESC("route_desc", false),
   TYPE("route_type", true),
   URL("route_url", false),
   COLOR("route_color", false),
   TEXT_COLOR("route_text_color", false);

   private String csvName;
   private boolean required;

   private Routes(String csvName, boolean required) {
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
