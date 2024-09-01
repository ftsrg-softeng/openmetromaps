package org.openmetromaps.gtfs4j.csvreader;

import au.com.bytecode.opencsv.CSVReader;
import java.io.Reader;

public class Util {
   public static int getIndex(String[] array, String name) {
      for (int i = 0; i < array.length; i++) {
         if (array[i].equals(name)) {
            return i;
         }
      }

      return -1;
   }

   public static CSVReader defaultCsvReader(Reader reader) {
      return new CSVReader(reader, ',', '"');
   }
}
