package de.topobyte.largescalefileio;

import java.io.File;
import java.io.InputStream;

public class SimpleClosingFileInputStreamFactory implements ClosingFileInputStreamFactory {
   private int idFactory = 0;
   private ClosingFileInputStreamPool pool = new SimpleClosingFileInputStreamPool();

   @Override
   public InputStream create(File file) {
      return new ClosingFileInputStream(this.pool, file, this.idFactory++);
   }
}
