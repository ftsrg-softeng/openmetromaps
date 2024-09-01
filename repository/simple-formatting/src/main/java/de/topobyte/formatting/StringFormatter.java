package de.topobyte.formatting;

public class StringFormatter implements IStringFormatter {
   @Override
   public Type getType() {
      return Type.STRING;
   }

   @Override
   public String format(String s) {
      StringBuilder buffer = new StringBuilder();
      this.format(buffer, s);
      return buffer.toString();
   }

   @Override
   public void format(StringBuilder buffer, String s) {
      buffer.append(s);
   }
}
