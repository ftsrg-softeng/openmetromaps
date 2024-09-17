package de.topobyte.utilities.apache.commons.cli;

import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

public class OptionHelper {
   public static Option addS(Options options, String opt, boolean hasArg, boolean required, String description) {
      Option option = new Option(opt, hasArg, description);
      option.setRequired(required);
      options.addOption(option);
      return option;
   }

   public static Option addS(Options options, String opt, boolean hasArg, boolean required, String argName, String description) {
      Option option = new Option(opt, hasArg, description);
      option.setRequired(required);
      option.setArgName(argName);
      options.addOption(option);
      return option;
   }

   public static Option addL(Options options, String longName, boolean hasArg, boolean required, String description) {
      Option option = new Option(null, longName, hasArg, description);
      option.setRequired(required);
      options.addOption(option);
      return option;
   }

   public static Option addL(Options options, String longName, boolean hasArg, boolean required, String argName, String description) {
      Option option = new Option(null, longName, hasArg, description);
      option.setRequired(required);
      option.setArgName(argName);
      options.addOption(option);
      return option;
   }

   public static Option add(Options options, String opt, String longName, boolean hasArg, boolean required, String description) {
      Option option = new Option(opt, longName, hasArg, description);
      option.setRequired(required);
      options.addOption(option);
      return option;
   }

   public static Option add(Options options, String opt, String longName, boolean hasArg, boolean required, String argName, String description) {
      Option option = new Option(opt, longName, hasArg, description);
      option.setRequired(required);
      option.setArgName(argName);
      options.addOption(option);
      return option;
   }
}
