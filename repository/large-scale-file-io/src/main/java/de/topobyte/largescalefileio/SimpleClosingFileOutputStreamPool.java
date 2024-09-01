package de.topobyte.largescalefileio;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

class SimpleClosingFileOutputStreamPool implements ClosingFileOutputStreamPool {
   private OutputStream cache = null;
   private int cacheId = -1;

   @Override
   public OutputStream create(File file, int id, boolean append) throws IOException {
      if (this.cache == null) {
         this.cache = new FileOutputStream(file, append);
         this.cacheId = id;
         return this.cache;
      } else if (this.cacheId == id) {
         return this.cache;
      } else {
         this.cache.close();
         this.cache = new FileOutputStream(file, append);
         this.cacheId = id;
         return this.cache;
      }
   }

   @Override
   public void flush(int id) throws IOException {
      if (id == this.cacheId) {
         this.cache.flush();
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
