package de.topobyte.osm4j.extra.extracts;

import java.util.HashMap;
import java.util.Map;
import org.joda.time.Duration;
import org.joda.time.Period;
import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;

public class TimeTable {
   private Map<String, Long> starts = new HashMap<>();
   private Map<String, Long> stops = new HashMap<>();
   private PeriodFormatter formatter = new PeriodFormatterBuilder()
      .appendHours()
      .appendSuffix("h")
      .appendSeparator(" ")
      .appendMinutes()
      .appendSuffix("m")
      .appendSeparator(" ")
      .printZeroAlways()
      .appendSeconds()
      .appendSuffix("s")
      .toFormatter();

   public void start(String key) {
      this.starts.put(key, System.currentTimeMillis());
   }

   public void stop(String key) {
      this.stops.put(key, System.currentTimeMillis());
   }

   public long time(String key) {
      if (!this.starts.containsKey(key)) {
         return 0L;
      } else {
         long stop;
         if (this.stops.containsKey(key)) {
            stop = this.stops.get(key);
         } else {
            stop = System.currentTimeMillis();
         }

         return stop - this.starts.get(key);
      }
   }

   public String htime(String key) {
      long millis = this.time(key);
      Duration duration = new Duration(millis);
      Period period = duration.toPeriod();
      return this.formatter.print(period);
   }
}
