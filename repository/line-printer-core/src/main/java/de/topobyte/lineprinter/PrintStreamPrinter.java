package de.topobyte.lineprinter;

import java.io.PrintStream;

public class PrintStreamPrinter implements LinePrinter {
   private PrintStream stream;

   public PrintStreamPrinter(PrintStream stream) {
      this.stream = stream;
   }

   @Override
   public void println(String line) {
      this.stream.println(line);
   }
}
