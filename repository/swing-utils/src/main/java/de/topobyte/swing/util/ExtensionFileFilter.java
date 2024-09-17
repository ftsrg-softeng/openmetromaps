package de.topobyte.swing.util;

import java.io.File;
import javax.swing.filechooser.FileFilter;

public class ExtensionFileFilter extends FileFilter {
   private final String extension;
   private final String info;

   public ExtensionFileFilter(String extension, String info) {
      this.extension = extension;
      this.info = info;
   }

   @Override
   public boolean accept(File f) {
      return f.isDirectory() ? true : f.getName().endsWith("." + this.extension);
   }

   @Override
   public String getDescription() {
      return "(." + this.extension + ") " + this.info;
   }

   public String getExtension() {
      return this.extension;
   }

   public String getInfo() {
      return this.info;
   }
}
