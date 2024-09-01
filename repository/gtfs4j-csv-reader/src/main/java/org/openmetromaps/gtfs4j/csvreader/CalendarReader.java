package org.openmetromaps.gtfs4j.csvreader;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import org.openmetromaps.gtfs4j.csv.Calendars;
import org.openmetromaps.gtfs4j.model.Calendar;

public class CalendarReader extends BaseReader<Calendar, Calendars> {
   public CalendarReader(Reader reader) throws IOException {
      super(reader, Calendars.class);
   }

   @Override
   public List<Calendar> readAll() throws IOException {
      List<Calendar> routes = new ArrayList<>();

      while (true) {
         String[] parts = this.csvReader.readNext();
         if (parts == null) {
            this.csvReader.close();
            return routes;
         }

         Calendar calendar = this.parse(parts);
         routes.add(calendar);
      }
   }

   private Calendar parse(String[] parts) {
      String serviceId = parts[this.idx.get(Calendars.SERVICE_ID)];
      String monday = parts[this.idx.get(Calendars.MONDAY)];
      String tuesday = parts[this.idx.get(Calendars.TUESDAY)];
      String wednesday = parts[this.idx.get(Calendars.WEDNESDAY)];
      String thursday = parts[this.idx.get(Calendars.THURSDAY)];
      String friday = parts[this.idx.get(Calendars.FRIDAY)];
      String saturday = parts[this.idx.get(Calendars.SATURDAY)];
      String sunday = parts[this.idx.get(Calendars.SUNDAY)];
      String startDate = parts[this.idx.get(Calendars.START_DATE)];
      String endDate = parts[this.idx.get(Calendars.END_DATE)];
      return new Calendar(serviceId, monday, tuesday, wednesday, thursday, friday, saturday, sunday, startDate, endDate);
   }
}
