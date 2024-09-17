package org.openmetromaps.gtfs4j.csv;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
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

public class GtfsDirectory {
   private Path path;

   public GtfsDirectory(Path path) {
      this.path = path;
   }

   private BufferedReader reader(Path path) throws IOException {
      return Files.newBufferedReader(path, StandardCharsets.UTF_8);
   }

   private Path path(GtfsFiles gtfsFile) {
      return this.path.resolve(gtfsFile.getFilename());
   }

   public AgencyReader createAgencyReader() throws IOException {
      Path path = this.path(GtfsFiles.AGENCY);
      BufferedReader br = this.reader(path);
      return new AgencyReader(br);
   }

   public StopsReader createStopsReader() throws IOException {
      Path path = this.path(GtfsFiles.STOPS);
      BufferedReader br = this.reader(path);
      return new StopsReader(br);
   }

   public RoutesReader createRoutesReader() throws IOException {
      Path path = this.path(GtfsFiles.ROUTES);
      BufferedReader br = this.reader(path);
      return new RoutesReader(br);
   }

   public TripsReader createTripsReader() throws IOException {
      Path path = this.path(GtfsFiles.TRIPS);
      BufferedReader br = this.reader(path);
      return new TripsReader(br);
   }

   public StopTimesReader createStopTimesReader() throws IOException {
      Path path = this.path(GtfsFiles.STOP_TIMES);
      BufferedReader br = this.reader(path);
      return new StopTimesReader(br);
   }

   public CalendarReader createCalendarReader() throws IOException {
      Path path = this.path(GtfsFiles.CALENDAR);
      BufferedReader br = this.reader(path);
      return new CalendarReader(br);
   }

   public List<Agency> readAgency() throws IOException {
      Path path = this.path(GtfsFiles.AGENCY);
      BufferedReader br = this.reader(path);
      AgencyReader reader = new AgencyReader(br);
      List<Agency> list = reader.readAll();
      reader.close();
      return list;
   }

   public List<Stop> readStops() throws IOException {
      Path path = this.path(GtfsFiles.STOPS);
      BufferedReader br = this.reader(path);
      StopsReader reader = new StopsReader(br);
      List<Stop> list = reader.readAll();
      reader.close();
      return list;
   }

   public List<Route> readRoutes() throws IOException {
      Path path = this.path(GtfsFiles.ROUTES);
      BufferedReader br = this.reader(path);
      RoutesReader reader = new RoutesReader(br);
      List<Route> list = reader.readAll();
      reader.close();
      return list;
   }

   public List<Trip> readTrips() throws IOException {
      Path path = this.path(GtfsFiles.TRIPS);
      BufferedReader br = this.reader(path);
      TripsReader reader = new TripsReader(br);
      List<Trip> list = reader.readAll();
      reader.close();
      return list;
   }

   public List<StopTime> readStopTimes() throws IOException {
      Path path = this.path(GtfsFiles.STOP_TIMES);
      BufferedReader br = this.reader(path);
      StopTimesReader reader = new StopTimesReader(br);
      List<StopTime> list = reader.readAll();
      reader.close();
      return list;
   }

   public List<Calendar> readCalendar() throws IOException {
      Path path = this.path(GtfsFiles.CALENDAR);
      BufferedReader br = this.reader(path);
      CalendarReader reader = new CalendarReader(br);
      List<Calendar> list = reader.readAll();
      reader.close();
      return list;
   }
}
