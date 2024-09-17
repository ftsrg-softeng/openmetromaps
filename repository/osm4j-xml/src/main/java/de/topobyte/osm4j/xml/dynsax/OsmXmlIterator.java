package de.topobyte.osm4j.xml.dynsax;

import de.topobyte.osm4j.core.access.OsmHandler;
import de.topobyte.osm4j.core.access.OsmInputException;
import de.topobyte.osm4j.core.access.OsmIterator;
import de.topobyte.osm4j.core.access.OsmReader;
import de.topobyte.osm4j.core.model.iface.EntityContainer;
import de.topobyte.osm4j.core.model.iface.EntityType;
import de.topobyte.osm4j.core.model.iface.OsmBounds;
import de.topobyte.osm4j.core.model.iface.OsmNode;
import de.topobyte.osm4j.core.model.iface.OsmRelation;
import de.topobyte.osm4j.core.model.iface.OsmWay;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

public class OsmXmlIterator implements OsmIterator, OsmHandler {
   private static int LIMIT = 128;
   private Object mutex = new Object();
   private OsmXmlIterator.State state = OsmXmlIterator.State.PUSH;
   private List<EntityContainer> list = new ArrayList<>();
   private Exception exception = null;
   private OsmBounds bounds = null;
   private boolean beyondBounds = false;

   public OsmXmlIterator(InputStream inputStream, boolean parseMetadata) {
      this.init(inputStream, parseMetadata);
   }

   public OsmXmlIterator(File file, boolean parseMetadata) throws FileNotFoundException {
      InputStream fis = new FileInputStream(file);
      InputStream bis = new BufferedInputStream(fis);
      this.init(bis, parseMetadata);
   }

   public OsmXmlIterator(String pathname, boolean parseMetadata) throws FileNotFoundException {
      this(new File(pathname), parseMetadata);
   }

   private void init(final InputStream inputStream, final boolean parseMetadata) {
      Thread thread = new Thread(new Runnable() {
         @Override
         public void run() {
            OsmReader reader = new OsmXmlReader(inputStream, parseMetadata);
            reader.setHandler(OsmXmlIterator.this);

            try {
               reader.read();
            } catch (OsmInputException var15) {
               OsmInputException e = var15;
               synchronized (OsmXmlIterator.this.mutex) {
                  OsmXmlIterator.this.beyondBounds = true;
                  OsmXmlIterator.this.state = OsmXmlIterator.State.EXCEPTION;
                  OsmXmlIterator.this.exception = e;
                  OsmXmlIterator.this.mutex.notify();
               }
            } finally {
               try {
                  inputStream.close();
               } catch (IOException var13) {
               }
            }
         }
      });
      thread.start();
   }

   public Iterator<EntityContainer> iterator() {
      return this;
   }

   public boolean hasNext() {
      while (true) {
         synchronized (this.mutex) {
            switch (this.state) {
               case READ:
                  return true;
               case END:
                  return !this.list.isEmpty();
               case EXCEPTION:
                  if (!this.list.isEmpty()) {
                     return true;
                  }

                  throw new RuntimeException("error while processing input", this.exception);
               case PUSH:
                  try {
                     this.mutex.wait();
                  } catch (InterruptedException var4) {
                  }
            }
         }
      }
   }

   public EntityContainer next() {
      while (true) {
         synchronized (this.mutex) {
            switch (this.state) {
               case READ:
               case END:
               case EXCEPTION:
                  if (!this.list.isEmpty()) {
                     EntityContainer next = this.list.remove(0);
                     if (this.list.isEmpty() && this.state == OsmXmlIterator.State.READ) {
                        this.state = OsmXmlIterator.State.PUSH;
                        this.mutex.notify();
                     }

                     return next;
                  }
               case PUSH:
               default:
                  if (this.state == OsmXmlIterator.State.EXCEPTION) {
                     throw new RuntimeException("error while processing input", this.exception);
                  }

                  if (this.state == OsmXmlIterator.State.END) {
                     throw new NoSuchElementException("End of stream has been reached");
                  }

                  try {
                     this.mutex.wait();
                  } catch (InterruptedException var4) {
                  }
            }
         }
      }
   }

   public void remove() {
      throw new UnsupportedOperationException("an iterator over osm files is read-only");
   }

   public void complete() {
      synchronized (this.mutex) {
         this.beyondBounds = true;
         this.state = OsmXmlIterator.State.END;
         this.mutex.notify();
      }
   }

   public void handle(OsmBounds bounds) throws IOException {
      synchronized (this.mutex) {
         if (!this.beyondBounds) {
            this.beyondBounds = true;
            this.bounds = bounds;
         }
      }
   }

   public void handle(OsmNode node) {
      synchronized (this.mutex) {
         while (this.state != OsmXmlIterator.State.PUSH) {
            try {
               this.mutex.wait();
            } catch (InterruptedException var5) {
            }
         }

         this.beyondBounds = true;
         this.list.add(new EntityContainer(EntityType.Node, node));
         if (this.list.size() == LIMIT) {
            this.state = OsmXmlIterator.State.READ;
            this.mutex.notify();
         }
      }
   }

   public void handle(OsmWay way) {
      synchronized (this.mutex) {
         while (this.state != OsmXmlIterator.State.PUSH) {
            try {
               this.mutex.wait();
            } catch (InterruptedException var5) {
            }
         }

         this.beyondBounds = true;
         this.list.add(new EntityContainer(EntityType.Way, way));
         if (this.list.size() == LIMIT) {
            this.state = OsmXmlIterator.State.READ;
            this.mutex.notify();
         }
      }
   }

   public void handle(OsmRelation relation) {
      synchronized (this.mutex) {
         while (this.state != OsmXmlIterator.State.PUSH) {
            try {
               this.mutex.wait();
            } catch (InterruptedException var5) {
            }
         }

         this.beyondBounds = true;
         this.list.add(new EntityContainer(EntityType.Relation, relation));
         if (this.list.size() == LIMIT) {
            this.state = OsmXmlIterator.State.READ;
            this.mutex.notify();
         }
      }
   }

   public boolean hasBounds() {
      synchronized (this.mutex) {
         while (!this.beyondBounds) {
            try {
               this.mutex.wait();
            } catch (InterruptedException var4) {
            }
         }

         return this.bounds != null;
      }
   }

   public OsmBounds getBounds() {
      synchronized (this.mutex) {
         while (!this.beyondBounds) {
            try {
               this.mutex.wait();
            } catch (InterruptedException var4) {
            }
         }

         return this.bounds;
      }
   }

   private static enum State {
      PUSH,
      READ,
      END,
      EXCEPTION;
   }
}
