package de.topobyte.compactio;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;

public class InputStreamCompactReader extends CompactReader {
   private final InputStream is;

   public InputStreamCompactReader(InputStream is) {
      this.is = is;
   }

   @Override
   public int readByte() throws IOException {
      int r = this.is.read();
      if (r < 0) {
         throw new EOFException();
      } else {
         return r;
      }
   }

   @Override
   public int read(byte[] buffer, int off, int len) throws IOException {
      int r = this.is.read(buffer, off, len);
      if (r < 0) {
         throw new EOFException();
      } else {
         return r;
      }
   }

   @Override
   public void skip(long len) throws IOException {
      long remaining = len;

      while (remaining > 0L) {
         long skipped = this.is.skip(remaining);
         if (skipped > 0L) {
            remaining -= skipped;
         }
      }
   }
}
