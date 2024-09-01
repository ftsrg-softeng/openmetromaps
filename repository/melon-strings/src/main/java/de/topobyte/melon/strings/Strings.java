package de.topobyte.melon.strings;

public class Strings {
   public static String repeat(String string, int num) {
      StringBuilder buffer = new StringBuilder();
      repeat(buffer, string, num);
      return buffer.toString();
   }

   public static void repeat(StringBuilder buffer, String string, int num) {
      for (int i = 0; i < num; i++) {
         buffer.append(string);
      }
   }
}
