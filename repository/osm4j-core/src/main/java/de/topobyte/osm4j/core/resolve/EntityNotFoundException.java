package de.topobyte.osm4j.core.resolve;

public class EntityNotFoundException extends Exception {
   private static final long serialVersionUID = 3215406014208968124L;

   public EntityNotFoundException(String message) {
      super(message);
   }

   public EntityNotFoundException(Throwable cause) {
      super(cause);
   }

   public EntityNotFoundException(String message, Throwable cause) {
      super(message, cause);
   }
}
