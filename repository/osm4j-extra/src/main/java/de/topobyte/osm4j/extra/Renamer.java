package de.topobyte.osm4j.extra;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

public class Renamer {
   private Path path;
   private String from;
   private String to;
   private boolean dry;

   public Renamer(Path path, String from, String to, boolean dry) {
      this.path = path;
      this.from = from;
      this.to = to;
      this.dry = dry;
   }

   public void execute() throws IOException {
      List<Path> paths = this.find(this.from);
      int i = 0;

      for (Path source : paths) {
         System.out.println(String.format("%d / %d: %s", ++i, paths.size(), source));
         Path target = source.resolveSibling(this.to);
         System.out.println(source + " -> " + target);
         if (Files.exists(target)) {
            System.out.println("target exists");
         }

         if (!this.dry) {
            Files.move(source, target);
         }
      }
   }

   private List<Path> find(final String name) throws IOException {
      final List<Path> results = new ArrayList<>();
      FileVisitor<Path> visitor = new SimpleFileVisitor<Path>() {
         public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
            String fname = file.getFileName().toString();
            if (name.equals(fname)) {
               results.add(file);
            }

            return FileVisitResult.CONTINUE;
         }
      };
      Files.walkFileTree(this.path, visitor);
      return results;
   }
}
