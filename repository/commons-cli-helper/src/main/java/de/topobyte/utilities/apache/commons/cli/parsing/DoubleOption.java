package de.topobyte.utilities.apache.commons.cli.parsing;

public class DoubleOption extends BaseOption {
   private double value;

   public DoubleOption(boolean hasValue) {
      super(hasValue);
   }

   public DoubleOption(boolean hasValue, double value) {
      super(hasValue);
      this.value = value;
   }

   public double getValue() {
      return this.value;
   }
}
