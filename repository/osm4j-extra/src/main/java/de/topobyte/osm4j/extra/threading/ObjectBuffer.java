package de.topobyte.osm4j.extra.threading;

import java.io.IOException;
import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;

public class ObjectBuffer<T> implements Iterable<T>, Iterator<T> {
   private int bufferSize;
   private int maxNumberOfBuffers;
   private int numberOfBuffers;
   private Deque<IternalObjectBuffer<T>> pool = new LinkedList<>();
   private Deque<IternalObjectBuffer<T>> buffers = new LinkedList<>();
   private IternalObjectBuffer<T> currentWriteBuffer;
   private IternalObjectBuffer<T> currentReadBuffer;
   private Object sync = new Object();
   private boolean done = false;
   private boolean valid = true;

   public ObjectBuffer(int bufferSize, int maxNumberOfBuffers) {
      this.bufferSize = bufferSize;
      this.maxNumberOfBuffers = maxNumberOfBuffers;
      this.currentWriteBuffer = new IternalObjectBuffer<>(bufferSize);
      this.currentReadBuffer = new IternalObjectBuffer<>(bufferSize);
      this.numberOfBuffers = 2;
   }

   public int getSize() {
      return this.buffers.size();
   }

   public void setInvalid() {
      this.valid = false;
   }

   public void write(T c) {
      if (this.currentWriteBuffer.size() < this.bufferSize) {
         this.currentWriteBuffer.add(c);
      } else if (this.enqueueCurrentWriteBuffer()) {
         this.currentWriteBuffer.add(c);
      }
   }

   public void close() throws IOException {
      if (!this.currentWriteBuffer.isEmpty()) {
         this.enqueueCurrentWriteBuffer();
      }

      synchronized (this.sync) {
         this.done = true;
         this.sync.notify();
      }
   }

   private boolean enqueueCurrentWriteBuffer() {
      synchronized (this.sync) {
         this.buffers.add(this.currentWriteBuffer);
         this.sync.notify();
         if (!this.pool.isEmpty()) {
            this.currentWriteBuffer = this.pool.removeFirst();
            return true;
         }

         if (this.numberOfBuffers < this.maxNumberOfBuffers) {
            this.currentWriteBuffer = new IternalObjectBuffer<>(this.bufferSize);
            this.numberOfBuffers++;
            return true;
         }
      }

      synchronized (this.sync) {
         while (this.valid) {
            if (!this.pool.isEmpty()) {
               this.currentWriteBuffer = this.pool.removeFirst();
               return true;
            }

            try {
               this.sync.wait();
            } catch (InterruptedException var4) {
            }
         }

         return false;
      }
   }

   @Override
   public Iterator<T> iterator() {
      return this;
   }

   @Override
   public boolean hasNext() {
      if (!this.currentReadBuffer.isEmpty()) {
         return true;
      } else {
         synchronized (this.sync) {
            while (this.valid) {
               if (!this.buffers.isEmpty()) {
                  return true;
               }

               if (this.done) {
                  return false;
               }

               try {
                  this.sync.wait();
               } catch (InterruptedException var4) {
               }
            }

            return false;
         }
      }
   }

   @Override
   public T next() {
      if (!this.currentReadBuffer.isEmpty()) {
         return this.currentReadBuffer.remove();
      } else {
         synchronized (this.sync) {
            while (this.valid) {
               if (!this.buffers.isEmpty()) {
                  this.currentReadBuffer.clear();
                  this.pool.add(this.currentReadBuffer);
                  this.currentReadBuffer = this.buffers.remove();
                  this.sync.notify();
                  return this.currentReadBuffer.remove();
               }

               if (this.done) {
                  throw new NoSuchElementException();
               }

               try {
                  this.sync.wait();
               } catch (InterruptedException var4) {
               }
            }
         }

         throw new NoSuchElementException();
      }
   }

   @Override
   public void remove() {
      throw new UnsupportedOperationException();
   }
}
