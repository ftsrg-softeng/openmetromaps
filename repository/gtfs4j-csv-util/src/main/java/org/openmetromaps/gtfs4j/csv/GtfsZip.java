package org.openmetromaps.gtfs4j.csv;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;
import org.openmetromaps.gtfs4j.csvreader.AgencyReader;
import org.openmetromaps.gtfs4j.csvreader.CalendarReader;
import org.openmetromaps.gtfs4j.csvreader.RoutesReader;
import org.openmetromaps.gtfs4j.csvreader.StopTimesReader;
import org.openmetromaps.gtfs4j.csvreader.StopsReader;
import org.openmetromaps.gtfs4j.csvreader.TripsReader;
import org.openmetromaps.gtfs4j.model.Agency;
import org.openmetromaps.gtfs4j.model.Calendar;
import org.openmetromaps.gtfs4j.model.Route;
import org.openmetromaps.gtfs4j.model.Stop;
import org.openmetromaps.gtfs4j.model.StopTime;
import org.openmetromaps.gtfs4j.model.Trip;

public class GtfsZip implements Closeable {
   private ZipFile zip;

   public GtfsZip(ZipFile zip) {
      this.zip = zip;
   }

   public GtfsZip(Path path) throws ZipException, IOException {
      this.zip = new ZipFile(path.toFile());
   }

   @Override
   public void close() throws IOException {
      this.zip.close();
   }

   private InputStreamReader reader(GtfsFiles file) throws IOException {
      ZipEntry entry = this.zip.getEntry(file.getFilename());
      InputStream is = this.zip.getInputStream(entry);
      return new InputStreamReader(is, StandardCharsets.UTF_8);
   }

   public AgencyReader createAgencyReader() throws IOException {
      InputStreamReader isr = this.reader(GtfsFiles.AGENCY);
      return new AgencyReader(isr);
   }

   public StopsReader createStopsReader() throws IOException {
      InputStreamReader isr = this.reader(GtfsFiles.STOPS);
      return new StopsReader(isr);
   }

   public RoutesReader createRoutesReader() throws IOException {
      InputStreamReader isr = this.reader(GtfsFiles.ROUTES);
      return new RoutesReader(isr);
   }

   public TripsReader createTripsReader() throws IOException {
      InputStreamReader isr = this.reader(GtfsFiles.TRIPS);
      return new TripsReader(isr);
   }

   public StopTimesReader createStopTimesReader() throws IOException {
      InputStreamReader isr = this.reader(GtfsFiles.STOP_TIMES);
      return new StopTimesReader(isr);
   }

   public CalendarReader createCalendarReader() throws IOException {
      InputStreamReader isr = this.reader(GtfsFiles.CALENDAR);
      return new CalendarReader(isr);
   }

   public List<Agency> readAgency() throws IOException {
      InputStreamReader isr = this.reader(GtfsFiles.AGENCY);
      AgencyReader reader = new AgencyReader(isr);
      List<Agency> data = reader.readAll();
      reader.close();
      return data;
   }

   public List<Stop> readStops() throws IOException {
      InputStreamReader isr = this.reader(GtfsFiles.STOPS);
      StopsReader reader = new StopsReader(isr);
      List<Stop> data = reader.readAll();
      reader.close();
      return data;
   }

   public List<Route> readRoutes() throws IOException {
      InputStreamReader isr = this.reader(GtfsFiles.ROUTES);
      RoutesReader reader = new RoutesReader(isr);
      List<Route> data = reader.readAll();
      reader.close();
      return data;
   }

   public List<Trip> readTrips() throws IOException {
      InputStreamReader isr = this.reader(GtfsFiles.TRIPS);
      TripsReader reader = new TripsReader(isr);
      List<Trip> data = reader.readAll();
      reader.close();
      return data;
   }

   public List<StopTime> readStopTimes() throws IOException {
      InputStreamReader isr = this.reader(GtfsFiles.STOP_TIMES);
      StopTimesReader reader = new StopTimesReader(isr);
      List<StopTime> data = reader.readAll();
      reader.close();
      return data;
   }

   public List<Calendar> readCalendar() throws IOException {
      InputStreamReader isr = this.reader(GtfsFiles.CALENDAR);
      CalendarReader reader = new CalendarReader(isr);
      List<Calendar> data = reader.readAll();
      reader.close();
      return data;
   }
}
