package de.topobyte.largescalefileio;

import java.io.File;
import java.io.InputStream;

public interface ClosingFileInputStreamFactory {
   InputStream create(File var1);
}
