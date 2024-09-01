package de.topobyte.utilities.apache.commons.cli.parsing;

public class BaseOption {
   private boolean hasValue;

   public BaseOption(boolean hasValue) {
      this.hasValue = hasValue;
   }

   public boolean hasValue() {
      return this.hasValue;
   }
}
