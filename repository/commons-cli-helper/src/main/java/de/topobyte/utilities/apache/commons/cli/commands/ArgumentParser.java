package de.topobyte.utilities.apache.commons.cli.commands;

import de.topobyte.utilities.apache.commons.cli.commands.args.BasicArguments;
import de.topobyte.utilities.apache.commons.cli.commands.args.CommonsCliArguments;
import de.topobyte.utilities.apache.commons.cli.commands.delegate.Delegate;
import de.topobyte.utilities.apache.commons.cli.commands.options.BasicExeOptions;
import de.topobyte.utilities.apache.commons.cli.commands.options.CommonsCliExeOptions;
import de.topobyte.utilities.apache.commons.cli.commands.options.DelegateExeOptions;
import de.topobyte.utilities.apache.commons.cli.commands.options.ExeOptions;
import de.topobyte.utilities.apache.commons.cli.commands.options.ExeOptionsFactory;
import de.topobyte.utilities.apache.commons.cli.parsing.ArgumentParseException;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class ArgumentParser {
   private String name;
   private ExeOptions options;
   private ErrorHandlingStrategy errorHandlingStrategy = ErrorHandlingStrategy.EXIT;

   public ArgumentParser(String name, ExeOptions options) {
      this.name = name;
      this.options = options;
   }

   public void setErrorHandlingStrategy(ErrorHandlingStrategy errorHandlingStrategy) {
      this.errorHandlingStrategy = errorHandlingStrategy;
   }

   public ExecutionData parse(String[] args) {
      try {
         return this.parse(this.name, args, null);
      } catch (ArgumentParseException var3) {
         this.error(var3, this.options, this.name);
         return null;
      }
   }

   public ExecutionData parse(String name, String[] args, Delegate delegate) throws ArgumentParseException {
      if (this.options instanceof DelegateExeOptions) {
         DelegateExeOptions delegateOptions = (DelegateExeOptions)this.options;
         return this.parse(name, delegateOptions, args, delegate);
      } else if (this.options instanceof CommonsCliExeOptions) {
         CommonsCliExeOptions commonsOptions = (CommonsCliExeOptions)this.options;
         return this.parse(name, commonsOptions, args, delegate);
      } else if (this.options instanceof BasicExeOptions) {
         BasicExeOptions basicOptions = (BasicExeOptions)this.options;
         return this.parse(name, basicOptions, args, delegate);
      } else {
         return null;
      }
   }

   private ExecutionData parse(String name, BasicExeOptions basicOptions, String[] args, Delegate delegate) {
      BasicArguments arguments = new BasicArguments(args);
      return new ExecutionData(name, arguments, delegate);
   }

   private ExecutionData parse(String name, CommonsCliExeOptions commonsOptions, String[] args, Delegate delegate) {
      Options options = commonsOptions.getOptions();

      try {
         CommandLine line = new DefaultParser().parse(options, args);
         CommonsCliArguments arguments = new CommonsCliArguments(commonsOptions, line);
         return new ExecutionData(name, arguments, delegate);
      } catch (ParseException var8) {
         this.error(var8, commonsOptions, name);
         return null;
      }
   }

   private ExecutionData parse(String name, DelegateExeOptions delegateOptions, String[] args, Delegate delegate) throws ArgumentParseException {
      if (args.length == 0) {
         throw new ArgumentParseException("Missing command name");
      } else {
         String task = args[0];
         if (!delegateOptions.hasSubCommand(task)) {
            throw new ArgumentParseException(String.format("Invalid command '%s'", task));
         } else {
            String[] taskArgs = new String[args.length - 1];

            for (int i = 0; i < taskArgs.length; i++) {
               taskArgs[i] = args[i + 1];
            }

            ExeOptionsFactory subOptionsFactory = delegateOptions.getSubOptions(task);
            ExeOptions subOptions = subOptionsFactory.createOptions();
            Delegate nextDelegate = delegateOptions.getDelegate(task);
            String subName = name + " " + task;
            ArgumentParser subParser = new ArgumentParser(subName, subOptions);
            subParser.setErrorHandlingStrategy(this.errorHandlingStrategy);

            try {
               return subParser.parse(subName, taskArgs, nextDelegate);
            } catch (ArgumentParseException var13) {
               this.error(var13, subOptions, subName);
               return null;
            }
         }
      }
   }

   private void error(Exception e, ExeOptions exeOptions, String name) {
      System.out.println(e.getMessage());
      exeOptions.usage(name);
      if (this.errorHandlingStrategy == ErrorHandlingStrategy.EXIT) {
         System.exit(1);
      }
   }
}
