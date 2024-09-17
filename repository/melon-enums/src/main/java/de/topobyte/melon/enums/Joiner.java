package de.topobyte.melon.enums;

import java.util.Iterator;

public class Joiner {
   private String separator;

   public static Joiner on(String string) {
      return new Joiner(string);
   }

   public Joiner(String separator) {
      this.separator = separator;
   }

   public String join(Iterable<String> values) {
      StringBuilder buffer = new StringBuilder();
      Iterator<String> iterator = values.iterator();

      while (iterator.hasNext()) {
         buffer.append(iterator.next());
         if (iterator.hasNext()) {
            buffer.append(this.separator);
         }
      }

      return buffer.toString();
   }
}
