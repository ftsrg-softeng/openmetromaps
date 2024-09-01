package de.topobyte.osm4j.utils.buffer;

import de.topobyte.osm4j.core.access.OsmIterator;
import de.topobyte.osm4j.core.access.OsmOutputStream;
import de.topobyte.osm4j.core.model.iface.EntityContainer;
import de.topobyte.osm4j.core.model.iface.EntityType;
import de.topobyte.osm4j.core.model.iface.OsmBounds;
import de.topobyte.osm4j.core.model.iface.OsmNode;
import de.topobyte.osm4j.core.model.iface.OsmRelation;
import de.topobyte.osm4j.core.model.iface.OsmWay;
import java.io.IOException;
import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;

public class OsmBuffer implements OsmOutputStream, OsmIterator {
   private int bufferSize;
   private int maxNumberOfBuffers;
   private int numberOfBuffers;
   private Deque<EntityBuffer> pool = new LinkedList<>();
   private Deque<EntityBuffer> buffers = new LinkedList<>();
   private EntityBuffer currentWriteBuffer;
   private EntityBuffer currentReadBuffer;
   private Object sync = new Object();
   private boolean done = false;
   private boolean valid = true;

   public OsmBuffer(int bufferSize, int maxNumberOfBuffers) {
      this.bufferSize = bufferSize;
      this.maxNumberOfBuffers = maxNumberOfBuffers;
      this.currentWriteBuffer = new EntityBuffer(bufferSize);
      this.currentReadBuffer = new EntityBuffer(bufferSize);
      this.numberOfBuffers = 2;
   }

   public int getSize() {
      return this.buffers.size();
   }

   public void setInvalid() {
      this.valid = false;
   }

   private void write(EntityContainer c) {
      if (this.currentWriteBuffer.size() < this.bufferSize) {
         this.currentWriteBuffer.add(c);
      } else if (this.enqueueCurrentWriteBuffer()) {
         this.currentWriteBuffer.add(c);
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
            this.currentWriteBuffer = new EntityBuffer(this.bufferSize);
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

   public void write(OsmBounds bounds) throws IOException {
   }

   public void write(OsmNode node) throws IOException {
      EntityContainer c = new EntityContainer(EntityType.Node, node);
      this.write(c);
   }

   public void write(OsmWay way) throws IOException {
      EntityContainer c = new EntityContainer(EntityType.Way, way);
      this.write(c);
   }

   public void write(OsmRelation relation) throws IOException {
      EntityContainer c = new EntityContainer(EntityType.Relation, relation);
      this.write(c);
   }

   public void complete() throws IOException {
      this.enqueueCurrentWriteBuffer();
      synchronized (this.sync) {
         this.done = true;
         this.sync.notify();
      }
   }

   public Iterator<EntityContainer> iterator() {
      return this;
   }

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

   public EntityContainer next() {
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

   public void remove() {
      throw new UnsupportedOperationException();
   }

   public boolean hasBounds() {
      return false;
   }

   public OsmBounds getBounds() {
      return null;
   }
}
