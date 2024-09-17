package org.openmetromaps.gtfs4j.csvwriter;

import java.io.IOException;
import java.io.Writer;
import java.util.List;
import org.openmetromaps.gtfs4j.csv.Stops;
import org.openmetromaps.gtfs4j.model.Stop;

public class StopsWriter extends BaseWriter<Stop, Stops> {
   public StopsWriter(Writer writer, List<Stops> fields) throws IOException {
      super(writer, Stops.class, fields);
   }

   public String get(Stop object, Stops field) {
      switch (field) {
         case CODE:
            return object.getCode();
         case DESC:
            return object.getDesc();
         case ID:
            return object.getId();
         case LAT:
            return object.getLat();
         case LOCATION_TYPE:
            return object.getLocationType();
         case LON:
            return object.getLon();
         case NAME:
            return object.getName();
         case PARENT_STATION:
            return object.getParentStation();
         case TIMEZONE:
            return object.getTimezone();
         case URL:
            return object.getUrl();
         case WHEELCHAIR_BOARDING:
            return object.getWheelchairBoarding();
         case ZONE_ID:
            return object.getZoneId();
         default:
            return null;
      }
   }
}
