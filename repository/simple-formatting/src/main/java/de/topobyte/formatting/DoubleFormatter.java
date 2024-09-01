package de.topobyte.formatting;

import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DoubleFormatter implements IDoubleFormatter {
   static final Logger logger = LoggerFactory.getLogger(DoubleFormatter.class);
   private int minWidth = 1;
   private int fractionDigits = 6;
   private char padChar = ' ';
   private boolean padBeforeMinus = true;

   @Override
   public Type getType() {
      return Type.DOUBLE;
   }

   @Override
   public String format(double d) {
      StringBuilder buffer = new StringBuilder();
      this.format(buffer, d);
      return buffer.toString();
   }

   @Override
   public void format(StringBuilder buffer, double d) {
      if (Double.isNaN(d)) {
         this.pad(buffer, this.minWidth - 3, ' ');
         buffer.append("NaN");
      } else if (d == Double.POSITIVE_INFINITY) {
         this.pad(buffer, this.minWidth - 8, ' ');
         buffer.append("Infinity");
      } else if (d == Double.NEGATIVE_INFINITY) {
         this.pad(buffer, this.minWidth - 9, ' ');
         buffer.append("-Infinity");
      } else {
         long integral = (long)d;
         boolean negative = d < 0.0;
         if (this.fractionDigits == 0) {
            if (negative) {
               double fraction = -d + (double)integral;
               if (fraction >= 0.5) {
                  integral--;
               }
            } else {
               double fraction = d - (double)integral;
               if (fraction >= 0.5) {
                  integral++;
               }
            }
         }

         int lenIntegral;
         if (!negative) {
            String integralPart = Long.toString(integral);
            lenIntegral = integralPart.length();
            int used = lenIntegral + this.fractionDigits;
            if (this.fractionDigits > 0) {
               used++;
            }

            this.pad(buffer, this.minWidth - used, this.padChar);
            buffer.append(integralPart);
         } else {
            String integralPart = Long.toString(-integral);
            lenIntegral = integralPart.length();
            int used = lenIntegral + this.fractionDigits;
            used++;
            if (this.fractionDigits > 0) {
               used++;
            }

            if (this.padBeforeMinus) {
               this.pad(buffer, this.minWidth - used, this.padChar);
            }

            buffer.append('-');
            if (!this.padBeforeMinus) {
               this.pad(buffer, this.minWidth - used, this.padChar);
            }

            buffer.append(integralPart);
         }

         if (this.fractionDigits > 0) {
            buffer.append(".");
            this.formatFractionDigits(buffer, d, lenIntegral);
         }
      }
   }

   private void pad(StringBuilder buffer, int n, char c) {
      for (int i = 0; i < n; i++) {
         buffer.append(c);
      }
   }

   private void formatFractionDigits(StringBuilder buffer, double d, int usedDigits) {
      int maxDigits = 16 - usedDigits;
      int validDigits = this.fractionDigits;
      int additionalDigits = 0;
      if (maxDigits < validDigits) {
         additionalDigits = validDigits - maxDigits;
         validDigits = maxDigits;
      }

      long nintegral = (long)d;
      List<Integer> ints = new ArrayList<>();
      logger.debug("format: " + d + " with " + this.fractionDigits + " digits");
      logger.debug(d + " " + nintegral);
      logger.debug("used digits: " + usedDigits + " valid digits: " + validDigits);
      double x = (d - (double)nintegral) * 10.0;
      if (x < 0.0) {
         x = -x;
      }

      for (int i = 0; i < validDigits; i++) {
         int integral = (int)x;
         logger.debug(x + " " + integral);
         ints.add(integral);
         x = (x - (double)integral) * 10.0;
      }

      logger.debug("digits: " + ints);
      logger.debug("remainder: " + x);
      if (x >= 5.0) {
         logger.debug("rounding up");

         for (int i = ints.size() - 1; i >= 0; i--) {
            int oldI = ints.get(i);
            int newI = oldI + 1;
            if (newI < 10) {
               ints.set(i, newI);
               break;
            }

            ints.set(i, 0);
         }
      }

      for (int i = 0; i < additionalDigits; i++) {
         ints.add(0);
      }

      for (int i : ints) {
         buffer.append(i);
      }
   }

   public int getMinWidth() {
      return this.minWidth;
   }

   public void setMinWidth(int minWidth) {
      this.minWidth = minWidth;
   }

   public int getFractionDigits() {
      return this.fractionDigits;
   }

   public void setFractionDigits(int fractionDigits) {
      this.fractionDigits = fractionDigits;
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
