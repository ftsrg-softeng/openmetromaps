package de.topobyte.formatting;

public class IntFormatter implements IIntFormatter {
   private int minWidth = 1;
   private char padChar = ' ';
   private boolean padBeforeMinus = true;

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
      if (this.padBeforeMinus) {
         this.formatPadBeforeMinus(buffer, n);
      } else {
         this.formatPadAfterMinus(buffer, n);
      }
   }

   private void formatPadBeforeMinus(StringBuilder buffer, int n) {
      String s = Integer.toString(n);
      int len = s.length();
      if (len < this.minWidth) {
         int rem = this.minWidth - len;

         for (int i = 0; i < rem; i++) {
            buffer.append(this.padChar);
         }
      }

      buffer.append(s);
   }

   private void formatPadAfterMinus(StringBuilder buffer, int n) {
      boolean negative = n < 0;
      String s = Integer.toString(n);
      if (negative) {
         buffer.append('-');
      }

      int len = s.length();
      if (len < this.minWidth) {
         int rem = this.minWidth - len;

         for (int i = 0; i < rem; i++) {
            buffer.append(this.padChar);
         }
      }

      if (negative) {
         buffer.append(s, 1, len);
      } else {
         buffer.append(s);
      }
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

   public boolean isPadBeforeMinus() {
      return this.padBeforeMinus;
   }

   public void setPadBeforeMinus(boolean padBeforeMinus) {
      this.padBeforeMinus = padBeforeMinus;
   }
}
