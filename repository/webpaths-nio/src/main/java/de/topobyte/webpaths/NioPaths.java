package de.topobyte.webpaths;

import de.topobyte.collections.util.ListUtil;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class NioPaths {
   public static WebPath convert(Path path, boolean isDir) {
      int ups = 0;
      List<String> comps = new ArrayList<>();

      for (Path part : path) {
         String p = part.toString();
         if (!p.equals(".")) {
            if (p.equals("..")) {
               if (comps.isEmpty()) {
                  ups++;
               } else {
                  ListUtil.removeLast(comps);
               }
            } else {
               comps.add(p);
            }
         }
      }

      return new WebPath(ups, comps, isDir);
   }

   public static Path resolve(Path path, WebPath relative) {
      Path p = path;

      for (int i = 0; i < relative.getUps(); i++) {
         p = p.getParent();
      }

      for (String part : relative) {
         p = p.resolve(part);
      }

      return p;
   }
}
