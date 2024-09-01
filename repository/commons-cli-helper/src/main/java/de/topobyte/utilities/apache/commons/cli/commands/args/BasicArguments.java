package de.topobyte.utilities.apache.commons.cli.commands.args;

public class BasicArguments implements ParsedArguments {
   private String[] args;

   public BasicArguments(String[] args) {
      this.args = args;
   }

   public String[] getArgs() {
      return this.args;
   }
}
