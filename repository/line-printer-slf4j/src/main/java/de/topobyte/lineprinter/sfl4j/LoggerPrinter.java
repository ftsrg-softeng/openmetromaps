package de.topobyte.lineprinter.sfl4j;

import de.topobyte.lineprinter.LinePrinter;
import org.slf4j.Logger;

public class LoggerPrinter implements LinePrinter {
   private Logger logger;
   private LogLevel level;

   public LoggerPrinter(Logger logger, LogLevel level) {
      this.logger = logger;
      this.level = level;
   }

   public void println(String line) {
      switch (this.level) {
         case TRACE:
         default:
            this.logger.trace(line);
            break;
         case DEBUG:
            this.logger.debug(line);
            break;
         case INFO:
            this.logger.info(line);
            break;
         case WARN:
            this.logger.warn(line);
            break;
         case ERROR:
            this.logger.error(line);
      }
   }
}
