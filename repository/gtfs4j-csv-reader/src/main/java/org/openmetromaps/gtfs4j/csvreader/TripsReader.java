package org.openmetromaps.gtfs4j.csvreader;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import org.openmetromaps.gtfs4j.csv.Trips;
import org.openmetromaps.gtfs4j.model.Trip;

public class TripsReader extends BaseReader<Trip, Trips> {
   public TripsReader(Reader reader) throws IOException {
      super(reader, Trips.class);
   }

   @Override
   public List<Trip> readAll() throws IOException {
      List<Trip> routes = new ArrayList<>();

      while (true) {
         String[] parts = this.csvReader.readNext();
         if (parts == null) {
            this.csvReader.close();
            return routes;
         }

         Trip trip = this.parse(parts);
         routes.add(trip);
      }
   }

   private Trip parse(String[] parts) {
      String routeId = parts[this.idx.get(Trips.ROUTE_ID)];
      String serviceId = parts[this.idx.get(Trips.SERVICE_ID)];
      String id = parts[this.idx.get(Trips.ID)];
      Trip trip = new Trip(routeId, serviceId, id);
      if (this.hasField(Trips.HEADSIGN)) {
         trip.setHeadsign(parts[this.idx.get(Trips.HEADSIGN)]);
      }

      if (this.hasField(Trips.SHORT_NAME)) {
         trip.setShortName(parts[this.idx.get(Trips.SHORT_NAME)]);
      }

      if (this.hasField(Trips.DIRECTION_ID)) {
         trip.setDirectionId(parts[this.idx.get(Trips.DIRECTION_ID)]);
      }

      if (this.hasField(Trips.BLOCK_ID)) {
         trip.setBlockId(parts[this.idx.get(Trips.BLOCK_ID)]);
      }

      if (this.hasField(Trips.SHAPE_ID)) {
         trip.setShapeId(parts[this.idx.get(Trips.SHAPE_ID)]);
      }

      if (this.hasField(Trips.WHEELCHAIR_ACCESSIBLE)) {
         trip.setWheelchairAccessible(parts[this.idx.get(Trips.WHEELCHAIR_ACCESSIBLE)]);
      }

      if (this.hasField(Trips.BIKES_ALLOWED)) {
         trip.setBikesAllowed(parts[this.idx.get(Trips.BIKES_ALLOWED)]);
      }

      return trip;
   }
}
