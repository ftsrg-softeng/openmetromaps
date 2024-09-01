package de.topobyte.osm4j.utils;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public abstract class AbstractExecutable {
   protected Options options = new Options();
   protected CommandLine line = null;

   protected abstract String getHelpMessage();

   protected void setup(String[] args) {
      try {
         this.line = new DefaultParser().parse(this.options, args);
      } catch (ParseException var3) {
         System.out.println("unable to parse command line: " + var3.getMessage());
         new HelpFormatter().printHelp(this.getHelpMessage(), this.options);
         System.exit(1);
      }
   }
}
