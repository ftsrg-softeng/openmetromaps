package de.topobyte.largescalefileio;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

interface ClosingFileOutputStreamPool {
   OutputStream create(File var1, int var2, boolean var3) throws IOException;

   void flush(int var1) throws IOException;

   void close(int var1) throws IOException;
}
