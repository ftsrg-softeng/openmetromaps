package de.topobyte.compactio;

import java.io.EOFException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class RandomAccessFileCompactReader extends CompactReader {
   private final RandomAccessFile file;

   public RandomAccessFileCompactReader(RandomAccessFile file) {
      this.file = file;
   }

   @Override
   public int readByte() throws IOException {
      int r = this.file.read();
      if (r < 0) {
         throw new EOFException();
      } else {
         return r;
      }
   }

   @Override
   public int read(byte[] buffer, int off, int len) throws IOException {
      int r = this.file.read(buffer, off, len);
      if (r < 0) {
         throw new EOFException();
      } else {
         return r;
      }
   }

   @Override
   public void skip(long len) throws IOException {
      this.file.seek(this.file.getFilePointer() + len);
   }
}
