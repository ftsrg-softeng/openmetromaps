package de.topobyte.formatting;

public class LiteralFormatter implements IVoidFormatter {
   private String literal;

   public LiteralFormatter(String literal) {
      this.literal = literal;
   }

   @Override
   public Type getType() {
      return Type.NONE;
   }

   @Override
   public String format() {
      return this.literal;
   }

   @Override
   public void format(StringBuilder buffer) {
      buffer.append(this.literal);
   }
}
