package de.topobyte.utilities.apache.commons.cli.parsing;

public class LongOption extends BaseOption {
   private long value;

   public LongOption(boolean hasValue) {
      super(hasValue);
   }

   public LongOption(boolean hasValue, long value) {
      super(hasValue);
      this.value = value;
   }

   public long getValue() {
      return this.value;
   }
}
