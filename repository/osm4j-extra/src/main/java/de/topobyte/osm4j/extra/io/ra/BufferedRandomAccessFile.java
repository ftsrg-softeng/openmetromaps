package de.topobyte.osm4j.extra.io.ra;

import java.io.EOFException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.HashMap;

public class BufferedRandomAccessFile implements RandomAccess {
   private RandomAccessFile file;
   private long fileSize;
   private int pageSize;
   private int cacheSize;
   private long filePointer = 0L;
   private HashMap<Long, Page> pages;

   public BufferedRandomAccessFile(File file, int pageSize, int cacheSize) throws FileNotFoundException, IOException {
      this(new RandomAccessFile(file, "r"), pageSize, cacheSize);
   }

   public BufferedRandomAccessFile(RandomAccessFile file, int pageSize, int cacheSize) throws IOException {
      this.file = file;
      this.pageSize = pageSize;
      this.cacheSize = cacheSize;
      this.fileSize = file.length();
      this.pages = new LruHashMap<>(cacheSize);
   }

   public int getPageSize() {
      return this.pageSize;
   }

   public int getCacheSize() {
      return this.cacheSize;
   }

   @Override
   public void close() throws IOException {
      this.file.close();
   }

   @Override
   public void seek(long pos) throws IOException {
      this.filePointer = pos;
   }

   @Override
   public long getFilePointer() {
      return this.filePointer;
   }

   public byte readByte() throws IOException {
      long pageNumber = this.filePointer / (long)this.pageSize;
      int pageOffset = (int)(this.filePointer % (long)this.pageSize);
      Page page = this.getPage(pageNumber);
      byte[] data = page.getData();
      if (pageOffset >= data.length) {
         throw new EOFException();
      } else {
         this.filePointer++;
         return data[pageOffset];
      }
   }

   private Page getPage(long pageNumber) throws IOException {
      Page page = this.pages.get(pageNumber);
      if (page == null) {
         page = this.readPage(pageNumber);
         this.pages.put(pageNumber, page);
      }

      return page;
   }

   private Page readPage(long pageNumber) throws IOException {
      long pageOffset = pageNumber * (long)this.pageSize;
      int size = (int)Math.min((long)this.pageSize, this.fileSize - pageOffset);
      byte[] buffer = new byte[size];
      this.file.seek(pageOffset);
      this.file.readFully(buffer);
      return new Page(pageOffset, buffer);
   }

   @Override
   public short readShort() throws IOException {
      int b1 = this.readByte() & 255;
      int b2 = this.readByte() & 255;
      return (short)(b1 << 8 | b2);
   }

   @Override
   public int readInt() throws IOException {
      int b1 = this.readByte() & 255;
      int b2 = this.readByte() & 255;
      int b3 = this.readByte() & 255;
      int b4 = this.readByte() & 255;
      return b1 << 24 | b2 << 16 | b3 << 8 | b4;
   }

   @Override
   public long readLong() throws IOException {
      long i1 = (long)this.readInt() & 4294967295L;
      long i2 = (long)this.readInt() & 4294967295L;
      return i1 << 32 | i2;
   }

   @Override
   public float readFloat() throws IOException {
      return Float.intBitsToFloat(this.readInt());
   }

   @Override
   public double readDouble() throws IOException {
      return Double.longBitsToDouble(this.readLong());
   }

   @Override
   public int read(byte[] b) throws IOException {
      return this.readBytes(b, 0, b.length);
   }

   @Override
   public int read(byte[] b, int off, int len) throws IOException {
      return this.readBytes(b, off, len);
   }

   private int readBytes(byte[] b, int off, int len) throws IOException {
      long pageNumber = this.filePointer / (long)this.pageSize;
      int pageOffset = (int)(this.filePointer % (long)this.pageSize);
      Page page = this.getPage(pageNumber);
      int inPage = page.getData().length - pageOffset;
      int get = Math.min(inPage, len);
      if (get == 0) {
         throw new EOFException();
      } else {
         this.filePointer += (long)get;
         System.arraycopy(page.getData(), pageOffset, b, off, get);
         return get;
      }
   }
}
