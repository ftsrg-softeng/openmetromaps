package de.topobyte.compactio;

import java.io.IOException;
import java.io.OutputStream;

public class OutputStreamCompactWriter extends CompactWriter {
   private final OutputStream os;

   public OutputStreamCompactWriter(OutputStream os) {
      this.os = os;
   }

   @Override
   public void writeByte(int b) throws IOException {
      this.os.write(b);
   }

   @Override
   public void write(byte[] bytes) throws IOException {
      this.os.write(bytes);
   }

   @Override
   public void write(byte[] bytes, int off, int len) throws IOException {
      this.os.write(bytes, off, len);
   }
}
