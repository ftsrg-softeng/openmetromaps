package de.topobyte.osm4j.extra.nodearray;

public class Coding {
   private static long INT_MIN = -2147483648L;
   private static long INT_MAX = 2147483647L;
   private static int SHORT_MIN = -32768;
   private static int SHORT_MAX = 32767;
   private static long INT_RANGE = INT_MAX - INT_MIN - 3L;
   private static int SHORT_RANGE = SHORT_MAX - SHORT_MIN - 3;
   private static double INT_RANGE_D = (double)INT_RANGE;
   private static double SHORT_RANGE_D = (double)SHORT_RANGE;
   public static int INT_NULL = Integer.MAX_VALUE;
   public static short SHORT_NULL = 32767;

   public static int encodeLonAsInt(double lon) {
      double a = (lon + 180.0) / 360.0;
      return (int)(INT_MIN + Math.round(a * (double)INT_RANGE));
   }

   public static int encodeLatAsInt(double lat) {
      double a = (lat + 90.0) / 180.0;
      return (int)(INT_MIN + Math.round(a * (double)INT_RANGE));
   }

   public static short encodeLonAsShort(double lon) {
      double a = (lon + 180.0) / 360.0;
      return (short)((int)((long)SHORT_MIN + Math.round(a * (double)SHORT_RANGE)));
   }

   public static short encodeLatAsShort(double lat) {
      double a = (lat + 90.0) / 180.0;
      return (short)((int)((long)SHORT_MIN + Math.round(a * (double)SHORT_RANGE)));
   }

   public static double decodeLonFromInt(int value) {
      double a = (double)((long)value - INT_MIN) / INT_RANGE_D;
      return a * 360.0 - 180.0;
   }

   public static double decodeLatFromInt(int value) {
      double a = (double)((long)value - INT_MIN) / INT_RANGE_D;
      return a * 180.0 - 90.0;
   }

   public static double decodeLonFromShort(short value) {
      double a = (double)(value - SHORT_MIN) / SHORT_RANGE_D;
      return a * 360.0 - 180.0;
   }

   public static double decodeLatFromShort(short value) {
      double a = (double)(value - SHORT_MIN) / SHORT_RANGE_D;
      return a * 180.0 - 90.0;
   }
}
