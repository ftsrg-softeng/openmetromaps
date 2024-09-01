package de.topobyte.utilities.apache.commons.cli.commands;

import de.topobyte.utilities.apache.commons.cli.commands.args.BasicArguments;
import de.topobyte.utilities.apache.commons.cli.commands.args.CommonsCliArguments;
import de.topobyte.utilities.apache.commons.cli.commands.args.ParsedArguments;
import de.topobyte.utilities.apache.commons.cli.commands.delegate.Delegate;
import de.topobyte.utilities.apache.commons.cli.commands.delegate.DelegateClass;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ExeRunner {
   public static void run(ExecutionData data) throws RunnerException {
      Delegate delegate = data.getDelegate();
      ParsedArguments arguments = data.getArgs();
      if (delegate instanceof DelegateClass) {
         DelegateClass delegateClass = (DelegateClass)delegate;
         if (arguments instanceof BasicArguments) {
            BasicArguments basicArgs = (BasicArguments)arguments;
            run(data.getName(), delegateClass, basicArgs);
         } else if (arguments instanceof CommonsCliArguments) {
            CommonsCliArguments commonsArgs = (CommonsCliArguments)arguments;
            run(data.getName(), delegateClass, commonsArgs);
         }
      }
   }

   private static void run(String name, DelegateClass c, BasicArguments arguments) throws RunnerException {
      String[] args = arguments.getArgs();

      try {
         Class<?> clazz = c.getClazz();
         if (!c.isPassName()) {
            Method method = clazz.getMethod("main", String[].class);
            method.invoke(null, args);
         } else {
            Method method = clazz.getMethod("main", String.class, String[].class);
            method.invoke(null, name, args);
         }
      } catch (SecurityException | IllegalAccessException | IllegalArgumentException | NoSuchMethodException var6) {
         throw new RunnerException("Error while starting main method for task", var6);
      } catch (InvocationTargetException var7) {
         throw new RunnerException(var7.getCause());
      }
   }

   private static void run(String name, DelegateClass c, CommonsCliArguments arguments) throws RunnerException {
      try {
         Class<?> clazz = c.getClazz();
         if (!c.isPassName()) {
            Method method = clazz.getMethod("main", CommonsCliArguments.class);
            method.invoke(null, arguments);
         } else {
            Method method = clazz.getMethod("main", String.class, CommonsCliArguments.class);
            method.invoke(null, name, arguments);
         }
      } catch (SecurityException | IllegalAccessException | IllegalArgumentException | NoSuchMethodException var5) {
         throw new RunnerException("Error while starting main method for task", var5);
      } catch (InvocationTargetException var6) {
         throw new RunnerException(var6.getCause());
      }
   }
}
