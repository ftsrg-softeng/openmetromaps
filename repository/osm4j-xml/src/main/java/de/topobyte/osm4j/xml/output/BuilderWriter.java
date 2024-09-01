package de.topobyte.osm4j.xml.output;

import java.io.IOException;
import java.io.Writer;

class BuilderWriter extends Writer {
   private StringBuilder buf;

   public BuilderWriter() {
      this.buf = new StringBuilder();
   }

   public BuilderWriter(StringBuilder buf) {
      this.buf = buf;
   }

   @Override
   public void write(char[] cbuf, int off, int len) throws IOException {
      this.buf.append(cbuf, off, len);
   }

   @Override
   public void flush() throws IOException {
   }

   @Override
   public void close() throws IOException {
   }

   public void append(String string) {
      this.buf.append(string);
   }

   @Override
   public String toString() {
      return this.buf.toString();
   }

   public void append(int i) {
      this.buf.append(i);
   }

   public void append(long l) {
      this.buf.append(l);
   }
}
