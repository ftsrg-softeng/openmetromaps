package de.topobyte.compactio;

import java.io.IOException;
import java.nio.charset.Charset;

public abstract class CompactWriter {
   private static final Charset CHARSET = Charset.forName("UTF-8");

   public abstract void writeByte(int var1) throws IOException;

   public abstract void write(byte[] var1) throws IOException;

   public abstract void write(byte[] var1, int var2, int var3) throws IOException;

   public void writeVariableLengthUnsignedInteger(long value) throws IOException {
      long store;
      for (store = value; (store & -128L) != 0L; store >>>= 7) {
         this.writeByte((int)store & 127 | 128);
      }

      this.writeByte((int)store);
   }

   public void writeVariableLengthSignedInteger(long value) throws IOException {
      long store;
      for (store = encodeZigZag(value); (store & -128L) != 0L; store >>>= 7) {
         this.writeByte((int)store & 127 | 128);
      }

      this.writeByte((int)store);
   }

   public static long encodeZigZag(long n) {
      return n << 1 ^ n >> 63;
   }

   public static int getNumberOfBytesUnsigned(long value) {
      if ((value & -128L) == 0L) {
         return 1;
      } else if ((value & -16384L) == 0L) {
         return 2;
      } else if ((value & -2097152L) == 0L) {
         return 3;
      } else if ((value & -268435456L) == 0L) {
         return 4;
      } else if ((value & -34359738368L) == 0L) {
         return 5;
      } else if ((value & -4398046511104L) == 0L) {
         return 6;
      } else if ((value & -562949953421312L) == 0L) {
         return 7;
      } else if ((value & -72057594037927936L) == 0L) {
         return 8;
      } else {
         return (value & Long.MIN_VALUE) == 0L ? 9 : 10;
      }
   }

   public static int getNumberOfBytesSigned(long value) {
      long store = encodeZigZag(value);
      if ((store & -128L) == 0L) {
         return 1;
      } else if ((store & -16384L) == 0L) {
         return 2;
      } else if ((store & -2097152L) == 0L) {
         return 3;
      } else if ((store & -268435456L) == 0L) {
         return 4;
      } else if ((store & -34359738368L) == 0L) {
         return 5;
      } else if ((store & -4398046511104L) == 0L) {
         return 6;
      } else if ((store & -562949953421312L) == 0L) {
         return 7;
      } else if ((store & -72057594037927936L) == 0L) {
         return 8;
      } else {
         return (store & Long.MIN_VALUE) == 0L ? 9 : 10;
      }
   }

   public void writeString(String string) throws IOException {
      byte[] bytes = string.getBytes(CHARSET);
      this.writeVariableLengthSignedInteger((long)bytes.length);
      this.write(bytes);
   }

   public void writeInt(int value) throws IOException {
      this.writeByte(value >>> 24 & 0xFF);
      this.writeByte(value >>> 16 & 0xFF);
      this.writeByte(value >>> 8 & 0xFF);
      this.writeByte(value & 0xFF);
   }

   public void writeLong(long value) throws IOException {
      this.writeByte((int)(value >>> 56) & 0xFF);
      this.writeByte((int)(value >>> 48) & 0xFF);
      this.writeByte((int)(value >>> 40) & 0xFF);
      this.writeByte((int)(value >>> 32) & 0xFF);
      this.writeByte((int)(value >>> 24) & 0xFF);
      this.writeByte((int)(value >>> 16) & 0xFF);
      this.writeByte((int)(value >>> 8) & 0xFF);
      this.writeByte((int)value & 0xFF);
   }
}
