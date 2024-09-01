package de.topobyte.utilities.apache.commons.cli.commands.options;

import de.topobyte.utilities.apache.commons.cli.CliTool;
import de.topobyte.utilities.apache.commons.cli.commands.delegate.Delegate;
import de.topobyte.utilities.apache.commons.cli.commands.delegate.DelegateClass;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class DelegateExeOptions implements ExeOptions {
   private Set<String> commandNames = new HashSet<>();
   private List<String> commands = new ArrayList<>();
   private Map<String, ExeOptionsFactory> optionFactories = new HashMap<>();
   private Map<String, Delegate> delegates = new HashMap<>();

   @Override
   public void usage(String name) {
      System.out.println("usage: " + name + " <command>");
      System.out.println("where <command> may be one of the following:");

      for (String command : this.commands) {
         System.out.println("   " + command);
      }
   }

   @Override
   public CliTool tool(String name) {
      return new CliTool(name, this);
   }

   public void addCommand(String command, ExeOptionsFactory delegateOptions) {
      this.commandNames.add(command);
      this.commands.add(command);
      this.optionFactories.put(command, delegateOptions);
   }

   public void addCommand(String command, Class<?> clazz) {
      this.commandNames.add(command);
      this.commands.add(command);
      this.optionFactories.put(command, OptionFactories.NO_OPTIONS);
      this.delegates.put(command, new DelegateClass(clazz, true));
   }

   public void addCommand(String command, ExeOptionsFactory delegateOptions, Class<?> clazz) {
      this.commandNames.add(command);
      this.commands.add(command);
      this.optionFactories.put(command, delegateOptions);
      this.delegates.put(command, new DelegateClass(clazz, true));
   }

   public boolean hasSubCommand(String command) {
      return this.commandNames.contains(command);
   }

   public ExeOptionsFactory getSubOptions(String task) {
      return this.optionFactories.get(task);
   }

   public Delegate getDelegate(String task) {
      return this.delegates.get(task);
   }
}
