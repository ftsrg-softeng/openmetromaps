package de.topobyte.largescalefileio;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

class SimpleClosingFileInputStreamPool implements ClosingFileInputStreamPool {
   private InputStream cache = null;
   private int cacheId = -1;

   @Override
   public InputStream create(File file, int id, long pos) throws IOException {
      if (this.cache == null) {
         this.cache = new FileInputStream(file);
         this.seek(pos);
         this.cacheId = id;
         return this.cache;
      } else if (this.cacheId == id) {
         return this.cache;
      } else {
         this.cache.close();
         this.cache = new FileInputStream(file);
         this.seek(pos);
         this.cacheId = id;
         return this.cache;
      }
   }

   private void seek(long pos) throws IOException {
      long remaining = pos;

      while (remaining > 0L) {
         long s = this.cache.skip(remaining);
         if (s < 0L) {
            throw new IOException();
         }

         remaining -= s;
      }
   }

   @Override
   public void close(int id) throws IOException {
      if (id == this.cacheId) {
         this.cache.close();
         this.cache = null;
         this.cacheId = -1;
      }
   }
}
