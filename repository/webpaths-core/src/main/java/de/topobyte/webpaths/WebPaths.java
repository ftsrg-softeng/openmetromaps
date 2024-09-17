package de.topobyte.webpaths;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import de.topobyte.collections.util.ListUtil;
import java.util.ArrayList;
import java.util.List;

public class WebPaths {
   static Splitter SPLITTER = Splitter.on('/').omitEmptyStrings();
   static Joiner JOINER = Joiner.on('/');

   public static WebPath get(String first, String... more) {
      WebPath result = getSingle(first);

      for (int i = 0; i < more.length; i++) {
         WebPath next = getSingle(more[i]);
         result = result.resolve(next);
      }

      return result;
   }

   private static WebPath getSingle(String spec) {
      int ups = 0;
      List<String> components = new ArrayList<>();
      List<String> parts = SPLITTER.splitToList(spec);
      if (parts.isEmpty()) {
         return new WebPath(0, new ArrayList<String>(), false);
      } else {
         boolean lastWasDir = false;

         for (String part : parts) {
            if (part.equals(".")) {
               lastWasDir = true;
            } else if (part.equals("..")) {
               lastWasDir = true;
               if (!components.isEmpty()) {
                  ListUtil.removeLast(components);
               } else {
                  ups++;
               }
            } else {
               lastWasDir = false;
               components.add(part);
            }
         }

         boolean isDir = spec.endsWith("/");
         isDir |= lastWasDir;
         return new WebPath(ups, components, isDir);
      }
   }
}
