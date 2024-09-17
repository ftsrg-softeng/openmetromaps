package de.topobyte.xml.dynsax;

public class ParsingException extends Exception {
   private static final long serialVersionUID = -5675650496770757710L;

   public ParsingException() {
   }

   public ParsingException(String message, Throwable cause) {
      super(message, cause);
   }

   public ParsingException(String message) {
      super(message);
   }

   public ParsingException(Throwable cause) {
      super(cause);
   }
}
