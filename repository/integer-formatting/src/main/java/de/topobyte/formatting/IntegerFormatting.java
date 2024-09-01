package de.topobyte.formatting;

public class IntegerFormatting {
   static final char[] digits = new char[]{
      '0',
      '1',
      '2',
      '3',
      '4',
      '5',
      '6',
      '7',
      '8',
      '9',
      'a',
      'b',
      'c',
      'd',
      'e',
      'f',
      'g',
      'h',
      'i',
      'j',
      'k',
      'l',
      'm',
      'n',
      'o',
      'p',
      'q',
      'r',
      's',
      't',
      'u',
      'v',
      'w',
      'x',
      'y',
      'z'
   };
   static final char[] DIGITS = new char[]{
      '0',
      '1',
      '2',
      '3',
      '4',
      '5',
      '6',
      '7',
      '8',
      '9',
      'A',
      'B',
      'C',
      'D',
      'E',
      'F',
      'G',
      'H',
      'I',
      'J',
      'K',
      'L',
      'M',
      'N',
      'O',
      'P',
      'Q',
      'R',
      'S',
      'T',
      'U',
      'V',
      'W',
      'X',
      'Y',
      'Z'
   };

   private static char[] table(Case c) {
      return c == Case.Uppercase ? DIGITS : digits;
   }

   public static String intToHexString(int i, Case c) {
      return intToUnsignedString(i, 4, table(c));
   }

   public static String intToOctalString(int i) {
      return intToUnsignedString(i, 3, digits);
   }

   public static String intToBinaryString(int i) {
      return intToUnsignedString(i, 1, digits);
   }

   public static String longToHexString(long i, Case c) {
      return longToUnsignedString(i, 4, table(c));
   }

   public static String longToOctalString(long i) {
      return longToUnsignedString(i, 3, digits);
   }

   public static String longToBinaryString(long i) {
      return longToUnsignedString(i, 1, digits);
   }

   private static String intToUnsignedString(int i, int shift, char[] table) {
      char[] buf = new char[32];
      int charPos = 32;
      int radix = 1 << shift;
      int mask = radix - 1;

      do {
         charPos--;
         buf[charPos] = table[i & mask];
         i >>>= shift;
      } while (i != 0);

      char[] copy = new char[32 - charPos];
      System.arraycopy(buf, charPos, copy, 0, copy.length);
      return new String(copy);
   }

   private static String longToUnsignedString(long val, int shift, char[] table) {
      int mag = 64 - numberOfLeadingZeros(val);
      int chars = Math.max((mag + (shift - 1)) / shift, 1);
      char[] buf = new char[chars];
      formatUnsignedLong(val, shift, buf, 0, chars, table);
      return new String(buf);
   }

   private static int formatUnsignedLong(long val, int shift, char[] buf, int offset, int len, char[] table) {
      int charPos = len;
      int radix = 1 << shift;
      int mask = radix - 1;

      do {
         charPos--;
         buf[offset + charPos] = table[(int)val & mask];
         val >>>= shift;
      } while (val != 0L && charPos > 0);

      return charPos;
   }

   private static int numberOfLeadingZeros(long i) {
      if (i == 0L) {
         return 64;
      } else {
         int n = 1;
         int x = (int)(i >>> 32);
         if (x == 0) {
            n += 32;
            x = (int)i;
         }

         if (x >>> 16 == 0) {
            n += 16;
            x <<= 16;
         }

         if (x >>> 24 == 0) {
            n += 8;
            x <<= 8;
         }

         if (x >>> 28 == 0) {
            n += 4;
            x <<= 4;
         }

         if (x >>> 30 == 0) {
            n += 2;
            x <<= 2;
         }

         return n - (x >>> 31);
      }
   }
}
