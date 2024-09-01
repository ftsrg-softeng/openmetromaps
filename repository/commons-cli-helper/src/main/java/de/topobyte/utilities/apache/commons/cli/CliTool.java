package de.topobyte.utilities.apache.commons.cli;

import de.topobyte.utilities.apache.commons.cli.commands.options.ExeOptions;

public class CliTool {
   private String name;
   private ExeOptions options;

   public CliTool(String name, ExeOptions options) {
      this.name = name;
      this.options = options;
   }

   public void printMessageAndExit(String message) {
      this.printMessageAndExit(1, message);
   }

   public void printMessageAndExit(int exitCode, String message) {
      System.out.println(message);
      System.exit(exitCode);
   }

   public void printMessagesAndExit(String... messages) {
      this.printMessagesAndExit(1, messages);
   }

   public void printMessagesAndExit(int exitCode, String... messages) {
      for (String message : messages) {
         System.out.println(message);
      }

      System.exit(exitCode);
   }

   public void printHelpAndExit() {
      this.printHelpAndExit(1);
   }

   public void printHelpAndExit(int exitCode) {
      this.options.usage(this.name);
      System.exit(exitCode);
   }

   public void printMessageAndHelpAndExit(String message) {
      this.printMessageAndHelpAndExit(1, message);
   }

   public void printMessageAndHelpAndExit(int exitCode, String message) {
      System.out.println(message);
      this.printHelpAndExit(exitCode);
   }

   public void printMessagesAndHelpAndExit(String... messages) {
      this.printMessagesAndHelpAndExit(1, messages);
   }

   public void printMessagesAndHelpAndExit(int exitCode, String... messages) {
      for (String message : messages) {
         System.out.println(message);
      }

      this.printHelpAndExit(exitCode);
   }
}
