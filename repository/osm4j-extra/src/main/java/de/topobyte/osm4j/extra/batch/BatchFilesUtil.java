package de.topobyte.osm4j.extra.batch;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class BatchFilesUtil {
   public static List<Path> getPaths(Path dir, String fileNames) throws IOException {
      List<Path> paths = new ArrayList<>();
      DirectoryStream<Path> directories = Files.newDirectoryStream(dir);

      for (Path path : directories) {
         if (Files.isDirectory(path)) {
            Path file = path.resolve(fileNames);
            if (Files.exists(file)) {
               paths.add(file);
            }
         }
      }

      directories.close();
      return paths;
   }
}
