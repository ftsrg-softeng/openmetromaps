package de.topobyte.utilities.apache.commons.cli.parsing;

public class StringOption extends BaseOption {
   private String value;

   public StringOption(boolean hasValue) {
      super(hasValue);
   }

   public StringOption(boolean hasValue, String value) {
      super(hasValue);
      this.value = value;
   }

   public String getValue() {
      return this.value;
   }
}
