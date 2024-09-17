package de.topobyte.largescalefileio;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

interface ClosingFileInputStreamPool {
   InputStream create(File var1, int var2, long var3) throws IOException;

   void close(int var1) throws IOException;
}
