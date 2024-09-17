package de.topobyte.utilities.apache.commons.cli.parsing;

public class IntegerOption extends BaseOption {
   private int value;

   public IntegerOption(boolean hasValue) {
      super(hasValue);
   }

   public IntegerOption(boolean hasValue, int value) {
      super(hasValue);
      this.value = value;
   }

   public int getValue() {
      return this.value;
   }
}
