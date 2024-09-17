package de.topobyte.utilities.apache.commons.cli.commands;

public class RunnerException extends Exception {
   private static final long serialVersionUID = -5592533777317193446L;

   public RunnerException() {
   }

   public RunnerException(String message) {
      super(message);
   }

   public RunnerException(Throwable cause) {
      super(cause);
   }

   public RunnerException(String message, Throwable cause) {
      super(message, cause);
   }
}
