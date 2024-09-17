package de.topobyte.utilities.apache.commons.cli.commands.options;

public class OptionFactories {
   public static ExeOptionsFactory NO_OPTIONS = new ExeOptionsFactory() {
      @Override
      public ExeOptions createOptions() {
         return new BasicExeOptions();
      }
   };

   private OptionFactories() {
   }
}
