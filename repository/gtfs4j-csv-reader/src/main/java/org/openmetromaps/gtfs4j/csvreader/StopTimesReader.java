package org.openmetromaps.gtfs4j.csvreader;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import org.openmetromaps.gtfs4j.csv.StopTimes;
import org.openmetromaps.gtfs4j.model.StopTime;

public class StopTimesReader extends BaseReader<StopTime, StopTimes> {
   public StopTimesReader(Reader reader) throws IOException {
      super(reader, StopTimes.class);
   }

   @Override
   public List<StopTime> readAll() throws IOException {
      List<StopTime> results = new ArrayList<>();

      while (true) {
         String[] parts = this.csvReader.readNext();
         if (parts == null) {
            this.csvReader.close();
            return results;
         }

         StopTime stopTime = this.parse(parts);
         results.add(stopTime);
      }
   }

   private StopTime parse(String[] parts) {
      String tripId = parts[this.idx.get(StopTimes.TRIP_ID)];
      String arrivalTime = parts[this.idx.get(StopTimes.ARRVIAL_TIME)];
      String departureTime = parts[this.idx.get(StopTimes.DEPARTURE_TIME)];
      String stopId = parts[this.idx.get(StopTimes.STOP_ID)];
      String stopSequence = parts[this.idx.get(StopTimes.STOP_SEQUENCE)];
      StopTime stopTime = new StopTime(tripId, arrivalTime, departureTime, stopId, stopSequence);
      if (this.hasField(StopTimes.STOP_HEADSIGN)) {
         stopTime.setStopHeadsign(parts[this.idx.get(StopTimes.STOP_HEADSIGN)]);
      }

      if (this.hasField(StopTimes.PICKUP_TYPE)) {
         stopTime.setPickupType(parts[this.idx.get(StopTimes.PICKUP_TYPE)]);
      }

      if (this.hasField(StopTimes.DROP_OFF_TYPE)) {
         stopTime.setDropOffType(parts[this.idx.get(StopTimes.DROP_OFF_TYPE)]);
      }

      if (this.hasField(StopTimes.SHAPE_DIST_TRAVELED)) {
         stopTime.setShapeDistTraveled(parts[this.idx.get(StopTimes.SHAPE_DIST_TRAVELED)]);
      }

      if (this.hasField(StopTimes.TIMEPOINT)) {
         stopTime.setTimepoint(parts[this.idx.get(StopTimes.TIMEPOINT)]);
      }

      return stopTime;
   }
}
