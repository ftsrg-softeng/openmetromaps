package de.topobyte.osm4j.extra.idextract;

import de.topobyte.osm4j.core.access.OsmOutputStream;
import de.topobyte.osm4j.extra.entitywriter.EntityWriter;
import de.topobyte.osm4j.extra.idlist.IdInput;
import java.io.IOException;
import java.io.OutputStream;

class Item {
   private long next;
   private IdInput input;
   private OutputStream output;
   private OsmOutputStream osmOutput;
   private EntityWriter writer;

   public Item(IdInput input, OutputStream output, OsmOutputStream osmOutput, EntityWriter writer) throws IOException {
      this.input = input;
      this.output = output;
      this.osmOutput = osmOutput;
      this.writer = writer;
      this.next = input.next();
   }

   public long getNext() {
      return this.next;
   }

   public void next() throws IOException {
      this.next = this.input.next();
   }

   public void close() throws IOException {
      this.input.close();
   }

   public OutputStream getOutput() {
      return this.output;
   }

   public OsmOutputStream getOsmOutput() {
      return this.osmOutput;
   }

   public EntityWriter getWriter() {
      return this.writer;
   }
}
