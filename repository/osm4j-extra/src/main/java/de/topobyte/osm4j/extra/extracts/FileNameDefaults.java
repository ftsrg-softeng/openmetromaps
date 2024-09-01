package de.topobyte.osm4j.extra.extracts;

import de.topobyte.osm4j.utils.FileFormat;

public class FileNameDefaults {
   public static ExtractionFileNames forFormat(FileFormat format) {
      return new ExtractionFileNames(format);
   }
}
