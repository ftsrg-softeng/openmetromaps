package de.topobyte.collections.util;

import java.util.HashSet;
import java.util.Set;

public class SetUtil {
   public static <T> Set<T> intersection(Set<T> set1, Set<T> set2) {
      Set<T> result = new HashSet<>();

      for (T element : set1) {
         if (set2.contains(element)) {
            result.add(element);
         }
      }

      return result;
   }
}
