package de.topobyte.osm4j.core.util;

import de.topobyte.osm4j.core.access.OsmIterator;
import de.topobyte.osm4j.core.model.iface.EntityContainer;
import de.topobyte.osm4j.core.model.iface.EntityType;
import java.util.Iterator;
import java.util.NoSuchElementException;

class EntityIterator<T> implements Iterable<T>, Iterator<T> {
   private OsmIterator iterator;
   private EntityType type;
   private boolean valid = false;
   private boolean hasNext = false;
   private T next = (T)null;

   public EntityIterator(OsmIterator iterator, EntityType type) {
      this.iterator = iterator;
      this.type = type;
   }

   @Override
   public Iterator<T> iterator() {
      return this;
   }

   @Override
   public void remove() {
      throw new UnsupportedOperationException();
   }

   @Override
   public boolean hasNext() {
      if (this.valid) {
         return this.hasNext;
      } else {
         this.advance();
         return this.hasNext;
      }
   }

   @Override
   public T next() {
      if (this.valid) {
         return this.current();
      } else {
         this.advance();
         if (this.valid) {
            return this.current();
         } else {
            throw new NoSuchElementException();
         }
      }
   }

   private T current() {
      T current = this.next;
      this.valid = false;
      this.next = null;
      return current;
   }

   private void advance() {
      while (this.iterator.hasNext()) {
         EntityContainer container = this.iterator.next();
         if (container.getType() == this.type) {
            this.valid = true;
            this.hasNext = true;
            this.next = (T)container.getEntity();
            return;
         }
      }

      this.valid = true;
      this.hasNext = false;
      this.next = null;
   }
}
