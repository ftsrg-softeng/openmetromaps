package de.topobyte.lineprinter;

public class SystemErrPrinter extends PrintStreamPrinter {
   public SystemErrPrinter() {
      super(System.err);
   }
}
