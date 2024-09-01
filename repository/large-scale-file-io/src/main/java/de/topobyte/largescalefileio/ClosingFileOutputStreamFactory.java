package de.topobyte.largescalefileio;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

public interface ClosingFileOutputStreamFactory {
   OutputStream create(File var1) throws IOException;
}
