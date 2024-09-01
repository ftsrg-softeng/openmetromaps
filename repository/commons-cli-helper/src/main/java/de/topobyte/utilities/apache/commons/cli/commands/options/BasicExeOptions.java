package de.topobyte.utilities.apache.commons.cli.commands.options;

import de.topobyte.utilities.apache.commons.cli.CliTool;

public class BasicExeOptions implements ExeOptions {
   private String usage;

   public BasicExeOptions() {
      this(null);
   }

   public BasicExeOptions(String usage) {
      this.usage = usage;
   }

   public String getUsage() {
      return this.usage;
   }

   @Override
   public void usage(String name) {
      String syntax;
      if (this.usage == null) {
         syntax = name;
      } else {
         syntax = name + " " + this.usage;
      }

      System.out.println(syntax);
   }

   @Override
   public CliTool tool(String name) {
      return new CliTool(name, this);
   }
}
