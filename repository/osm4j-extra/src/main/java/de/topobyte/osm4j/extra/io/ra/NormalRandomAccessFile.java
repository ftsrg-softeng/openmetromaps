package de.topobyte.osm4j.extra.io.ra;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.RandomAccessFile;

public class NormalRandomAccessFile extends RandomAccessFile implements RandomAccess {
   public NormalRandomAccessFile(File file) throws FileNotFoundException {
      super(file, "r");
   }

   public NormalRandomAccessFile(String name) throws FileNotFoundException {
      super(name, "r");
   }
}
