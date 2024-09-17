package de.topobyte.osm4j.extra.idlist.merge;

import de.topobyte.osm4j.extra.idlist.IdInput;
import java.io.IOException;

class MergeInput {
   private long next;
   private IdInput input;

   public MergeInput(IdInput input) throws IOException {
      this.input = input;
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
}
