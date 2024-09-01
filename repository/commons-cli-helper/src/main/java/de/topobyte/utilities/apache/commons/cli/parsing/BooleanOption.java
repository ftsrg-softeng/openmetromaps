package de.topobyte.utilities.apache.commons.cli.parsing;

public class BooleanOption extends BaseOption {
   private boolean value;

   public BooleanOption(boolean hasValue) {
      super(hasValue);
   }

   public BooleanOption(boolean hasValue, boolean value) {
      super(hasValue);
      this.value = value;
   }

   public boolean getValue() {
      return this.value;
   }
}
