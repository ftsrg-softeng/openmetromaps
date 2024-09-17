package de.topobyte.largescalefileio;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

class ClosingFileOutputStream extends OutputStream {
   private ClosingFileOutputStreamPool pool;
   private File file;
   private int id;
   private boolean append = false;

   public ClosingFileOutputStream(ClosingFileOutputStreamPool pool, File file, int id) throws IOException {
      this.pool = pool;
      this.file = file;
      this.id = id;
      FileOutputStream out = new FileOutputStream(file);
      out.close();
   }

   public int getId() {
      return this.id;
   }

   @Override
   public void write(int b) throws IOException {
      OutputStream fos = this.pool.create(this.file, this.id, this.append);
      this.append = true;
      fos.write(b);
   }

   @Override
   public void write(byte[] b) throws IOException {
      OutputStream fos = this.pool.create(this.file, this.id, this.append);
      this.append = true;
      fos.write(b);
   }

   @Override
   public void write(byte[] b, int off, int len) throws IOException {
      OutputStream fos = this.pool.create(this.file, this.id, this.append);
      this.append = true;
      fos.write(b, off, len);
   }

   @Override
   public void flush() throws IOException {
      this.pool.flush(this.id);
   }

   @Override
   public void close() throws IOException {
      this.pool.close(this.id);
   }
}
