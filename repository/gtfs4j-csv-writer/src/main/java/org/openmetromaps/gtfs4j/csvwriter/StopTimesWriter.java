package org.openmetromaps.gtfs4j.csvwriter;

import java.io.IOException;
import java.io.Writer;
import java.util.List;
import org.openmetromaps.gtfs4j.csv.StopTimes;
import org.openmetromaps.gtfs4j.model.StopTime;

public class StopTimesWriter extends BaseWriter<StopTime, StopTimes> {
   public StopTimesWriter(Writer writer, List<StopTimes> fields) throws IOException {
      super(writer, StopTimes.class, fields);
   }

   public String get(StopTime object, StopTimes field) {
      switch (field) {
         case ARRVIAL_TIME:
            return object.getArrivalTime();
         case DEPARTURE_TIME:
            return object.getDepartureTime();
         case DROP_OFF_TYPE:
            return object.getDropOffType();
         case PICKUP_TYPE:
            return object.getPickupType();
         case SHAPE_DIST_TRAVELED:
            return object.getShapeDistTraveled();
         case STOP_HEADSIGN:
            return object.getStopHeadsign();
         case STOP_ID:
            return object.getStopId();
         case STOP_SEQUENCE:
            return object.getStopSequence();
         case TIMEPOINT:
            return object.getTimepoint();
         case TRIP_ID:
            return object.getTripId();
         default:
            return null;
      }
   }
}
