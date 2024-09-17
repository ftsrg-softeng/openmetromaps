package de.topobyte.osm4j.xml.dynsax;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

class DateParser {
   private static final String[] PATTERNS = new String[]{"yyyy-MM-dd'T'HH:mm:ss'Z'", "yyyy-MM-dd'T'HH:mm:ssZ"};
   private static final DateTimeFormatter[] PARSERS = new DateTimeFormatter[PATTERNS.length];
   private DateTimeFormatter current = PARSERS[0];

   public DateTime parse(String formattedDate) {
      try {
         return this.current.parseDateTime(formattedDate);
      } catch (IllegalArgumentException var6) {
         for (int i = 0; i < PARSERS.length; i++) {
            DateTimeFormatter parser = PARSERS[i];
            if (parser != this.current) {
               try {
                  DateTime result = parser.parseDateTime(formattedDate);
                  this.current = parser;
                  return result;
               } catch (IllegalArgumentException var5) {
               }
            }
         }

         throw new RuntimeException("Unable to parse date '" + formattedDate + "'");
      }
   }

   static {
      for (int i = 0; i < PATTERNS.length; i++) {
         PARSERS[i] = DateTimeFormat.forPattern(PATTERNS[i]);
      }
   }
}
