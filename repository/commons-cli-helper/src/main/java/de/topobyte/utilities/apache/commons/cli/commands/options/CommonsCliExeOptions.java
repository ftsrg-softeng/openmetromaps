package de.topobyte.utilities.apache.commons.cli.commands.options;

import de.topobyte.utilities.apache.commons.cli.CliTool;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;

public class CommonsCliExeOptions implements ExeOptions {
   private Options options;
   private String usageSuffix;

   public CommonsCliExeOptions(Options options, String usageSuffix) {
      this.options = options;
      this.usageSuffix = usageSuffix;
   }

   public Options getOptions() {
      return this.options;
   }

   public String getUsageSuffix() {
      return this.usageSuffix;
   }

   @Override
   public void usage(String name) {
      String syntax;
      if (this.usageSuffix == null) {
         syntax = name;
      } else {
         syntax = name + " " + this.usageSuffix;
      }

      new HelpFormatter().printHelp(syntax, this.options);
   }

   @Override
   public CliTool tool(String name) {
      return new CliTool(name, this);
   }
}
