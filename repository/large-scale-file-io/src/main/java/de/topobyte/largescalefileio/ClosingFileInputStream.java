package de.topobyte.largescalefileio;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

class ClosingFileInputStream extends InputStream {
   private ClosingFileInputStreamPool pool;
   private File file;
   private int id;
   private long pos = 0L;

   public ClosingFileInputStream(ClosingFileInputStreamPool pool, File file, int id) {
      this.pool = pool;
      this.file = file;
      this.id = id;
   }

   public int getId() {
      return this.id;
   }

   @Override
   public void close() throws IOException {
      this.pool.close(this.id);
   }

   @Override
   public int read() throws IOException {
      InputStream fis = this.pool.create(this.file, this.id, this.pos);
      int r = fis.read();
      if (r >= 0) {
         this.pos++;
      }

      return r;
   }

   @Override
   public int read(byte[] b) throws IOException {
      InputStream fis = this.pool.create(this.file, this.id, this.pos);
      int r = fis.read(b);
      if (r >= 0) {
         this.pos += (long)r;
      }

      return r;
   }

   @Override
   public int read(byte[] b, int off, int len) throws IOException {
      InputStream fis = this.pool.create(this.file, this.id, this.pos);
      int r = fis.read(b, off, len);
      if (r >= 0) {
         this.pos += (long)r;
      }

      return r;
   }

   @Override
   public long skip(long n) throws IOException {
      InputStream fis = this.pool.create(this.file, this.id, this.pos);
      long r = fis.skip(n);
      if (r >= 0L) {
         this.pos += r;
      }

      return r;
   }

   @Override
   public int available() throws IOException {
      InputStream fis = this.pool.create(this.file, this.id, this.pos);
      return fis.available();
   }
}
