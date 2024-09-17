package de.topobyte.utilities.apache.commons.cli.parsing;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.apache.commons.cli.CommandLine;

public class LineHelper {
   public static boolean getValue(CommandLine line, String optionName, boolean defaultValue, boolean trueIfPresent) {
      return !line.hasOption(optionName) ? defaultValue : trueIfPresent;
   }

   public static List<String> getRemainingArguments(CommandLine line) {
      String[] arguments = line.getArgs();
      List<String> args = new ArrayList<>();

      for (String argument : arguments) {
         args.add(argument);
      }

      return args;
   }

   public static Set<String> findOptions(CommandLine line, Collection<String> options) {
      Set<String> results = new HashSet<>();

      for (String option : options) {
         if (line.hasOption(option)) {
            results.add(option);
         }
      }

      return results;
   }
}
