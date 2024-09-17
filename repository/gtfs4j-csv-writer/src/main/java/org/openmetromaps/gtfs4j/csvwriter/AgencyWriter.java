package org.openmetromaps.gtfs4j.csvwriter;

import java.io.IOException;
import java.io.Writer;
import java.util.List;
import org.openmetromaps.gtfs4j.csv.Agencies;
import org.openmetromaps.gtfs4j.model.Agency;

public class AgencyWriter extends BaseWriter<Agency, Agencies> {
   public AgencyWriter(Writer writer, List<Agencies> fields) throws IOException {
      super(writer, Agencies.class, fields);
   }

   public String get(Agency object, Agencies field) {
      switch (field) {
         case EMAIL:
            return object.getEmail();
         case FARE_URL:
            return object.getFareUrl();
         case ID:
            return object.getId();
         case LANG:
            return object.getLang();
         case NAME:
            return object.getName();
         case PHONE:
            return object.getPhone();
         case TIMEZONE:
            return object.getTimezone();
         case URL:
            return object.getUrl();
         default:
            return null;
      }
   }
}
