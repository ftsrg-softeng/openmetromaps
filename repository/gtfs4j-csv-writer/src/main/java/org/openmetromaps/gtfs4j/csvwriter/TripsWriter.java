package org.openmetromaps.gtfs4j.csvwriter;

import java.io.IOException;
import java.io.Writer;
import java.util.List;
import org.openmetromaps.gtfs4j.csv.Trips;
import org.openmetromaps.gtfs4j.model.Trip;

public class TripsWriter extends BaseWriter<Trip, Trips> {
   public TripsWriter(Writer writer, List<Trips> fields) throws IOException {
      super(writer, Trips.class, fields);
   }

   public String get(Trip object, Trips field) {
      switch (field) {
         case BIKES_ALLOWED:
            return object.getBikesAllowed();
         case BLOCK_ID:
            return object.getBlockId();
         case DIRECTION_ID:
            return object.getDirectionId();
         case HEADSIGN:
            return object.getHeadsign();
         case ID:
            return object.getId();
         case ROUTE_ID:
            return object.getRouteId();
         case SERVICE_ID:
            return object.getServiceId();
         case SHAPE_ID:
            return object.getShapeId();
         case SHORT_NAME:
            return object.getShortName();
         case WHEELCHAIR_ACCESSIBLE:
            return object.getWheelchairAccessible();
         default:
            return null;
      }
   }
}
