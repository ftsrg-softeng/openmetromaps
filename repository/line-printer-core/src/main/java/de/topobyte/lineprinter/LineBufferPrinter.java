package de.topobyte.lineprinter;

import java.util.ArrayList;
import java.util.List;

public class LineBufferPrinter implements LinePrinter {
   private List<String> lines = new ArrayList<>();

   @Override
   public void println(String line) {
      this.lines.add(line);
   }

   public List<String> getLines() {
      return this.lines;
   }
}
