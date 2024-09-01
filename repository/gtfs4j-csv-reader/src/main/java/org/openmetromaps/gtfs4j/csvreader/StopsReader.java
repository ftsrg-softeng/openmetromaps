package org.openmetromaps.gtfs4j.csvreader;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import org.openmetromaps.gtfs4j.csv.Stops;
import org.openmetromaps.gtfs4j.model.Stop;

public class StopsReader extends BaseReader<Stop, Stops> {
   public StopsReader(Reader reader) throws IOException {
      super(reader, Stops.class);
   }

   @Override
   public List<Stop> readAll() throws IOException {
      List<Stop> stops = new ArrayList<>();

      while (true) {
         String[] parts = this.csvReader.readNext();
         if (parts == null) {
            this.csvReader.close();
            return stops;
         }

         Stop stop = this.parse(parts);
         stops.add(stop);
      }
   }

   private Stop parse(String[] parts) {
      String id = parts[this.idx.get(Stops.ID)];
      String name = parts[this.idx.get(Stops.NAME)];
      String lat = parts[this.idx.get(Stops.LAT)];
      String lon = parts[this.idx.get(Stops.LON)];
      Stop stop = new Stop(id, name, lat, lon);
      if (this.hasField(Stops.CODE)) {
         stop.setCode(parts[this.idx.get(Stops.CODE)]);
      }

      if (this.hasField(Stops.DESC)) {
         stop.setDesc(parts[this.idx.get(Stops.DESC)]);
      }

      if (this.hasField(Stops.ZONE_ID)) {
         stop.setZoneId(parts[this.idx.get(Stops.ZONE_ID)]);
      }

      if (this.hasField(Stops.URL)) {
         stop.setUrl(parts[this.idx.get(Stops.URL)]);
      }

      if (this.hasField(Stops.LOCATION_TYPE)) {
         stop.setLocationType(parts[this.idx.get(Stops.LOCATION_TYPE)]);
      }

      if (this.hasField(Stops.PARENT_STATION)) {
         stop.setParentStation(parts[this.idx.get(Stops.PARENT_STATION)]);
      }

      if (this.hasField(Stops.TIMEZONE)) {
         stop.setTimezone(parts[this.idx.get(Stops.TIMEZONE)]);
      }

      if (this.hasField(Stops.WHEELCHAIR_BOARDING)) {
         stop.setWheelchairBoarding(parts[this.idx.get(Stops.WHEELCHAIR_BOARDING)]);
      }

      return stop;
   }
}
