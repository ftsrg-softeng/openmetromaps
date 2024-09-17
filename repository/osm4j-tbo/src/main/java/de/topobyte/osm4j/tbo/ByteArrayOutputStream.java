package de.topobyte.osm4j.tbo;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class ByteArrayOutputStream extends OutputStream {
   private List<byte[]> buffers = new ArrayList<>();
   private int currentBufferIndex;
   private int filledBufferSum;
   private byte[] currentBuffer;
   private int count;

   public ByteArrayOutputStream() {
      this(1024);
   }

   public ByteArrayOutputStream(int size) {
      if (size < 0) {
         throw new IllegalArgumentException("Negative initial size: " + size);
      } else {
         this.needNewBuffer(size);
      }
   }

   private byte[] getBuffer(int index) {
      return this.buffers.get(index);
   }

   private void needNewBuffer(int newcount) {
      if (this.currentBufferIndex < this.buffers.size() - 1) {
         this.filledBufferSum = this.filledBufferSum + this.currentBuffer.length;
         this.currentBufferIndex++;
         this.currentBuffer = this.getBuffer(this.currentBufferIndex);
      } else {
         int newBufferSize;
         if (this.currentBuffer == null) {
            newBufferSize = newcount;
            this.filledBufferSum = 0;
         } else {
            newBufferSize = Math.max(this.currentBuffer.length << 1, newcount - this.filledBufferSum);
            this.filledBufferSum = this.filledBufferSum + this.currentBuffer.length;
         }

         this.currentBufferIndex++;
         this.currentBuffer = new byte[newBufferSize];
         this.buffers.add(this.currentBuffer);
      }
   }

   @Override
   public void write(byte[] b, int off, int len) {
      if (off >= 0 && off <= b.length && len >= 0 && off + len <= b.length && off + len >= 0) {
         if (len != 0) {
            int newcount = this.count + len;
            int remaining = len;
            int inBufferPos = this.count - this.filledBufferSum;

            while (remaining > 0) {
               int part = Math.min(remaining, this.currentBuffer.length - inBufferPos);
               System.arraycopy(b, off + len - remaining, this.currentBuffer, inBufferPos, part);
               remaining -= part;
               if (remaining > 0) {
                  this.needNewBuffer(newcount);
                  inBufferPos = 0;
               }
            }

            this.count = newcount;
         }
      } else {
         throw new IndexOutOfBoundsException();
      }
   }

   @Override
   public void write(int b) {
      this.write(new byte[]{(byte)b}, 0, 1);
   }

   public int size() {
      return this.count;
   }

   @Override
   public void close() throws IOException {
   }

   public void reset() {
      this.count = 0;
      this.filledBufferSum = 0;
      this.currentBufferIndex = 0;
      this.currentBuffer = this.getBuffer(this.currentBufferIndex);
   }

   public void writeTo(OutputStream out) throws IOException {
      int remaining = this.count;

      for (int i = 0; i < this.buffers.size(); i++) {
         byte[] buf = this.getBuffer(i);
         int c = Math.min(buf.length, remaining);
         out.write(buf, 0, c);
         remaining -= c;
         if (remaining == 0) {
            break;
         }
      }
   }

   public byte[] toByteArray() {
      int remaining = this.count;
      int pos = 0;
      byte[] newbuf = new byte[this.count];

      for (int i = 0; i < this.buffers.size(); i++) {
         byte[] buf = this.getBuffer(i);
         int c = Math.min(buf.length, remaining);
         System.arraycopy(buf, 0, newbuf, pos, c);
         pos += c;
         remaining -= c;
         if (remaining == 0) {
            break;
         }
      }

      return newbuf;
   }

   @Override
   public String toString() {
      return new String(this.toByteArray());
   }

   public String toString(String enc) throws UnsupportedEncodingException {
      return new String(this.toByteArray(), enc);
   }
}
