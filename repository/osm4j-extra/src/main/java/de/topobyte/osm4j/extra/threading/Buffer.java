package de.topobyte.osm4j.extra.threading;

import java.io.IOException;
import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;

public class Buffer<T> implements Iterable<T>, Iterator<T> {
   private int numberOfObjects = 0;
   private int maxNumberOfObjects;
   private Deque<T> buffer = new LinkedList<>();
   private Object sync = new Object();
   private boolean done = false;
   private boolean valid = true;

   public Buffer(int maxNumberOfObjects) {
      this.maxNumberOfObjects = maxNumberOfObjects;
   }

   public void returnObject(T object) {
      synchronized (this.sync) {
         this.numberOfObjects--;
         this.sync.notify();
      }
   }

   public int getSize() {
      return this.buffer.size();
   }

   public void setInvalid() {
      this.valid = false;
   }

   public void complete() throws IOException {
      synchronized (this.sync) {
         this.done = true;
         this.sync.notify();
      }
   }

   public void write(T object) {
      synchronized (this.sync) {
         while (this.valid) {
            if (this.numberOfObjects < this.maxNumberOfObjects) {
               this.buffer.add(object);
               this.numberOfObjects++;
               this.sync.notify();
               return;
            }

            try {
               this.sync.wait();
            } catch (InterruptedException var5) {
            }
         }
      }
   }

   @Override
   public Iterator<T> iterator() {
      return this;
   }

   @Override
   public boolean hasNext() {
      synchronized (this.sync) {
         while (this.valid) {
            if (!this.buffer.isEmpty()) {
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

   @Override
   public T next() {
      synchronized (this.sync) {
         while (this.valid) {
            if (!this.buffer.isEmpty()) {
               T object = this.buffer.remove();
               this.sync.notify();
               return object;
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

   @Override
   public void remove() {
      throw new UnsupportedOperationException();
   }
}
