package de.topobyte.utilities.apache.commons.cli.parsing;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.cli.CommandLine;

public class ArgumentHelper {
   public static boolean parseBooleanWithDefaultValue(String value, boolean defaultValue) {
      try {
         return parseBoolean(value);
      } catch (ArgumentParseException var3) {
         return defaultValue;
      }
   }

   public static boolean parseBoolean(String value) throws ArgumentParseException {
      String lower = value.toLowerCase();
      if (lower.equals("yes")) {
         return true;
      } else if (lower.equals("true")) {
         return true;
      } else if (lower.equals("no")) {
         return false;
      } else if (lower.equals("false")) {
         return false;
      } else {
         throw new ArgumentParseException("unable to parse boolean: '" + value + "'");
      }
   }

   public static int parseInteger(String value) throws ArgumentParseException {
      try {
         return Integer.parseInt(value);
      } catch (NumberFormatException var2) {
         throw new ArgumentParseException("unable to parse integer: '" + value + "'");
      }
   }

   public static long parseLong(String value) throws ArgumentParseException {
      try {
         return Long.parseLong(value);
      } catch (NumberFormatException var2) {
         throw new ArgumentParseException("unable to parse long: '" + value + "'");
      }
   }

   public static double parseDouble(String value) throws ArgumentParseException {
      try {
         return Double.parseDouble(value);
      } catch (NumberFormatException var2) {
         throw new ArgumentParseException("unable to parse double: '" + value + "'");
      }
   }

   public static BooleanOption getBoolean(CommandLine line, String option) throws ArgumentParseException {
      String value = line.getOptionValue(option);
      if (value == null) {
         return new BooleanOption(false);
      } else {
         boolean bool = parseBoolean(value);
         return new BooleanOption(true, bool);
      }
   }

   public static List<BooleanOption> getBooleans(CommandLine line, String option) throws ArgumentParseException {
      String[] values = line.getOptionValues(option);
      if (values == null) {
         return new ArrayList<>();
      } else {
         List<BooleanOption> options = new ArrayList<>();

         for (String value : values) {
            boolean bool = parseBoolean(value);
            options.add(new BooleanOption(true, bool));
         }

         return options;
      }
   }

   public static IntegerOption getInteger(CommandLine line, String option) throws ArgumentParseException {
      String value = line.getOptionValue(option);
      if (value == null) {
         return new IntegerOption(false);
      } else {
         int num = parseInteger(value);
         return new IntegerOption(true, num);
      }
   }

   public static List<IntegerOption> getIntegers(CommandLine line, String option) throws ArgumentParseException {
      String[] values = line.getOptionValues(option);
      if (values == null) {
         return new ArrayList<>();
      } else {
         List<IntegerOption> options = new ArrayList<>();

         for (String value : values) {
            int num = parseInteger(value);
            options.add(new IntegerOption(true, num));
         }

         return options;
      }
   }

   public static LongOption getLong(CommandLine line, String option) throws ArgumentParseException {
      String value = line.getOptionValue(option);
      if (value == null) {
         return new LongOption(false);
      } else {
         long num = parseLong(value);
         return new LongOption(true, num);
      }
   }

   public static List<LongOption> getLongs(CommandLine line, String option) throws ArgumentParseException {
      String[] values = line.getOptionValues(option);
      if (values == null) {
         return new ArrayList<>();
      } else {
         List<LongOption> options = new ArrayList<>();

         for (String value : values) {
            long num = parseLong(value);
            options.add(new LongOption(true, num));
         }

         return options;
      }
   }

   public static DoubleOption getDouble(CommandLine line, String option) throws ArgumentParseException {
      String value = line.getOptionValue(option);
      if (value == null) {
         return new DoubleOption(false);
      } else {
         Double num = parseDouble(value);
         return new DoubleOption(true, num);
      }
   }

   public static List<DoubleOption> getDoubles(CommandLine line, String option) throws ArgumentParseException {
      String[] values = line.getOptionValues(option);
      if (values == null) {
         return new ArrayList<>();
      } else {
         List<DoubleOption> options = new ArrayList<>();

         for (String value : values) {
            Double num = parseDouble(value);
            options.add(new DoubleOption(true, num));
         }

         return options;
      }
   }

   public static StringOption getString(CommandLine line, String option) {
      String value = line.getOptionValue(option);
      return value == null ? new StringOption(false) : new StringOption(true, value);
   }

   public static List<StringOption> getStrings(CommandLine line, String option) {
      String[] values = line.getOptionValues(option);
      if (values == null) {
         return new ArrayList<>();
      } else {
         List<StringOption> options = new ArrayList<>();

         for (String value : values) {
            options.add(new StringOption(true, value));
         }

         return options;
      }
   }
}
