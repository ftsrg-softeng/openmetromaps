package de.topobyte.osm4j.extra.datatree;

import java.io.File;
import java.nio.file.Path;

public class DataTreeFiles {
   private File dirFile;
   private Path dirPath;
   private String filename;

   public DataTreeFiles(File dir, String filename) {
      this.dirPath = dir.toPath();
      this.dirFile = dir;
      this.filename = filename;
   }

   public DataTreeFiles(Path dir, String filename) {
      this.dirPath = dir;
      this.dirFile = dir.toFile();
      this.filename = filename;
   }

   public Path getSubdirPath(Node leaf) {
      return this.dirPath.resolve(Long.toHexString(leaf.getPath()));
   }

   public File getSubdirFile(Node leaf) {
      return new File(this.dirFile, Long.toHexString(leaf.getPath()));
   }

   public File getFile(Node leaf) {
      File subdir = new File(this.dirFile, Long.toHexString(leaf.getPath()));
      return new File(subdir, this.filename);
   }

   public Path getPath(Node leaf) {
      Path subdir = this.dirPath.resolve(Long.toHexString(leaf.getPath()));
      return subdir.resolve(this.filename);
   }

   public File getFile(long leafPath) {
      File subdir = new File(this.dirFile, Long.toHexString(leafPath));
      return new File(subdir, this.filename);
   }

   public Path getPath(long leafPath) {
      Path subdir = this.dirPath.resolve(Long.toHexString(leafPath));
      return subdir.resolve(this.filename);
   }
}
