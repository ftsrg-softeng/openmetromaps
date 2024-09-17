package de.topobyte.formatting;

public class BooleanFormatter implements IBooleanFormatter {
   @Override
   public Type getType() {
      return Type.BOOLEAN;
   }

   @Override
   public String format(boolean b) {
      StringBuilder buffer = new StringBuilder();
      this.format(buffer, b);
      return buffer.toString();
   }

   @Override
   public void format(StringBuilder buffer, boolean b) {
      buffer.append(b);
   }
}
