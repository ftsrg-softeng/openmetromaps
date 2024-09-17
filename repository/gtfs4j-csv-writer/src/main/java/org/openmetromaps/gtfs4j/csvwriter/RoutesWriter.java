package org.openmetromaps.gtfs4j.csvwriter;

import java.io.IOException;
import java.io.Writer;
import java.util.List;
import org.openmetromaps.gtfs4j.csv.Routes;
import org.openmetromaps.gtfs4j.model.Route;

public class RoutesWriter extends BaseWriter<Route, Routes> {
   public RoutesWriter(Writer writer, List<Routes> fields) throws IOException {
      super(writer, Routes.class, fields);
   }

   public String get(Route object, Routes field) {
      switch (field) {
         case AGENCY_ID:
            return object.getAgencyId();
         case COLOR:
            return object.getColor();
         case DESC:
            return object.getDesc();
         case ID:
            return object.getId();
         case LONG_NAME:
            return object.getLongName();
         case SHORT_NAME:
            return object.getShortName();
         case TEXT_COLOR:
            return object.getTextColor();
         case TYPE:
            return object.getType();
         case URL:
            return object.getUrl();
         default:
            return null;
      }
   }
}
