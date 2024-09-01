package org.openmetromaps.gtfs4j.csvwriter;

import java.io.IOException;
import java.io.Writer;
import java.util.List;
import org.openmetromaps.gtfs4j.csv.Calendars;
import org.openmetromaps.gtfs4j.model.Calendar;

public class CalendarWriter extends BaseWriter<Calendar, Calendars> {
   public CalendarWriter(Writer writer, List<Calendars> fields) throws IOException {
      super(writer, Calendars.class, fields);
   }

   public String get(Calendar object, Calendars field) {
      switch (field) {
         case END_DATE:
            return object.getEndDate();
         case FRIDAY:
            return object.getFriday();
         case MONDAY:
            return object.getMonday();
         case SATURDAY:
            return object.getSaturday();
         case SERVICE_ID:
            return object.getServiceId();
         case START_DATE:
            return object.getStartDate();
         case SUNDAY:
            return object.getSunday();
         case THURSDAY:
            return object.getThursday();
         case TUESDAY:
            return object.getTuesday();
         case WEDNESDAY:
            return object.getWednesday();
         default:
            return null;
      }
   }
}
