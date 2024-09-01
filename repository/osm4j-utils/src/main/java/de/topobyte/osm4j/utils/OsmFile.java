package de.topobyte.osm4j.utils;

import java.nio.file.Path;

public class OsmFile {
   private Path path;
   private FileFormat fileFormat;

   public OsmFile(Path path, FileFormat fileFormat) {
      this.path = path;
      this.fileFormat = fileFormat;
   }

   public Path getPath() {
      return this.path;
   }

   public FileFormat getFileFormat() {
      return this.fileFormat;
   }
}
