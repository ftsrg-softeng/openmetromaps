package de.topobyte.utilities.apache.commons.cli;

import org.apache.commons.cli.Option;

public class OptionUtil {
   public static String getKey(Option option) {
      String opt = option.getOpt();
      return opt != null ? opt : option.getLongOpt();
   }
}
