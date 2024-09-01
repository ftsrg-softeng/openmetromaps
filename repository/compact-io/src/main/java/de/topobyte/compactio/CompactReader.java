package de.topobyte.compactio;

import java.io.EOFException;
import java.io.IOException;
import java.nio.charset.Charset;

public abstract class CompactReader {
   private static final Charset CHARSET = Charset.forName("UTF-8");

   public abstract int readByte() throws IOException;

   public abstract int read(byte[] var1, int var2, int var3) throws IOException;

   public abstract void skip(long var1) throws IOException;

   public long readVariableLengthUnsignedInteger() throws IOException {
      int shift = 0;

      for (long result = 0L; shift < 64; shift += 7) {
         int b = this.readByte();
         result |= (long)(b & 127) << shift;
         if ((b & 128) == 0) {
            return result;
         }
      }

      throw new IOException("invalid encoding for a long value");
   }

   public long readVariableLengthSignedInteger() throws IOException {
      int shift = 0;

      for (long result = 0L; shift < 64; shift += 7) {
         int b = this.readByte();
         result |= (long)(b & 127) << shift;
         if ((b & 128) == 0) {
            return decodeZigZag(result);
         }
      }

      throw new IOException("invalid encoding for a long value");
   }

   public static long decodeZigZag(long n) {
      return n >>> 1 ^ -(n & 1L);
   }

   public String readString() throws IOException {
      int length = (int)this.readVariableLengthSignedInteger();
      if (length < 0) {
         throw new IOException("Unable to parse string with negative length");
      } else {
         byte[] buffer = new byte[length];
         this.readFully(buffer);
         return new String(buffer, CHARSET);
      }
   }

   public void readFully(byte[] buffer) throws IOException {
      this.readFully(buffer, 0, buffer.length);
   }

   private void readFully(byte[] buffer, int off, int len) throws IOException {
      while (len > 0) {
         int n = this.read(buffer, off, len);
         if (n < 0) {
            throw new EOFException();
         }

         off += n;
         len -= n;
      }
   }

   public int readInt() throws IOException {
      int b1 = this.readByte();
      int b2 = this.readByte();
      int b3 = this.readByte();
      int b4 = this.readByte();
      return b1 << 24 | b2 << 16 | b3 << 8 | b4;
   }

   public long readLong() throws IOException {
      long i1 = (long)this.readInt();
      long i2 = (long)this.readInt();
      return i1 << 32 | i2 & 4294967295L;
   }
}
