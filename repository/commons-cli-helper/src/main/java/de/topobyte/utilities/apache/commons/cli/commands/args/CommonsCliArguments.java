package de.topobyte.utilities.apache.commons.cli.commands.args;

import de.topobyte.utilities.apache.commons.cli.commands.options.CommonsCliExeOptions;
import org.apache.commons.cli.CommandLine;

public class CommonsCliArguments implements ParsedArguments {
   private CommonsCliExeOptions options;
   private CommandLine line;

   public CommonsCliArguments(CommonsCliExeOptions options, CommandLine line) {
      this.options = options;
      this.line = line;
   }

   public CommonsCliExeOptions getOptions() {
      return this.options;
   }

   public CommandLine getLine() {
      return this.line;
   }
}
