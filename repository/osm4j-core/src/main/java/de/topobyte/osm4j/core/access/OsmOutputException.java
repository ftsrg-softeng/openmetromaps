package de.topobyte.osm4j.core.access;

public class OsmOutputException extends Exception {
   private static final long serialVersionUID = 16140224990271126L;

   public OsmOutputException(String message) {
      super(message);
   }

   public OsmOutputException(Throwable cause) {
      super(cause);
   }

   public OsmOutputException(String message, Throwable cause) {
      super(message, cause);
   }
}
