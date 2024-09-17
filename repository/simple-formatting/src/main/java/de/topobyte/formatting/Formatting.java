package de.topobyte.formatting;

import java.util.List;

public class Formatting {
   public static String format(String format, Object... args) {
      StringBuilder buffer = new StringBuilder();
      formatBuilder(buffer, format, args);
      return buffer.toString();
   }

   public static void formatBuilder(StringBuilder buffer, String format, Object... args) {
      List<IFormatter> formatters = FormatParser.parse(format);
      Formatter formatter = new Formatter(formatters);
      formatter.formatBuilder(buffer, args);
   }
}
