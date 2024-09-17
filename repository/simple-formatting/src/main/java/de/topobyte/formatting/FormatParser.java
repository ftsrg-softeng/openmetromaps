package de.topobyte.formatting;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FormatParser {
   static final Logger logger = LoggerFactory.getLogger(FormatParser.class);
   private String format;
   private List<IFormatter> formatters = new ArrayList<>();
   private int pos = 0;
   private int numChars = 0;
   private char[] flags = new char[]{'0', '#', '+', '(', ',', ' '};
   private char[] conversions = new char[]{'s', 'd', 'f', 'b', 'x', 'X', '%', 'n'};
   private char[] digits = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
   private Set<Character> flagLookup = new HashSet<>();
   private Set<Character> optionLookup;
   private Set<Character> conversionLookup;

   public static List<IFormatter> parse(String format) {
      FormatParser parser = new FormatParser(format);
      parser.parse();
      return parser.formatters;
   }

   private FormatParser(String format) {
      for (char c : this.flags) {
         this.flagLookup.add(c);
      }

      this.optionLookup = new HashSet<>();

      for (char c : this.flags) {
         this.optionLookup.add(c);
      }

      for (char c : this.digits) {
         this.optionLookup.add(c);
      }

      this.optionLookup.add('.');
      this.conversionLookup = new HashSet<>();

      for (char c : this.conversions) {
         this.conversionLookup.add(c);
      }

      this.format = format;
   }

   private void parse() {
      this.numChars = this.format.length();
      StringBuilder accu = new StringBuilder();

      while (this.pos < this.numChars) {
         char c = this.format.charAt(this.pos);
         if (c == '%') {
            this.appendAccu(accu);
            this.parseFormatter();
         } else {
            accu.append(c);
            this.pos++;
         }
      }

      this.appendAccu(accu);
   }

   private void appendAccu(StringBuilder accu) {
      if (accu.length() != 0) {
         String current = accu.toString();
         logger.debug("literal: \"" + current + "\"");
         this.formatters.add(new LiteralFormatter(current));
         accu.setLength(0);
      }
   }

   private boolean isOption(char c) {
      return this.optionLookup.contains(c);
   }

   private boolean isFlag(char c) {
      return this.flagLookup.contains(c);
   }

   private boolean isConversion(char c) {
      return this.conversionLookup.contains(c);
   }

   private void parseFormatter() {
      logger.debug("parse formatter at: " + this.pos);
      this.pos++;
      StringBuilder options = new StringBuilder();

      while (this.pos < this.numChars) {
         char c = this.format.charAt(this.pos++);
         logger.debug("char: " + c);
         if (this.isConversion(c)) {
            this.conversion(c, options);
            break;
         }

         if (this.isOption(c)) {
            options.append(c);
         }
      }
   }

   private void conversion(char c, StringBuilder options) {
      if (c == 's') {
         this.formatters.add(new StringFormatter());
      } else if (c == '%') {
         this.formatters.add(new LiteralFormatter("%"));
      } else if (c == 'n') {
         this.formatters.add(new LiteralFormatter("\n"));
      } else if (c == 'b') {
         this.formatters.add(new BooleanFormatter());
      } else if (c == 'd') {
         this.formatters.add(new LongFormatter());
      } else if (c == 'f') {
         FormatParser.PrecisionResult precision = this.precision(options);
         DoubleFormatter formatter = new DoubleFormatter();
         if (precision.isPresent) {
            formatter.setFractionDigits(precision.precision);
         }

         this.formatters.add(formatter);
      } else if (c == 'x') {
         LongHexFormatter formatter = new LongHexFormatter();
         if (this.hasFlag(options, '#')) {
            formatter.setPrintRadixIndicator(true);
         }

         formatter.setCase(Case.Lowercase);
         this.formatters.add(formatter);
      } else {
         if (c != 'X') {
            throw new IllegalArgumentException("Unable to parse format");
         }

         LongHexFormatter formatter = new LongHexFormatter();
         if (this.hasFlag(options, '#')) {
            formatter.setPrintRadixIndicator(true);
         }

         formatter.setCase(Case.Uppercase);
         this.formatters.add(formatter);
      }
   }

   private boolean hasFlag(StringBuilder options, char c) {
      for (int i = 0; i < options.length(); i++) {
         if (options.charAt(i) == c) {
            return true;
         }
      }

      return false;
   }

   private FormatParser.PrecisionResult precision(StringBuilder options) {
      FormatParser.PrecisionResult result = new FormatParser.PrecisionResult();
      int dot = -1;

      for (int i = 0; i < options.length(); i++) {
         if (options.charAt(i) == '.') {
            dot = i;
            break;
         }
      }

      if (dot < 0) {
         return result;
      } else {
         int precision = 0;

         for (int ix = dot + 1; ix < options.length(); ix++) {
            char c = options.charAt(ix);
            if (!Character.isDigit(c)) {
               throw new IllegalArgumentException("invalid precision character: '" + c + "'");
            }

            precision *= 10;
            precision += c - '0';
         }

         result.isPresent = true;
         result.precision = precision;
         return result;
      }
   }

   private class PrecisionResult {
      boolean isPresent = false;
      int precision = 0;

      private PrecisionResult() {
      }
   }
}
