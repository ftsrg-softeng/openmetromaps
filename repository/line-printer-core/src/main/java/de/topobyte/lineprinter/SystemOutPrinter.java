package de.topobyte.lineprinter;

public class SystemOutPrinter extends PrintStreamPrinter {
   public SystemOutPrinter() {
      super(System.out);
   }
}
