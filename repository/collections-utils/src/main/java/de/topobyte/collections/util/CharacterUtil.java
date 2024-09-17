package de.topobyte.collections.util;

import java.util.Iterator;
import java.util.List;

public class CharacterUtil {
   public static String listAsString(List<Character> chars) {
      StringBuilder builder = new StringBuilder(chars.size());

      for (Character c : chars) {
         builder.append(c);
      }

      return builder.toString();
   }

   public static String iterableAsString(Iterable<Character> chars) {
      StringBuilder builder = new StringBuilder();

      for (Character c : chars) {
         builder.append(c);
      }

      return builder.toString();
   }

   public static String iteratorAsString(Iterator<Character> chars) {
      StringBuilder builder = new StringBuilder();

      while (chars.hasNext()) {
         builder.append(chars.next());
      }

      return builder.toString();
   }
}
