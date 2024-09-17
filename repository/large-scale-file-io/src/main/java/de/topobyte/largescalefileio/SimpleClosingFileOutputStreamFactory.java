package de.topobyte.largescalefileio;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

public class SimpleClosingFileOutputStreamFactory implements ClosingFileOutputStreamFactory {
   private int idFactory = 0;
   private ClosingFileOutputStreamPool pool = new SimpleClosingFileOutputStreamPool();

   @Override
   public OutputStream create(File file) throws IOException {
      return new ClosingFileOutputStream(this.pool, file, this.idFactory++);
   }
}
