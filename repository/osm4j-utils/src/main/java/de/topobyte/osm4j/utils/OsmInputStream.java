package de.topobyte.osm4j.utils;

import java.io.InputStream;

public class OsmInputStream {
   private InputStream input;
   private FileFormat fileFormat;

   public OsmInputStream(InputStream input, FileFormat fileFormat) {
      this.input = input;
      this.fileFormat = fileFormat;
   }

   public InputStream getInputStream() {
      return this.input;
   }

   public FileFormat getFileFormat() {
      return this.fileFormat;
   }
}
