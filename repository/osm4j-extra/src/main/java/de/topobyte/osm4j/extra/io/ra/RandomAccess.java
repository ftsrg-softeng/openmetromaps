package de.topobyte.osm4j.extra.io.ra;

import java.io.Closeable;
import java.io.IOException;

public interface RandomAccess extends Closeable {
   void seek(long var1) throws IOException;

   long getFilePointer() throws IOException;

   short readShort() throws IOException;

   int readInt() throws IOException;

   long readLong() throws IOException;

   float readFloat() throws IOException;

   double readDouble() throws IOException;

   int read(byte[] var1) throws IOException;

   int read(byte[] var1, int var2, int var3) throws IOException;
}
