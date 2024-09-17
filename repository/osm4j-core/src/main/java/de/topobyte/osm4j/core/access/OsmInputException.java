package de.topobyte.osm4j.core.access;

public class OsmInputException extends Exception {
   private static final long serialVersionUID = 5706518298351094712L;

   public OsmInputException(String message) {
      super(message);
   }

   public OsmInputException(Throwable cause) {
      super(cause);
   }

   public OsmInputException(String message, Throwable cause) {
      super(message, cause);
   }
}
