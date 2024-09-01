package org.openmetromaps.gtfs4j.csvreader;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import org.openmetromaps.gtfs4j.csv.Routes;
import org.openmetromaps.gtfs4j.model.Route;

public class RoutesReader extends BaseReader<Route, Routes> {
   public RoutesReader(Reader reader) throws IOException {
      super(reader, Routes.class);
   }

   @Override
   public List<Route> readAll() throws IOException {
      List<Route> routes = new ArrayList<>();

      while (true) {
         String[] parts = this.csvReader.readNext();
         if (parts == null) {
            this.csvReader.close();
            return routes;
         }

         Route route = this.parse(parts);
         routes.add(route);
      }
   }

   private Route parse(String[] parts) {
      String id = parts[this.idx.get(Routes.ID)];
      String shortName = parts[this.idx.get(Routes.SHORT_NAME)];
      String longName = parts[this.idx.get(Routes.LONG_NAME)];
      String type = parts[this.idx.get(Routes.TYPE)];
      Route route = new Route(id, shortName, longName, type);
      if (this.hasField(Routes.AGENCY_ID)) {
         route.setAgencyId(parts[this.idx.get(Routes.AGENCY_ID)]);
      }

      if (this.hasField(Routes.DESC)) {
         route.setDesc(parts[this.idx.get(Routes.DESC)]);
      }

      if (this.hasField(Routes.URL)) {
         route.setUrl(parts[this.idx.get(Routes.URL)]);
      }

      if (this.hasField(Routes.COLOR)) {
         route.setColor(parts[this.idx.get(Routes.COLOR)]);
      }

      if (this.hasField(Routes.TEXT_COLOR)) {
         route.setTextColor(parts[this.idx.get(Routes.TEXT_COLOR)]);
      }

      return route;
   }
}
