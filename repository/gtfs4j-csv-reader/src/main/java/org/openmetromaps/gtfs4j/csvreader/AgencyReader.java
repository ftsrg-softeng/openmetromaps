package org.openmetromaps.gtfs4j.csvreader;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import org.openmetromaps.gtfs4j.csv.Agencies;
import org.openmetromaps.gtfs4j.model.Agency;

public class AgencyReader extends BaseReader<Agency, Agencies> {
   public AgencyReader(Reader reader) throws IOException {
      super(reader, Agencies.class);
   }

   @Override
   public List<Agency> readAll() throws IOException {
      List<Agency> routes = new ArrayList<>();

      while (true) {
         String[] parts = this.csvReader.readNext();
         if (parts == null) {
            this.csvReader.close();
            return routes;
         }

         Agency agency = this.parse(parts);
         routes.add(agency);
      }
   }

   private Agency parse(String[] parts) {
      String name = parts[this.idx.get(Agencies.NAME)];
      String url = parts[this.idx.get(Agencies.URL)];
      String timezone = parts[this.idx.get(Agencies.TIMEZONE)];
      Agency agency = new Agency(name, url, timezone);
      if (this.hasField(Agencies.ID)) {
         agency.setId(parts[this.idx.get(Agencies.ID)]);
      }

      if (this.hasField(Agencies.LANG)) {
         agency.setLang(parts[this.idx.get(Agencies.LANG)]);
      }

      if (this.hasField(Agencies.PHONE)) {
         agency.setPhone(parts[this.idx.get(Agencies.PHONE)]);
      }

      if (this.hasField(Agencies.FARE_URL)) {
         agency.setFareUrl(parts[this.idx.get(Agencies.FARE_URL)]);
      }

      if (this.hasField(Agencies.EMAIL)) {
         agency.setEmail(parts[this.idx.get(Agencies.EMAIL)]);
      }

      return agency;
   }
}
