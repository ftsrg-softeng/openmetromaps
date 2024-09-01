package de.topobyte.utilities.apache.commons.cli.commands;

import de.topobyte.utilities.apache.commons.cli.commands.args.ParsedArguments;
import de.topobyte.utilities.apache.commons.cli.commands.delegate.Delegate;

public class ExecutionData {
   private String name;
   private ParsedArguments args;
   private Delegate delegate;

   public ExecutionData(String name, ParsedArguments args, Delegate delegate) {
      this.name = name;
      this.args = args;
      this.delegate = delegate;
   }

   public String getName() {
      return this.name;
   }

   public ParsedArguments getArgs() {
      return this.args;
   }

   public Delegate getDelegate() {
      return this.delegate;
   }
}
