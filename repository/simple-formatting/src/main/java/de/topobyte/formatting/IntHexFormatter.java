package de.topobyte.formatting;

public class IntHexFormatter implements IIntFormatter {
   private Case c = Case.Lowercase;
   private boolean printRadixIndicator = false;
   private int minWidth = 1;
   private char padChar = ' ';
   private boolean padBeforeRadixIndicator = true;

   @Override
   public Type getType() {
      return Type.INT;
   }

   @Override
   public String format(int n) {
      StringBuilder buffer = new StringBuilder();
      this.format(buffer, n);
      return buffer.toString();
   }

   @Override
   public void format(StringBuilder buffer, int n) {
      String hex = IntegerFormatting.intToHexString(n, this.c);
      int len = hex.length();
      if (this.printRadixIndicator) {
         len += 2;
      }

      if (this.padBeforeRadixIndicator && len < this.minWidth) {
         this.pad(buffer, len);
      }

      if (this.printRadixIndicator) {
         buffer.append(this.c == Case.Uppercase ? "0X" : "0x");
      }

      if (!this.padBeforeRadixIndicator && len < this.minWidth) {
         this.pad(buffer, len);
      }

      buffer.append(hex);
   }

   private void pad(StringBuilder buffer, int len) {
      int rem = this.minWidth - len;

      for (int i = 0; i < rem; i++) {
         buffer.append(this.padChar);
      }
   }

   public Case getCase() {
      return this.c;
   }

   public void setCase(Case c) {
      this.c = c;
   }

   public boolean isPrintRadixIndicator() {
      return this.printRadixIndicator;
   }

   public void setPrintRadixIndicator(boolean printRadixIndicator) {
      this.printRadixIndicator = printRadixIndicator;
   }

   public int getMinWidth() {
      return this.minWidth;
   }

   public void setMinWidth(int minWidth) {
      this.minWidth = minWidth;
   }

   public char getPadChar() {
      return this.padChar;
   }

   public void setPadChar(char padChar) {
      this.padChar = padChar;
   }

   public boolean isPadBeforeRadixIndicator() {
      return this.padBeforeRadixIndicator;
   }

   public void setPadBeforeRadixIndicator(boolean padBeforeRadixIndicator) {
      this.padBeforeRadixIndicator = padBeforeRadixIndicator;
   }
}
